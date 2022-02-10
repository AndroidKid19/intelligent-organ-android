package com.yway.scomponent.organ.mvp.ui.fragment;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import static com.jess.arms.utils.Preconditions.checkNotNull;
import com.jess.arms.utils.ArmsUtils;
import android.content.Intent;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yway.scomponent.commonres.dialog.IToast;
import com.yway.scomponent.commonres.dialog.MessageDialog;
import com.yway.scomponent.commonres.dialog.ProgresDialog;
import com.yway.scomponent.commonres.view.layout.MultipleStatusView;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.utils.CacheUtils;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.di.component.DaggerDraftsComponent;
import com.yway.scomponent.organ.mvp.contract.DraftsContract;
import com.yway.scomponent.organ.mvp.model.entity.ConferenceBean;
import com.yway.scomponent.organ.mvp.model.entity.MeetingDetailsBean;
import com.yway.scomponent.organ.mvp.model.entity.MeetingRecordBean;
import com.yway.scomponent.organ.mvp.model.entity.RoomDetailsBean;
import com.yway.scomponent.organ.mvp.model.entity.SubscribeTimeBean;
import com.yway.scomponent.organ.mvp.presenter.DraftsPresenter;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.mvp.ui.adapter.DraftsAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import butterknife.BindView;

/**
 * 草稿箱
 */
public class DraftsFragment extends BaseFragment<DraftsPresenter> implements DraftsContract.View {

    /**
     * 多状态view
     */
    @BindView(R2.id.multiple_layout)
    MultipleStatusView mMultipleStatusView;
    /**
     * 下拉刷新，上拉加载View
     */
    @BindView(R2.id.refresh_layout)
    RefreshLayout mRefreshLayout;
    /**
     * RecycleView
     **/
    @BindView(R2.id.recycle_view)
    RecyclerView mRecyclerView;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    DraftsAdapter mAdapter;

    /**
     * 查询参数
     **/
    private Map<String, Object> paramMap = new HashMap<>();

    /**
     * 骨架屏
     **/
    private SkeletonScreen mSkeletonScreen;

    /**
     * 记录当前删除的草稿下标
     * */
    private int delPosition;
    /**
     * 会议详情
     * */
    private RoomDetailsBean mRoomDetailsBean;

    public static DraftsFragment newInstance() {
        DraftsFragment fragment = new DraftsFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerDraftsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.organ_fragment_drafts, container, false);
    }

    /**
     * 在 onActivityCreate()时调用
     */
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
        //初始化骨架屏
        initSkeletonScreen();
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(mOnRecyclerViewItemClickListener);
        //设置下拉刷新监听
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mRefreshLayout.setOnLoadMoreListener(mOnLoadMoreListener);

    }

    private DefaultAdapter.OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener = (view, viewType, data, position) -> {
        MeetingRecordBean meetingRecordBean = (MeetingRecordBean) data;
        this.delPosition = position;
        if (view.getId() == R.id.tv_apply_state) {
            //删除草稿箱
            delDrafts(meetingRecordBean.getId());
        } else {
            //预约会议室
            //创建会议室预约信息
            mRoomDetailsBean = new RoomDetailsBean();
            //初始化预约时间
            mRoomDetailsBean.setMeetingDate(meetingRecordBean.getMeetingStartTime());
            //查询会议室详情
            Map<String,Object> paramsMap = new HashMap<>();
            paramsMap.put("id",meetingRecordBean.getId());
            mPresenter.queryByMeetingRecordDetails(paramsMap);
        }
    };


    private void delDrafts(String id){
        new MessageDialog.Builder()
                .setTitle("删除提醒")
                .setMessage("确定要删除当前会议草稿")
                .setOnViewItemClickListener(v -> {
                    //删除草稿箱
                    Map<String, Object> paramMap = new HashMap<>();
                    paramMap.put("id",id);
                    mPresenter.deleteMeetingRecord(paramMap);
                })
                .showPopupWindow();
    }

    /**
     * @description : TODO 下拉刷新监听
     * @author : yuanweiwei
     */
    private OnRefreshListener mOnRefreshListener = refreshLayout -> {
        mPresenter.queryMeetingRecordPageList(paramMap, true);
    };

    /**
     * @description : TODO 上拉加载监听
     * @author : yuanweiwei
     */
    private OnLoadMoreListener mOnLoadMoreListener = refreshLayout -> {
        mPresenter.queryMeetingRecordPageList(paramMap, false);
    };


    /**
     * @return
     * @description 初始化骨架屏
     * @date: 2020/8/10 16:56
     * @author: YIWUANYUAN
     */
    private void initSkeletonScreen() {
        paramMap.put("approvalStatusStrs", "0");
        //初始化数据
        mPresenter.queryMeetingRecordPageList(paramMap, true);
        mSkeletonScreen = Skeleton.bind(mRecyclerView)
                .adapter(mAdapter)
                .shimmer(true)
                .angle(20)
                .frozen(false)
                .duration(1200)
                .count(15)
                .load(R.layout.organ_item_skeleton_metting)
                .show(); //default count is 10
    }

    @Override
    public void queryMessingDetailsSuccess(MeetingDetailsBean data) {
        Utils.postcard(RouterHub.HOME_APPLYROOMACTIVITY)
                .withParcelable("roomDetailsBean",mRoomDetailsBean)
                .withParcelable("meetingDetailsBean",data)
                .withInt("pageFrom",1)
                .navigation(getActivity());
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        ProgresDialog.getInstance(getActivity()).show();
    }

    @Override
    public void hideLoading() {
        ProgresDialog.getInstance(getActivity()).dismissDialog();
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

    public Fragment getFragment() {
        return this;
    }


    @Override
    public SkeletonScreen skeletonScreen() {
        return mSkeletonScreen;
    }

    @Override
    public MultipleStatusView multipleStatusView() {
        return mMultipleStatusView;
    }

    @Override
    public RefreshLayout refreshLayout() {
        return mRefreshLayout;
    }

    @Override
    public void delMeetingSuccess() {
        IToast.showFinishShort("删除成功");
        mAdapter.remove(delPosition);
    }


}