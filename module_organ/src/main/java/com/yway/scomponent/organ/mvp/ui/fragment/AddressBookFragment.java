package com.yway.scomponent.organ.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.CollectionUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yway.scomponent.commonsdk.core.Constants;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.commonsdk.utils.CacheUtils;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.di.component.DaggerAddressBookComponent;
import com.yway.scomponent.organ.mvp.contract.AddressBookContract;
import com.yway.scomponent.commonsdk.core.AddressCompanyBean;
import com.yway.scomponent.organ.mvp.presenter.AddressBookPresenter;
import com.yway.scomponent.organ.mvp.ui.adapter.AddressBookOrganAdapter;
import com.yway.scomponent.organ.mvp.ui.adapter.AddressBookPartsAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 通讯录 - 用户
 */
public class AddressBookFragment extends BaseFragment<AddressBookPresenter> implements AddressBookContract.View {

    /**
     * 下拉刷新View
     */
    @BindView(R2.id.refresh_layout)
    RefreshLayout mRefreshLayout;

    /**
     * 通讯录组织机构选择
     */
    @BindView(R2.id.recycle_view_organ)
    RecyclerView mRecyclerViewOrgan;
    /**
     * 通讯录列表
     */
    @BindView(R2.id.recycle_view_parts)
    RecyclerView mRecyclerViewParts;
    /**
     * 注入列表管理器
     */
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    AddressBookOrganAdapter mAddressBookOrganAdapter;

    @Inject
    AddressBookPartsAdapter mAddressBookPartsAdapter;
    /**
     * 注入列表数据源
     */
    @Inject
    List<AddressCompanyBean> mDataLs;
    /**
     * 注入列表数据源
     */
    @Inject
    List<Object> mObjectDataLs;

