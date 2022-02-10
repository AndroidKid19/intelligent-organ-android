package com.yway.scomponent.organ.mvp.ui.fragment;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.PhoneUtils;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;

import static com.jess.arms.utils.Preconditions.checkNotNull;

import com.jess.arms.utils.ArmsUtils;

import android.content.Intent;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yway.scomponent.commonres.dialog.IToast;
import com.yway.scomponent.commonres.dialog.ProgresDialog;
import com.yway.scomponent.commonres.view.layout.MultipleStatusView;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.di.component.DaggerAfterPrepareComponent;
import com.yway.scomponent.organ.mvp.contract.AfterPrepareContract;
import com.yway.scomponent.organ.mvp.model.entity.MeetingRecordBean;
import com.yway.scomponent.organ.mvp.presenter.AfterPreparePresenter;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.mvp.ui.adapter.AfterPrepareAdapter;
import com.yway.scomponent.organ.mvp.ui.adapter.BeforePrepareAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 准备后
 */
public class AfterPrepareFragment extends BaseFragment<AfterPreparePresenter> implements AfterPrepareContract.View {
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
    AfterPrepareAdapter mAdapter;
    /**
     * 查询参数
     **/
    private Map<String, Object> paramMap = new HashMap<>();

    /**
     * 骨架屏
     **/
    private SkeletonScreen mSkeletonScreen;
    /**
     * 注入列表数据源
     */
    @Inject
    List<MeetingRecordBean> mDataLs;

    public static AfterPrepareFragment newInstance() {
        AfterPrepareFragment fragment = new AfterPrepareFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerAfterPrepareComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.organ_fragment_after_prepare, container, false);
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
        //设置下拉刷新监听
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mRefreshLayout.setOnLoadMoreListener(mOnLoadMoreListener);
        mAdapter.setOnItemClickListener((view, viewType, data, position) -> {
            MeetingRecordBean meetingRecordBean = (MeetingRecordBean) data;
            //审批参数
            Map<String, Object> paramsMap = new HashMap<>();
            //会议id
            paramsMap.put("meetingRecordId", meetingRecordBean.getId());
            //根据viewid 获取点击事件
            if (view.getId() == R.id.tv_create_user) {
                //拨号
                PhoneUtils.dial(meetingRecordBean.getCellPhone());
            } else {
                Utils.postcard(RouterHub.HOME_MEETINGDETAILSACTIVITY)
                        .withString("mettingId", meetingRecordBean.getId())
                        .withInt("pageFrom", 5)
                        .navigation(getActivity(), 10000);
            }

        });
    }


    /**
     * @description : TODO 下拉刷新监听
     * @author : yuanweiwei
     */
    private OnRefreshListener mOnRefreshListener = refreshLayout -> {
        mPresenter.getMeetingRecordReadyedList(paramMap, true);
    };

    /**
     * @description : TODO 上拉加载监听
     * @author : yuanweiwei
     */
    private OnLoadMoreListener mOnLoadMoreListener = refreshLayout -> {
        mPresenter.getMeetingRecordReadyedList(paramMap, false);
    };


    /**
     * @return
     * @description 初始化骨架屏
     * @date: 2020/8/10 16:56
     * @author: YIWUANYUAN
     */
    private void initSkeletonScreen() {
        //初始化数据
        mPresenter.getMeetingRecordReadyedList(paramMap, true);
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10000 && resultCode == Activity.RESULT_OK) {
            mRefreshLayout.autoRefresh();
        }
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
    public void setData(@Nullable Object data) {

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
}