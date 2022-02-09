package com.yway.scomponent.user.mvp.ui.activity;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.base.BaseActivity;
import static com.jess.arms.utils.Preconditions.checkNotNull;
import com.jess.arms.utils.ArmsUtils;
import android.content.Intent;

import com.yway.scomponent.commonres.view.layout.NiceImageView;
import com.yway.scomponent.commonres.view.layout.SettingBar;
import com.yway.scomponent.commonsdk.core.Constants;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.user.R2;
import com.yway.scomponent.user.di.component.DaggerUserDetailsComponent;
import com.yway.scomponent.user.mvp.contract.UserDetailsContract;
import com.yway.scomponent.user.mvp.presenter.UserDetailsPresenter;
import com.yway.scomponent.user.R;

import butterknife.BindView;

/**
 * 个人信息
 */
@Route(path = RouterHub.USER_USERDETAILSACTIVITY)
public class UserDetailsActivity extends BaseActivity<UserDetailsPresenter> implements UserDetailsContract.View {

    /**
     * 头像
     */
    @BindView(R2.id.niv_head)
    NiceImageView mNiceImageView;
    /**
     * 姓名
     */
    @BindView(R2.id.tv_username)
    AppCompatTextView mTvUserName;
    /**
     * 岗位
     */
    @BindView(R2.id.tv_jop)
    AppCompatTextView mTvUserOffice;
    /**
     * 部门
     */
    @BindView(R2.id.bar_class)
    SettingBar mBarClass;
    /**
     * 电话
     */
    @BindView(R2.id.bar_phone)
    SettingBar mBarPhone;


    @Autowired(name = Constants.APP_USER_INFO)
    UserInfoBean mUserInfoBean;

    @Autowired(name = Constants.APP_USER_ORGAN)
    String mOrganName;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUserDetailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.user_activity_user_details; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.imageLoader(mUserInfoBean.getSysUserFilePath(),mNiceImageView);
        mTvUserName.setText(mUserInfoBean.getNickName());
        mBarPhone.setLeftText(mUserInfoBean.getCellPhone());
        mBarClass.setLeftText(mOrganName);
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