    public static AddressBookFragment newInstance() {
        AddressBookFragment fragment = new AddressBookFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerAddressBookComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.organ_fragment_address_book, container, false);
    }

    /**
     * 在 onActivityCreate()时调用
     */
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //状态栏沉浸
        ImmersionBar.with(this).titleBar(R.id.view_search).statusBarDarkFont(true).init();
        initRecyclerView();
        mPresenter.queryAllSysOrgAndSysUserList();
        //设置下拉刷新监听
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
    }

    /**
     * @description : TODO 下拉刷新监听
     * @author : yuanweiwei
     */
    private OnRefreshListener mOnRefreshListener = refreshLayout -> {
        mPresenter.queryAllSysOrgAndSysUserList();
    };

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        //初始化人员选择
        ArmsUtils.configRecyclerView(mRecyclerViewOrgan, mLayoutManager);
        mRecyclerViewOrgan.setAdapter(mAddressBookOrganAdapter);
        //设置部门快捷筛选事件回调
        mAddressBookOrganAdapter.setOnItemClickListener(mOnAddressBookOrganItemClickListener);

        //初始化部门及人员
        ArmsUtils.configRecyclerView(mRecyclerViewParts, new LinearLayoutManager(getActivity()));
        mRecyclerViewParts.setAdapter(mAddressBookPartsAdapter);
        //设置部门及人员事件监听
        mAddressBookPartsAdapter.setOnItemClickListener(mOnAddressBookPartsItemClickListener);
    }


    /**
     * 组织机构及人员事件回调监听
     */
    private DefaultAdapter.OnRecyclerViewItemClickListener mOnAddressBookPartsItemClickListener = (view, viewType, data, position) -> {
        if (data instanceof AddressCompanyBean) {//根据items数据类型的不同来判断他是标题还是数据项
            AddressCompanyBean companyBean = (AddressCompanyBean) data;
            //设置部门快捷筛选事件回调
            companyBean.setFlag(0);
            mAddressBookOrganAdapter.addOrgan(companyBean);

            //获取当前组织机构数据
            List<AddressCompanyBean> listOrg = mPresenter.queryCompany(companyBean.getOrgId());
            //清空当前组织机构
            mObjectDataLs.clear();
            //重新组织通讯录
            if (CollectionUtils.isNotEmpty(listOrg)) {
                mObjectDataLs.addAll(listOrg);
            }

            //默认获取一级通讯录人员数据
            List<UserInfoBean> listUser = mPresenter.queryCompanyUser(companyBean.getOrgId());
            if (CollectionUtils.isNotEmpty(listUser)) {
                mObjectDataLs.addAll(listUser);
            }
            //刷新
            mAddressBookPartsAdapter.notifyDataSetChanged();

            mRecyclerViewOrgan.smoothScrollToPosition(mAddressBookOrganAdapter.getItemCount() - 1);

        } else if (data instanceof UserInfoBean) {
            //人员
            Utils.postcard(RouterHub.USER_USERDETAILSACTIVITY)
                    .withParcelable(Constants.APP_USER_INFO, (UserInfoBean) data)
                    .withString("organName", mDataLs.get(mDataLs.size() - 1).getOrgTitle())
                    .navigation(getActivity());
        }
    };


    /**
     * 部门快捷筛选事件回调
     */
    private DefaultAdapter.OnRecyclerViewItemClickListener mOnAddressBookOrganItemClickListener = (view, viewType, data, position) -> {
        AddressCompanyBean addressCompanyBean = (AddressCompanyBean) data;
        if (addressCompanyBean.getFlag() == 0) {
            return;
        }
        //获取当前组织机构数据
        List<AddressCompanyBean> listOrg = mPresenter.queryCompany(addressCompanyBean.getOrgId());
        ////清空当前组织机构
        mObjectDataLs.clear();
        //重新组织通讯录
        if (CollectionUtils.isNotEmpty(listOrg)) {
            mObjectDataLs.addAll(listOrg);
        }

        //默认获取一级通讯录人员数据
        List<UserInfoBean> listUser = mPresenter.queryCompanyUser(addressCompanyBean.getOrgId());
        if (CollectionUtils.isNotEmpty(listUser)) {
            mObjectDataLs.addAll(listUser);
        }
        //刷新
        mAddressBookPartsAdapter.notifyDataSetChanged();
        //删除除自己之后的组织机构
        mAddressBookOrganAdapter.removeOrgan(position);

    };


    @Override
    public void setData(@Nullable Object data) {

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

    public Fragment getFragment() {
        return this;
    }

    @Override
    public void queryOrgRspCallBack(AddressCompanyBean data) {
        //缓存组织机构
        CacheUtils.initMMKV().encode(Constants.APP_USER_ORGAN, data);

        //缓存用户信息
        if (CollectionUtils.isNotEmpty(data.getSysUserRspBOList())) {
            mPresenter.setSysUserRspBOList(data.getSysUserRspBOList());
        }

        if (CollectionUtils.isNotEmpty(data.getSysOrgRspBOList())) {
            //缓存组织信息
            mPresenter.setSysOrgRspBOList(data.getSysOrgRspBOList());
            //获取默认第一个组织信息
            AddressCompanyBean organInfo = data.getSysOrgRspBOList().get(0);
            mDataLs.clear();
            //默认初始化头部快捷组织信息
            organInfo.setFlag(0);
            mDataLs.add(organInfo);
            mAddressBookOrganAdapter.notifyDataSetChanged();

            //默认获取一级通讯录数据
            List<AddressCompanyBean> listOrg = mPresenter.queryCompany(organInfo.getOrgId());
            //清空通讯录
            mObjectDataLs.clear();
            //重新组织通讯录
            if (CollectionUtils.isNotEmpty(listOrg)) {
                mObjectDataLs.addAll(listOrg);
            }

            //默认获取一级通讯录人员数据
            List<UserInfoBean> listUser = mPresenter.queryCompanyUser(organInfo.getOrgId());
            if (CollectionUtils.isNotEmpty(listUser)) {
                mObjectDataLs.addAll(listUser);
            }
            mAddressBookPartsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public RefreshLayout refreshLayout() {
        return mRefreshLayout;
    }
}