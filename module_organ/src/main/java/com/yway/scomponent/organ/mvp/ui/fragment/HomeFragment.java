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
import com.blankj.utilcode.util.StringUtils;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yway.scomponent.commonres.dialog.IToast;
import com.yway.scomponent.commonres.dialog.MessageDialog;
import com.yway.scomponent.commonres.view.banner.BGABanner;
import com.yway.scomponent.commonsdk.core.EventBusHub;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.di.component.DaggerHomeComponent;
import com.yway.scomponent.organ.mvp.contract.HomeContract;
import com.yway.scomponent.organ.mvp.model.entity.ConferenceBean;
import com.yway.scomponent.organ.mvp.model.entity.ConferenceTitleBean;
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
        //初始化字段数据
        mPresenter.queryDict();
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
        //初始化默认数据
        MessageTitleBean messageTitleBean = new MessageTitleBean();
        mDataLs.add(messageTitleBean);

        MessageBean messageBean = new MessageBean();
        mDataLs.add(messageBean);
        messageBean = new MessageBean();
        mDataLs.add(messageBean);

        ConferenceTitleBean conferenceTitleBean = new ConferenceTitleBean();
        mDataLs.add(conferenceTitleBean);

        ConferenceBean conferenceBean = new ConferenceBean();
        conferenceBean.setConfIng(true);
        mDataLs.add(conferenceBean);
        conferenceBean = new ConferenceBean();
        mDataLs.add(conferenceBean);
        conferenceBean = new ConferenceBean();
        mDataLs.add(conferenceBean);

//        mAdapter.refreshData(mDataLs);
    }

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
        bannerLs.add("https://bdp-dev-bucket.oss-cn-beijing.aliyuncs.com/breed/home_ic_banner1.jpg");
        bannerLs.add("https://bdp-dev-bucket.oss-cn-beijing.aliyuncs.com/breed/home_ic_banner2.jpg");
        mContentBanner.setAutoPlayAble(bannerLs.size() > 1);
        mContentBanner.setData(bannerLs, null);
    }

    /**
     * @description : TODO 下拉监听
     * @author : yuanweiwei
     */
    private OnRefreshListener mOnRefreshListener = refreshLayout -> {
        //初始化字段数据
        mPresenter.queryDict();
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
