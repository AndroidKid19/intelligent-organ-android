package com.yway.scomponent.login.mvp.ui.activity;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.UriUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.base.BaseActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;

import com.jess.arms.utils.ArmsUtils;

import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;

import com.yway.scomponent.commonres.dialog.IToast;
import com.yway.scomponent.commonres.dialog.ProgresDialog;
import com.yway.scomponent.commonres.utils.InputTextHelper;
import com.yway.scomponent.commonres.view.layout.CountdownView;
import com.yway.scomponent.commonres.view.layout.SettingBar;
import com.yway.scomponent.commonres.view.titlebar.TitleBar;
import com.yway.scomponent.commonsdk.core.AddressCompanyBean;
import com.yway.scomponent.commonsdk.core.Constants;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.utils.FileUtils;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.login.R2;
import com.yway.scomponent.login.di.component.DaggerRegisterComponent;
import com.yway.scomponent.login.mvp.contract.RegisterContract;
import com.yway.scomponent.login.mvp.presenter.RegisterPresenter;
import com.yway.scomponent.login.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * 注册
 */
@Route(path = RouterHub.LOGGIN_REGISTERACTIVITY)
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {
    @BindView(R2.id.bar_title)
    TitleBar mBarTitle;
    /**
     * 昵称View
     **/
    @BindView(R2.id.et_username)
    AppCompatEditText mEtUserName;
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
     * 提交View
     **/
    @BindView(R2.id.btn_commit)
    View mBtnCommit;
    /**
     * 注册View
     **/
    @BindView(R2.id.tv_login)
    AppCompatTextView mTvLogin;
    /**
     * 选择参会单位
     */
    @BindView(R2.id.bar_choose_company)
    SettingBar mBarChooseCompany;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRegisterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.user_activity_register; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //状态栏沉浸
        ImmersionBar.with(this)
                .titleBar(R.id.bar_title)
                .keyboardEnable(true)
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED) //单独指定软键盘模式
                .init();
        //校验手机号密码，为输入手机号或密码禁用按钮状态
        InputTextHelper.with(this)
                .addView(mEtUserName)
                .addView(mPhoneView)
                .addView(mEtPwd)
                .setMain(mBtnCommit)
                .setListener(helper -> mPhoneView.getText().toString().length() > 1)
                .build();

        //初始化注册View
        initRegisterView();
    }

    /**
     * 初始化注册View
     */
    private void initRegisterView() {
        SpannableString spannableString = new SpannableString(getString(R.string.login_use_login_now));
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.public_color_text_sign));
        spannableString.setSpan(foregroundColorSpan, 6, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new Clickable(mOnRegisterClickListener), 6, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvLogin.setText(spannableString);
        mTvLogin.setHighlightColor(Color.parseColor("#00FFFFFF"));
        mTvLogin.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
    }

    /**
     * 登录Click
     */
    private View.OnClickListener mOnRegisterClickListener = v -> {
        finish();
    };

    /**
     * 获取验证码点击事件
     */
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

    /**
     * 注册
     */
    @OnClick(R2.id.btn_commit)
    public void onRegisterClick(View v) {
        String mUserName = mEtUserName.getText().toString();
        String mLoginPhone = mPhoneView.getText().toString();
        String mCountryCode = mEtCode.getText().toString();
        String mPassword = mEtPwd.getText().toString();

        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("nickName", mUserName);
        mapParams.put("loginName", mLoginPhone);
        mapParams.put("cellPhone", mLoginPhone);
        mapParams.put("password", mPassword);
        mapParams.put("mobileCode", mCountryCode);
        mPresenter.register(mapParams);

    }

    /**
     * 选择参会单位
     */
    @OnClick(R2.id.bar_choose_company)
    public void onBarChooseCompanyClick(View view) {
        Utils.postcard(RouterHub.HOME_CHOOSECOMPANYACTIVITY)
                .navigation(getActivity(), Constants.RESULT_CHOOSE_COMPANY_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RESULT_CHOOSE_COMPANY_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    AddressCompanyBean companyBean = data.getParcelableExtra("addressCompanyBean");
                    //获取已选择用户信息
                    mBarChooseCompany.setRightText(companyBean.getOrgTitle());
                }
                break;

        }
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

    }

    public Activity getActivity() {
        return this;
    }

    @Override
    public void sendSmsSuccess() {
        IToast.showFinishShort("发送成功");
    }

    @Override
    public void registerSuccess(String msg) {
        IToast.showFinishShort("修改密码成功，请登录");
        finish();
    }

    /**
     * 内部类，用于截获点击富文本后的事件
     */
    class Clickable extends ClickableSpan implements View.OnClickListener {
        private final View.OnClickListener mListener;

        public Clickable(View.OnClickListener mListener) {
            this.mListener = mListener;
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);    //去除超链接的下划线
        }
    }
}