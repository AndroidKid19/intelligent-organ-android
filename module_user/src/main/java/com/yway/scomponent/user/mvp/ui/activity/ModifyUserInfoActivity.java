package com.yway.scomponent.user.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.yway.scomponent.commonres.dialog.IToast;
import com.yway.scomponent.commonres.dialog.ProgresDialog;
import com.yway.scomponent.commonres.utils.InputTextHelper;
import com.yway.scomponent.commonres.view.layout.ClearEditText;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.commonsdk.utils.CacheUtils;
import com.yway.scomponent.user.R2;
import com.yway.scomponent.user.di.component.DaggerModifyUserInfoComponent;
import com.yway.scomponent.user.mvp.contract.ModifyUserInfoContract;
import com.yway.scomponent.user.mvp.presenter.ModifyUserInfoPresenter;

import com.yway.scomponent.user.R;


import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * 修改个人信息
 * ================================================
 */
@Route(path = RouterHub.USER_MODIFYUSERINFOACTIVITY)
public class ModifyUserInfoActivity extends BaseActivity<ModifyUserInfoPresenter> implements ModifyUserInfoContract.View {

    @BindView(R2.id.et_modify_text)
    ClearEditText mEtModifyText;
    /**
     * 提交View
     **/
    @BindView(R2.id.btn_commit)
    View mBtnCommit;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerModifyUserInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.user_activity_modify_user_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).titleBar(R.id.bar_title).init();
        InputTextHelper.with(this)
                .addView(mEtModifyText)
                .setMain(mBtnCommit)
                .setListener(helper -> mEtModifyText.getText().toString().length() > 1)
                .build();

        mEtModifyText.setText(CacheUtils.queryNickName());
    }

    /**
     * 提交
     */
    @OnClick(R2.id.btn_commit)
    public void onRegisterClick(View v) {
        String mNickName = mEtModifyText.getText().toString();
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("nickName", mNickName);
        mPresenter.modifyAppUserInfoById(mapParams);

    }

    @Override
    public void showLoading() {
        ProgresDialog.getInstance(this).show();
    }

    @Override
    public void hideLoading() {
        ProgresDialog.getInstance(this).dismissDialog();
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
        finish();
    }

    @Override
    public void modifySuccess() {
        UserInfoBean userInfoBean = CacheUtils.queryUserInfo();
        userInfoBean.setNickName(mEtModifyText.getText().toString());
        //更新缓存昵称
        CacheUtils.cacheIsUserInfo(userInfoBean);
        IToast.showFinishShort("修改成功");
        finish();
    }
}
