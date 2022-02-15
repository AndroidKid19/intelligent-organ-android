package com.yway.scomponent.organ.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.StringUtils;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yway.scomponent.commonres.dialog.IToast;
import com.yway.scomponent.commonres.view.banner.BGABanner;
import com.yway.scomponent.commonsdk.BuildConfig;
import com.yway.scomponent.commonsdk.core.EventBusHub;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.di.component.DaggerHomeComponent;
import com.yway.scomponent.organ.mvp.contract.HomeContract;
import com.yway.scomponent.organ.mvp.model.entity.ConferenceBean;
import com.yway.scomponent.organ.mvp.model.entity.ConferenceTitleBean;
import com.yway.scomponent.organ.mvp.model.entity.HomeMetingBean;
import com.yway.scomponent.organ.mvp.model.entity.MessageBean;
import com.yway.scomponent.organ.mvp.model.entity.MessageTitleBean;
import com.yway.scomponent.organ.mvp.presenter.HomePresenter;
import com.yway.scomponent.organ.mvp.ui.adapter.HomeAdapter;
import org.simple.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;
import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * ================================================
 * Description:首页
 * ================================================
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View, BGABanner.Delegate<ImageView, String>, BGABanner.Adapter<ImageView, String> {
    @BindView(R2.id.banner_view)
    BGABanner mContentBanner;
    /**
     * 消息及会议内容
     */
    @BindView(R2.id.recycle_view)
    RecyclerView mRecyclerView;
    /**
     * 下拉刷新，上拉加载View
     */
    @BindView(R2.id.refresh_layout)
    RefreshLayout mRefreshLayout;

    /**
     * 注入列表管理器
     */
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    HomeAdapter mAdapter;
    /**
     * 注入列表数据源
     */
    @Inject
    List<Object> mDataLs;
    /**
     * 骨架屏
     **/
    private SkeletonScreen mSkeletonScreen;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.organ_fragment_home, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        BarUtils.setStatusBarLightMode(getActivity(), false);
        //状态栏沉浸
        ImmersionBar.with(this).titleBar(R.id.view_search).statusBarDarkFont(true).init();
        //初始化recylerview
        initRecyclerView();
        //初始化banner
        initBanner();
        //初始化骨架屏
//        initSkeletonScreen();
        initQueryData();
    }

    private void initQueryData(){
        //初始化字段数据
        mPresenter.queryDict();
        //查询我的会议
        mPresenter.queryMyMeetingList();
        //查询资讯
        mPresenter.queryArticlePublishPageList();
    }

    /**
     * 搜索点击事件回调
     * */
    @OnClick(R2.id.tv_search)
    void onSearchClick(View view){
        Utils.postcard(RouterHub.HOME_INFORMATIONACTIVITY)
                .navigation(getActivity());
    }


    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        //设置下拉刷新监听
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        //设置上拉加载
//        mRefreshLayout.setOnLoadMoreListener(mOnLoadMoreListener);
        mRefreshLayout.setEnableLoadMore(false);

        //列表事件监听
        mAdapter.setOnItemClickListener(mOnRecyclerViewItemClickListener);
    }

    private DefaultAdapter.OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener = (view, viewType, data, position) -> {
        if (view.getId() == R.id.tv_queryall){
            //查看更多会议 - 我的会议
            Utils.navigation(getActivity(), RouterHub.HOME_MYMEETINGACTIVITY);
        }else if(view.getId() == R.id.view_metting){
            ConferenceBean conferenceBean = (ConferenceBean) data;
            Utils.postcard(RouterHub.HOME_MEETINGDETAILSACTIVITY)
                    .withString("mettingId",conferenceBean.getMeetingRecordId())
                    .withInt("pageFrom",6)
                    .navigation(getActivity());
        }else if(view.getId() == R.id.tv_more){
            //文章查看更多
            MessageTitleBean messageTitleBean = (MessageTitleBean) data;
            Utils.postcard(RouterHub.HOME_INFORMATIONACTIVITY)
                    .navigation(getActivity());
        }else if(view.getId() == R.id.view_information){
            MessageBean messageBean = (MessageBean) data;
            Timber.i(Utils.appendStr(BuildConfig.H5_HOST_ROOT,"articleMobile?id=",messageBean.getId()));
            Utils.postcard(RouterHub.HOME_WEBVIEWACTIVITY)
                    .withString(RouterHub.PARAM_WEBVIEWXURL,Utils.appendStr(BuildConfig.H5_HOST_ROOT,"articleMobile?id=",messageBean.getId()))
                    .withInt("pageFrom",2)
                    .withString("articleId",messageBean.getId())
                    .navigation(getActivity());
        }
    };

    /**
     * @return
     * @description 初始化骨架屏
     * @date: 2020/8/10 16:56
     * @author: YIWUANYUAN
     */
    private void initSkeletonScreen() {
        mSkeletonScreen = Skeleton.bind(mRecyclerView)
                .adapter(mAdapter)
                .shimmer(true)
                .angle(20)
                .frozen(false)
                .duration(1200)
                .count(15)
                .load(R.layout.organ_home_item_skeleton)
                .show(); //default count is 10
    }


    /**
     * init banner data
     */
    private void initBanner() {
        // 设置数据源

        mContentBanner.setDelegate(this);
        mContentBanner.setAdapter(this);
        List<String> bannerLs = new ArrayList<>();
        bannerLs.add("https://bdp-dev-bucket.oss-cn-beijing.aliyuncs.com/app/20220214140555.png");
        bannerLs.add("https://bdp-dev-bucket.oss-cn-beijing.aliyuncs.com/app/20220214140548.png");
        mContentBanner.setAutoPlayAble(bannerLs.size() > 1);
        mContentBanner.setData(bannerLs, null);
    }

    /**
     * @description : TODO 下拉监听
     * @author : yuanweiwei
     */
    private OnRefreshListener mOnRefreshListener = refreshLayout -> {
        //初始化字段数据
        initQueryData();
    };

    /**
     * @description : TODO 上拉加载
     * @author : yuanweiwei
     */
    private OnLoadMoreListener mOnLoadMoreListener = refreshLayout -> {
    };

    /**
     * 通讯录
     */
    @OnClick(R2.id.tv_menu_address)
    void onAddressClick(View view){
        EventBus.getDefault().post(1, EventBusHub.EVENTBUS_TAG_HOME_CURRENTITEM_REFRESH);
    }

    /**
     * 会议室
     */
    @OnClick(R2.id.tv_menu_room)
    void onRoomClick(View view){
        Utils.navigation(getActivity(), RouterHub.HOME_CONFERENCEROOMACTIVITY);
    }

    /**
     * 健康食堂
     */
    @OnClick(R2.id.tv_menu_canteen)
    void onCanteenClick(View view){
        IToast.showWarnShort("正在努力开发中");
    }

    /**
     * 卡包
     */
    @OnClick(R2.id.tv_menu_card)
    void onCardClick(View view){
        IToast.showWarnShort("正在努力开发中");
    }


    /**
     * 我的会议
     */
    @Override
    public void metingListCallBack(HomeMetingBean data) {
        //正在开会议
        List<ConferenceBean> inMeetingPersonnelRspBOList = data.getInMeetingPersonnelRspBOList();
        //待开会议
        List<ConferenceBean> todoMeetingPersonnelRspBOList = data.getTodoMeetingPersonnelRspBOList();

        //创建我的会议标题
        if (!checkMetingTitle()){
            ConferenceTitleBean conferenceTitleBean = new ConferenceTitleBean();
            conferenceTitleBean.setMetingCount(inMeetingPersonnelRspBOList.size()+todoMeetingPersonnelRspBOList.size());
            mDataLs.add(conferenceTitleBean);
        }

        //校验正在开的会议
        if (CollectionUtils.isNotEmpty(inMeetingPersonnelRspBOList)){
            for (ConferenceBean conferenceBean :inMeetingPersonnelRspBOList) {
                conferenceBean.setConfIng(true);
                /**
                 * 校验会议是否展示 未展示则添加到首页展示
                 * */
                if (!checkMeting(conferenceBean.getMeetingRecordId())){
                    mDataLs.add(conferenceBean);
                }
            }
        }
        //校验待开会议
        if (CollectionUtils.isNotEmpty(todoMeetingPersonnelRspBOList)){
            for (ConferenceBean conferenceBean :todoMeetingPersonnelRspBOList) {
                /**
                 * 校验会议是否展示 未展示则添加到首页展示
                 * */
                if (!checkMeting(conferenceBean.getMeetingRecordId())){
                    mDataLs.add(conferenceBean);
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 资讯
     * */
    @Override
    public void queryArticleCallBack(List<MessageBean> rows) {
        mDataLs.clear();
        //创建我的会议标题
        if (!checkMsgTitle()){
            //初始化默认数据
            MessageTitleBean messageTitleBean = new MessageTitleBean();
            mDataLs.add(messageTitleBean);
        }
        mDataLs.addAll(rows);
    }

    /**
     * 校验当前会议是否展示到首页
     * */
    public boolean checkMeting(String id){
      List<Object> objectList =  mAdapter.getInfos();
        for (Object o : objectList) {
            if (o instanceof ConferenceBean){
                ConferenceBean conferenceBean = (ConferenceBean) o;
                if (conferenceBean.getMeetingRecordId().equals(id)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 校验当前会议是否展示到首页
     * */
    public boolean checkMetingTitle(){
        List<Object> objectList =  mAdapter.getInfos();
        for (Object o : objectList) {
            if (o instanceof ConferenceTitleBean){
                return true;
            }
        }
        return false;
    }


    /**
     * 校验当前会议是否展示到首页
     * */
    public boolean checkMsgTitle(){
        List<Object> objectList =  mAdapter.getInfos();
        for (Object o : objectList) {
            if (o instanceof MessageTitleBean){
                return true;
            }
        }
        return false;
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public RefreshLayout refreshLayout() {
        return mRefreshLayout;
    }


    @Override
    public SkeletonScreen skeletonScreen() {
        return mSkeletonScreen;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    /**
     * banner click callback
     */
    @Override
    public void onBannerItemClick(BGABanner banner, ImageView itemView, @Nullable @org.jetbrains.annotations.Nullable String model, int position) {
        String newUrl = "https://www.baidu.com";
        if (!StringUtils.isEmpty(newUrl)) {
            Utils.postcard(RouterHub.APP_AGENTWEBACTIVITY)
                    .withString(RouterHub.PARAM_WEBVIEWXURL, newUrl)
                    .navigation(getContext());
        }
    }

    /**
     * banner 图片展示
     */
    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable @org.jetbrains.annotations.Nullable String model, int position) {
        mPresenter.imageLoader(model, itemView);
    }
}
