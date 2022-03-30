package com.yway.scomponent.organ.mvp.ui.activity;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.CollectionUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.base.BaseActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;

import com.jess.arms.utils.ArmsUtils;

import android.content.Intent;
import android.view.View;

import com.yway.scomponent.commonres.view.titlebar.OnTitleBarListener;
import com.yway.scomponent.commonres.view.titlebar.TitleBar;
import com.yway.scomponent.commonsdk.core.Constants;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.commonsdk.utils.CacheUtils;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.di.component.DaggerChooseUserComponent;
import com.yway.scomponent.organ.mvp.contract.ChooseUserContract;
import com.yway.scomponent.commonsdk.core.AddressCompanyBean;
import com.yway.scomponent.organ.mvp.presenter.ChooseUserPresenter;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.mvp.ui.adapter.AddressBookOrganAdapter;
import com.yway.scomponent.organ.mvp.ui.adapter.CheckedUserAdapter;
import com.yway.scomponent.organ.mvp.ui.adapter.ChooseUserAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 选择人员
 */
@Route(path = RouterHub.HOME_CHOOSEUSERACTIVITY)
public class ChooseUserActivity extends BaseActivity<ChooseUserPresenter> implements ChooseUserContract.View {

    /**
     * title
     */
    @BindView(R2.id.bar_title)
    TitleBar mTitleBar;
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
     * 选中人员列表
     */
    @BindView(R2.id.recycle_view_choose_user)
    RecyclerView mRecyclerViewCheckedUser;

    /**
     * 选中人员数量
     */
    @BindView(R2.id.tv_checked_count)
    AppCompatTextView mTvCheckedCount;

    /**
     * 选中Viewgroup
     */
    @BindView(R2.id.view_choose_user)
    View mViewCheckedUser;

    /**
     * 注入列表管理器
     */
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    AddressBookOrganAdapter mAddressBookOrganAdapter;

    @Inject
    ChooseUserAdapter mChooseUserAdapter;

    @Inject
    CheckedUserAdapter mCheckedUserAdapter;
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

