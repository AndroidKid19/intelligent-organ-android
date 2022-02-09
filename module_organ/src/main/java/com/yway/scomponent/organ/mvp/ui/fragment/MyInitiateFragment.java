package com.yway.scomponent.organ.mvp.ui.fragment;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ethanhua.skeleton.SkeletonScreen;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;

import static com.jess.arms.utils.Preconditions.checkNotNull;

import com.jess.arms.utils.ArmsUtils;

import android.content.Intent;
import android.widget.RadioGroup;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yway.scomponent.commonres.dialog.ProgresDialog;
import com.yway.scomponent.commonres.view.layout.MultipleStatusView;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.di.component.DaggerMyInitiateComponent;
import com.yway.scomponent.organ.mvp.contract.MyInitiateContract;
import com.yway.scomponent.organ.mvp.model.entity.ConferenceBean;
import com.yway.scomponent.organ.mvp.presenter.MyInitiatePresenter;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.mvp.ui.adapter.ApprovedAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 我发起的
 */
public class MyInitiateFragment extends BaseFragment<MyInitiatePresenter> implements MyInitiateContract.View {

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
    /**赛选条件**/
    @BindView(R2.id.rg_condition)
    RadioGroup mRadioGroup;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    ApprovedAdapter mAdapter;

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

    public static MyInitiateFragment newInstance() {
        MyInitiateFragment fragment = new MyInitiateFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMyInitiateComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.organ_fragment_my_initiate, container, false);
    }

    /**
     * 在 onActivityCreate()时调用
     */
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
        //初始化骨架屏
//        initSkeletonScreen();
        //监听赛选条件
        mRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }


    /**
     * 状态监听回调
     */
    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = (group, checkedId) -> {
        if (checkedId == R.id.rb_1){//全部
        }else if(checkedId == R.id.rb_2){//未审批
        }else if(checkedId == R.id.rb_3){//通过
        }else if(checkedId == R.id.rb_4){//驳回
        }
//        mRefreshLayout.autoRefresh();
    };



    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        ConferenceBean conferenceBean = new ConferenceBean();
        conferenceBean.setConfIng(true);
        mDataLs.add(conferenceBean);
        conferenceBean = new ConferenceBean();
        mDataLs.add(conferenceBean);
        conferenceBean = new ConferenceBean();
        mDataLs.add(conferenceBean);

        //设置下拉刷新监听
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mRefreshLayout.setOnLoadMoreListener(mOnLoadMoreListener);

    }


    /**
     * @description : TODO 下拉刷新监听
     * @author : yuanweiwei
     */
    private OnRefreshListener mOnRefreshListener = refreshLayout -> {
//        mPresenter.queryReportExceptionRecordPageByReportTypeList(paramMap, true);
    };

    /**
     * @description : TODO 上拉加载监听
     * @author : yuanweiwei
     */
    private OnLoadMoreListener mOnLoadMoreListener = refreshLayout -> {
//        mPresenter.queryReportExceptionRecordPageByReportTypeList(paramMap, false);
    };


    /**
     * @return
     * @description 初始化骨架屏
     * @date: 2020/8/10 16:56
     * @author: YIWUANYUAN
     */
    private void initSkeletonScreen() {
//        paramMap.put("treatStatus", 0);
//        //初始化数据
////        mPresenter.queryReportExceptionRecordPageByReportTypeList(paramMap, true);
//        mSkeletonScreen = Skeleton.bind(mRecyclerView)
//                .adapter(mAdapter)
//                .shimmer(true)
//                .angle(20)
//                .frozen(false)
//                .duration(1200)
//                .count(15)
//                .load(R.layout.organ_item_skeleton_metting)
//                .show(); //default count is 10
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
}