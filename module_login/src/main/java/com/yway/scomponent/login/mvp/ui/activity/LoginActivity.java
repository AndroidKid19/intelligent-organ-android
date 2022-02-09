package com.yway.scomponent.login.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.blankj.utilcode.util.StringUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yway.scomponent.commonres.dialog.IToast;
import com.yway.scomponent.commonres.dialog.ProgresDialog;
import com.yway.scomponent.commonres.utils.InputTextHelper;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.commonsdk.utils.CacheUtils;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.login.R;
import com.yway.scomponent.login.R2;
import com.yway.scomponent.login.di.component.DaggerLoginComponent;
import com.yway.scomponent.login.mvp.contract.LoginHomeContract;
import com.yway.scomponent.login.mvp.presenter.LoginPresenter;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;
import static com.yway.scomponent.commonsdk.core.Constants.APP_USER_INFO;

/**
 * ================================================
 * 登录 Acivity
 * ================================================
 */
@Route(path = RouterHub.LOGGIN_LOGINACTIVITY)
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginHomeContract.View {
    /**
     * 手机号View
     **/
    @BindView(R2.id.et_login_phone)
    AppCompatEditText mEtLoginPhone;
    /**
     * 密码View
     **/
    @BindView(R2.id.et_login_pwd)
    AppCompatEditText mEtLoginPwd;
    /**
     * 协议View
     **/
    @BindView(R2.id.tv_login_agreement)
    AppCompatTextView mTvAgreement;
    /**
     * 注册View
     **/
    @BindView(R2.id.tv_register)
    AppCompatTextView mTvRegister;
    /**
     * 登录View
     **/
    @BindView(R2.id.btn_login_commit)
    View mBtnLoginCommit;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.login_activity_home;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //校验手机号密码，为输入手机号或密码禁用按钮状态
        InputTextHelper.with(this)
                .addView(mEtLoginPhone)
                .addView(mEtLoginPwd)
                .setMain(mBtnLoginCommit)
                .setListener(helper -> mEtLoginPhone.getText().toString().length() > 1)
                .build();

        //
        initAgreement();

        //初始化注册View
        initRegisterView();
    }

    /**
     * 初始化注册View
     */
    private void initRegisterView() {
        SpannableString spannableString = new SpannableString(getString(R.string.login_register_now));
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.public_color_text_sign));
        spannableString.setSpan(foregroundColorSpan, 6, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new Clickable(mOnRegisterClickListener1), 6, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvRegister.setText(spannableString);
        mTvRegister.setHighlightColor(Color.parseColor("#00FFFFFF"));
        mTvRegister.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
    }

    /**
     * @return void
     * @method initAgreement
     * @description 初始化用户协议
     * @date: 2020/12/1 10:12
     * @author: Yuan
     */
    private void initAgreement() {
        SpannableString spannableString = new SpannableString(getString(R.string.login_lable_agreement));
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.public_color_text_sign));
        spannableString.setSpan(foregroundColorSpan, 8, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new Clickable(mOnRegProtocolClickListener), 8, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        foregroundColorSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.public_color_text_sign));
        spannableString.setSpan(foregroundColorSpan, 13, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new Clickable(mOnAuthProtocolClickListener1), 13, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        mTvAgreement.setText(spannableString);
        mTvAgreement.setHighlightColor(Color.parseColor("#00FFFFFF"));
        mTvAgreement.setMovementMethod(LinkMovementMethod.getInstance());//必须设置才能响应点击事件
    }

    /**
     * 注册Click
     */
    private View.OnClickListener mOnRegisterClickListener1 = v -> {
        Utils.postcard(RouterHub.LOGGIN_REGISTERACTIVITY)
                .navigation(v.getContext());
    };


    /**
     * 注册协议
     */
    private View.OnClickListener mOnRegProtocolClickListener = v -> {
//            Utils.postcard(RouterHub.APP_AGENTWEBACTIVITY)
//                    .withString(RouterHub.PARAM_WEBVIEWXURL,RouterHub.H5_URL_REGISTRATIONPROTOCOL)
//                    .navigation(v.getContext());
    };

    /**
     * 授权协议
     */
    private View.OnClickListener mOnAuthProtocolClickListener1 = v -> {
//            Utils.postcard(RouterHub.APP_AGENTWEBACTIVITY)
//                    .withString(RouterHub.PARAM_WEBVIEWXURL,RouterHub.H5_URL_LICENSEAGREEMENT)
//                    .navigation(v.getContext());
    };

    /**
     * @param view 登录
     * @return void
     * @method onLoginClick
     * @description 登录
     * @date: 2020/12/1 9:27
     * @author: Yuan
     */
    @OnClick(R2.id.btn_login_commit)
    void onLoginClick(View view) {
        //01 获取手机号
        String mLoginPhone = mEtLoginPhone.getText().toString();
        //02 获取密码
        String mLoginPwd = mEtLoginPwd.getText().toString();
        //03 二次校验手机号是否为空
        if (StringUtils.isEmpty(mLoginPhone)) {
            IToast.showShort(getString(R.string.login_hint_input_phone), IToast.Type.WARN);
            return;
        }
        //04 二次校验密码是否为空
        if (StringUtils.isEmpty(mLoginPwd)) {
            IToast.showShort(getString(R.string.login_hint_input_pwd), IToast.Type.WARN);
            return;
        }
//
//        Utils.postcard(RouterHub.APP_MAINACTIVITY)
//                .withTransition(android.R.anim.fade_in, android.R.anim.fade_out)
//                .navigation(this, new NavCallback() {
//                    @Override
//                    public void onArrival(Postcard postcard) {
//                        finish();
//                    }
//                });

        //05 开始登录
        Map<String, String> params = new HashMap<>();
        params.put("loginName", mLoginPhone);
        params.put("password", mLoginPwd);
        mPresenter.login(params);

    }


    /**
     * @param view 忘记密码
     * @return void
     * @method onForgetPwdClick
     * @description 忘记密码
     * @date: 2020/12/1 9:27
     * @author: Yuan
     */
    @OnClick(R2.id.tv_forgetpwd)
    void onForgetPwdClick(View view) {
        Utils.postcard(RouterHub.LOGGIN_FORGETPWDACTIVITY)
                .navigation(this);
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
    public void showMessage(String message) {
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(Intent intent) {
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    /**
     * @param data token
     * @return
     * @description TODO 登录成功
     */
    @Override
    public void loginSuccess(UserInfoBean data) {
        IToast.showFinishShort("登录成功");
        CacheUtils.cacheToken(data.getToken());
        CacheUtils.cacheIsUserInfo(data);
        Utils.postcard(RouterHub.APP_MAINACTIVITY)
                .withTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                .navigation(this, new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {
                        finish();
                    }
                });
    }

    /**
     * @param msg error msg
     * @return
     * @description TODO 登录失败
     */
    @Override
    public void loginError(String msg) {
        IToast.showShort(msg, IToast.Type.ERROR);
    }

    @Override
    public void queryDoctorInfoSuccess(UserInfoBean data) {
        //缓存个人信息
        CacheUtils.initMMKV().encode(APP_USER_INFO, data);

        Utils.postcard(RouterHub.APP_MAINACTIVITY)
                .withTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                .navigation(this, new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {
                        finish();
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
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