    @Autowired(name = Constants.APP_USER_LIST)
    UserInfoBean mUserInfoBean;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerChooseUserComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.organ_activity_choose_user; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //状态栏沉浸
        ImmersionBar.with(this).titleBar(R.id.bar_title).statusBarDarkFont(true).init();
        //初始化title点击回调
        mTitleBar.setOnTitleBarListener(mOnTitleBarListener);
        //初始化recycleview
        initRecyclerView();
        //获取缓存的组织机构
        AddressCompanyBean data = CacheUtils.initMMKV().decodeParcelable(Constants.APP_USER_ORGAN, AddressCompanyBean.class);
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
            mChooseUserAdapter.notifyDataSetChanged();
        }

        //初始化已选择人员
        if (CollectionUtils.isNotEmpty(mUserInfoBean.getList())) {
            mCheckedUserAdapter.addAllCheckedUser(mUserInfoBean.getList());
            initCheckedUserData();
        }
    }

    /**
     * title 回调
     */
    private OnTitleBarListener mOnTitleBarListener = new OnTitleBarListener() {
        @Override
        public void onLeftClick(View v) {

        }

        @Override
        public void onTitleClick(View v) {

        }

        @Override
        public void onRightClick(View v) {
            Intent intent = new Intent();
            //已选择人员
            ArrayList<UserInfoBean> userInfoBeanList = (ArrayList<UserInfoBean>) mCheckedUserAdapter.getInfos();
            intent.putParcelableArrayListExtra("userInfoBeans", userInfoBeanList);
            //设置参数
            getActivity().setResult(RESULT_OK, intent);
            finish();
        }
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
        mRecyclerViewParts.setAdapter(mChooseUserAdapter);
        //设置部门及人员事件监听
        mChooseUserAdapter.setOnItemClickListener(mOnAddressBookPartsItemClickListener);

        //初始化选中人员
        ArmsUtils.configRecyclerView(mRecyclerViewCheckedUser, new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewCheckedUser.setAdapter(mCheckedUserAdapter);
        mCheckedUserAdapter.setOnItemClickListener(mOnRecyclerViewItemClickListener);
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
            //初始化组织机构数据
            initOrganData(companyBean);

            //移动到最后一个位置
            mRecyclerViewOrgan.smoothScrollToPosition(mAddressBookOrganAdapter.getItemCount() - 1);

        } else if (data instanceof UserInfoBean) {
            UserInfoBean userInfoBean = (UserInfoBean) data;
            if (userInfoBean.isChecked() == false) {
                //选中
                mCheckedUserAdapter.addCheckedUser(userInfoBean);
            } else {
                //反选
                mCheckedUserAdapter.removeCheckedUser(userInfoBean);
            }
            //人员选中/反选
            mChooseUserAdapter.checkedUser(position);
            //初始化已选择人员数据
            initCheckedUserData();

        }
    };

    /**
     * 初始化已选择人员数据
     */
    private void initCheckedUserData() {
        //校验是否选中了人员，如果选中人员则展示选中view
        if (mCheckedUserAdapter.getItemCount() > 0) {
//            mViewCheckedUser.setVisibility(View.VISIBLE);
            mTvCheckedCount.setText(Utils.appendStr("已选择(", mCheckedUserAdapter.getItemCount(), "):"));
        } else {
//            mViewCheckedUser.setVisibility(View.GONE);
        }
    }


    /**
     * 部门快捷筛选事件回调
     */
    private DefaultAdapter.OnRecyclerViewItemClickListener mOnAddressBookOrganItemClickListener = (view, viewType, data, position) -> {
        AddressCompanyBean addressCompanyBean = (AddressCompanyBean) data;
        if (addressCompanyBean.getFlag() == 0) {
            return;
        }
        //获取当前组织机构数据
        initOrganData(addressCompanyBean);
        //删除除自己之后的组织机构
        mAddressBookOrganAdapter.removeOrgan(position);
    };

    /**
     * 已选择人员点击事件监听回调
     */
    private DefaultAdapter.OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener = (view, viewType, data, position) -> {
        mCheckedUserAdapter.removeCheckedUser((UserInfoBean) data);
        mChooseUserAdapter.removeChecked((UserInfoBean) data);
        //初始化已选择人员数据
        initCheckedUserData();
    };


    /**
     * 组织机构数据展示
     */
    private void initOrganData(AddressCompanyBean companyBean) {
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
            //获取选中的用户
            List<UserInfoBean> userInfoBeans = mCheckedUserAdapter.getInfos();
            //标记选中
            for (int i = 0; i < listUser.size(); i++) {
                boolean isChecked = false;
                for (int j = 0; j < userInfoBeans.size(); j++) {
                    if (listUser.get(i).getUserId().equals(userInfoBeans.get(j).getUserId())) {
                        isChecked = true;
                    }
                }
                if (isChecked) {
                    listUser.get(i).setChecked(true);
                } else {
                    listUser.get(i).setChecked(false);
                }
            }
            mObjectDataLs.addAll(listUser);
        }
        //刷新
        mChooseUserAdapter.notifyDataSetChanged();
        //初始化已选择人员数据
        initCheckedUserData();
    }

    @Override
    public void onBackPressed() {
        int position = mAddressBookOrganAdapter.getItemCount() - 2;
        if (position < 0) {
            super.onBackPressed();
        } else {
            //获取上一级组织机构
            AddressCompanyBean addressCompanyBean = mAddressBookOrganAdapter.getInfos().get(position);
            if (addressCompanyBean.getFlag() == 0) {
                return;
            }
            //获取当前组织机构数据
            initOrganData(addressCompanyBean);
            //删除除自己之后的组织机构
            mAddressBookOrganAdapter.removeOrgan(position);
        }
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
}