package com.yway.scomponent.organ.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yway.scomponent.commonres.view.layout.MultipleStatusView;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.di.component.DaggerMyMeetingComponent;
import com.yway.scomponent.organ.mvp.contract.MyMeetingContract;
import com.yway.scomponent.organ.mvp.presenter.MyMeetingPresenter;
import com.yway.scomponent.organ.mvp.ui.adapter.ConfirmMeetingAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 我的会议
 */
@Route(path = RouterHub.HOME_MYMEETINGACTIVITY)
public class MyMeetingActivity extends BaseActivity<MyMeetingPresenter> implements MyMeetingContract.View {
    /**
     * 多状态view
     */
    @BindView(R2.id.multiple_layout_meting)
    MultipleStatusView mMultipleStatusView;
    /**
     * 下拉刷新，上拉加载View
     */
    @BindView(R2.id.refresh_layout_meting)
    RefreshLayout mRefreshLayout;
    /**
     * RecycleView
     **/
    @BindView(R2.id.recycle_view_meting)
    RecyclerView mRecyclerView;
    /**
     * 筛选条件
     **/
    @BindView(R2.id.rg_condition_meting)
    RadioGroup mRadioGroup;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    ConfirmMeetingAdapter mAdapter;
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
    List<Object> mDataLs;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyMeetingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.organ_activity_my_meeting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).titleBar(R.id.bar_title).init();

        initRecyclerView();
        //初始化骨架屏
        initSkeletonScreen();
        //监听赛选条件
        mRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
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

    }


    /**
     * 状态监听回调
     */
    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = (group, checkedId) -> {
        if (checkedId == R.id.rb_1) {//会议中
            paramMap.put("reqType", "2");
        } else if (checkedId == R.id.rb_2) {//未开会
            paramMap.put("reqType", "1");
        } else if (checkedId == R.id.rb_3) {//已结束
            paramMap.put("reqType", "3");
        }
        mRefreshLayout.autoRefresh();
    };


    /**
     * @description : TODO 下拉刷新监听
     * @author : yuanweiwei
     */
    private OnRefreshListener mOnRefreshListener = refreshLayout -> {
        mPresenter.queryMyMeetingPageList(paramMap, true);
    };

    /**
     * @description : TODO 上拉加载监听
     * @author : yuanweiwei
     */
    private OnLoadMoreListener mOnLoadMoreListener = refreshLayout -> {
        mPresenter.queryMyMeetingPageList(paramMap, false);
    };


    /**
     * @return
     * @description 初始化骨架屏
     * @date: 2020/8/10 16:56
     * @author: YIWUANYUAN
     */
    private void initSkeletonScreen() {
        paramMap.put("reqType", "2");
        //初始化数据
        mPresenter.queryMyMeetingPageList(paramMap, true);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int indext = 0; indext < fragmentManager.getFragments().size(); indext++) {
            Fragment fragment = fragmentManager.getFragments().get(indext); //找到第一层Fragment
            if (fragment == null)
                Timber.w("Activity result no fragment exists for index: 0x"
                        + Integer.toHexString(requestCode));
            else
                handleResult(fragment, requestCode, resultCode, data);
        }
    }

    /**
     * 递归调用，对所有的子Fragment生效
     *
     * @param fragment
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleResult(Fragment fragment, int requestCode, int resultCode, Intent data) {
        fragment.onActivityResult(requestCode, resultCode, data);//调用每个Fragment的onActivityResult
        Timber.w("handleResult");
        List<Fragment> childFragment = fragment.getChildFragmentManager().getFragments(); //找到第二层Fragment
        if (childFragment != null)
            for (Fragment f : childFragment)
                if (f != null) {
                    handleResult(f, requestCode, resultCode, data);
                }
        if (childFragment == null)
            Timber.e("MyBaseFragmentActivity1111");
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

    public Activity getActivity() {
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