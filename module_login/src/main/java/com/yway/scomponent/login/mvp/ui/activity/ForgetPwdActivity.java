package com.yway.scomponent.login.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yway.scomponent.commonres.dialog.IToast;
import com.yway.scomponent.commonres.dialog.ProgresDialog;
import com.yway.scomponent.commonres.utils.InputTextHelper;
import com.yway.scomponent.commonres.view.layout.CountdownView;
import com.yway.scomponent.commonres.view.titlebar.TitleBar;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.login.R;
import com.yway.scomponent.login.R2;
import com.yway.scomponent.login.di.component.DaggerForgetPwdComponent;
import com.yway.scomponent.login.mvp.contract.ForgetPwdContract;
import com.yway.scomponent.login.mvp.presenter.ForgetPwdPresenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * ================================================
 * Description: 忘记密码
 * ================================================
 */
@Route(path = RouterHub.LOGGIN_FORGETPWDACTIVITY)
public class ForgetPwdActivity extends BaseActivity<ForgetPwdPresenter> implements ForgetPwdContract.View {

    @BindView(R2.id.bar_title)
    TitleBar mBarTitle;
    /**
     * 手机号View
     **/
    @BindView(R2.id.et_phone)
    AppCompatEditText mPhoneView;
    /**
     * 发送验证码View
     **/
    @BindView(R2.id.cv_register_countdown)
    CountdownView mCountdownView;
    /**
     * 验证码View
     **/
    @BindView(R2.id.et_code)
    AppCompatEditText mEtCode;
    /**
     * 密码View
     **/
    @BindView(R2.id.et_pwd)
    AppCompatEditText mEtPwd;
    /**
     * 密码View
     **/
    @BindView(R2.id.et_pwd_new)
    AppCompatEditText mEtPwdNew;
    /**
     * 提交View
     **/
    @BindView(R2.id.btn_commit)
    View mBtnCommit;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerForgetPwdComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.login_activity_forget_pwd; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //状态栏沉浸
        ImmersionBar.with(this).titleBar(R.id.bar_title).init();
        //校验手机号密码，为输入手机号或密码禁用按钮状态
        InputTextHelper.with(this)
                .addView(mPhoneView)
                .addView(mEtPwd)
                .addView(mEtPwdNew)
                .setMain(mBtnCommit)
                .setListener(helper -> mPhoneView.getText().toString().length() > 1)
                .build();
    }

    @OnClick(R2.id.cv_register_countdown)
    void onSendCodeClick(View v) {
        String mPhone = mPhoneView.getText().toString();
        // 获取验证码
        if (mPhone.length() <= 0) {
            // 重置验证码倒计时控件
            mCountdownView.resetState();
            IToast.showErrorShort(getString(R.string.login_hint_input_phone));
            return;
        }
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("mobile", mPhone);
        mapParams.put("type", 1);
        mPresenter.sendSms(mapParams);
    }


    @OnClick(R2.id.btn_commit)
    public void onRegisterClick(View v) {
        String mLoginPhone = mPhoneView.getText().toString();
        String mCountryCode = mEtCode.getText().toString();
        String mPassword = mEtPwd.getText().toString();
        String mPasswordNew = mEtPwdNew.getText().toString();
        if (!mPassword.equals(mPasswordNew)){
            IToast.showErrorShort("两次密码不一致");
            return;
        }
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("cellPhone", mLoginPhone);
        mapParams.put("password", mPassword);
        mapParams.put("mobileCode", mCountryCode);
        mPresenter.modifyForgetPasswordByPhone(mapParams);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
    public void sendSmsSuccess() {
        IToast.showFinishShort("发送成功");
    }

    @Override
    public void modifyForgetPasswordSuccess() {
        IToast.showFinishShort("修改成功,请登录");
        finish();
    }
}
