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
import com.blankj.utilcode.util.CollectionUtils;
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
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.commonsdk.utils.CacheUtils;
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
 * ??????
 */
@Route(path = RouterHub.LOGGIN_REGISTERACTIVITY)
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {
    @BindView(R2.id.bar_title)
    TitleBar mBarTitle;
    /**
     * ??????View
     **/
    @BindView(R2.id.et_username)
    AppCompatEditText mEtUserName;
    /**
     * ?????????View
     **/
    @BindView(R2.id.et_phone)
    AppCompatEditText mPhoneView;
    /**
     * ???????????????View
     **/
    @BindView(R2.id.cv_register_countdown)
    CountdownView mCountdownView;
    /**
     * ?????????View
     **/
    @BindView(R2.id.et_code)
    AppCompatEditText mEtCode;
    /**
     * ??????View
     **/
    @BindView(R2.id.et_pwd)
    AppCompatEditText mEtPwd;
    /**
     * ??????View
     **/
    @BindView(R2.id.btn_commit)
    View mBtnCommit;
    /**
     * ??????View
     **/
    @BindView(R2.id.tv_login)
    AppCompatTextView mTvLogin;
    /**
     * ??????????????????
     */
    @BindView(R2.id.bar_choose_company)
    SettingBar mBarChooseCompany;

    /**
     * ????????????
     * */
    private AddressCompanyBean companyBean;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRegisterComponent //??????????????????,?????????????????????
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.user_activity_register; //???????????????????????????????????? setContentView(id) ??????????????????,????????? 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //???????????????
        ImmersionBar.with(this)
                .titleBar(R.id.bar_title)
                .keyboardEnable(true)
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED) //???????????????????????????
                .init();
        //?????????????????????????????????????????????????????????????????????
        InputTextHelper.with(this)
                .addView(mEtUserName)
                .addView(mPhoneView)
                .addView(mEtPwd)
                .setMain(mBtnCommit)
                .setListener(helper -> mPhoneView.getText().toString().length() > 1)
                .build();

        //???????????????View
        initRegisterView();
        //?????????????????????
        mPresenter.queryAllSysOrgAndSysUserList();
    }

    /**
     * ???????????????View
     */
    private void initRegisterView() {
        SpannableString spannableString = new SpannableString(getString(R.string.login_use_login_now));
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.public_color_text_sign));
        spannableString.setSpan(foregroundColorSpan, 6, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new Clickable(mOnRegisterClickListener), 6, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvLogin.setText(spannableString);
        mTvLogin.setHighlightColor(Color.parseColor("#00FFFFFF"));
        mTvLogin.setMovementMethod(LinkMovementMethod.getInstance());//????????????????????????????????????
    }

    /**
     * ??????Click
     */
    private View.OnClickListener mOnRegisterClickListener = v -> {
        finish();
    };

    /**
     * ???????????????????????????
     */
    @OnClick(R2.id.cv_register_countdown)
    void onSendCodeClick(View v) {
        String mPhone = mPhoneView.getText().toString();
        // ???????????????
        if (mPhone.length() <= 0) {
            // ??????????????????????????????
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
     * ??????
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
        mapParams.put("orgId", companyBean.getOrgId());

        mPresenter.register(mapParams);
    }

    /**
     * ??????????????????
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
                    companyBean = data.getParcelableExtra("addressCompanyBean");
                    //???????????????????????????
                    mBarChooseCompany.setLeftText(companyBean.getOrgTitle());
                }
                break;

        }
    }
    @Override
    public void queryOrgRspCallBack(AddressCompanyBean data) {
        //??????????????????
        CacheUtils.initMMKV().encode(Constants.APP_USER_ORGAN, data);
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
        IToast.showFinishShort("????????????");
    }

    @Override
    public void registerSuccess(String msg) {
        IToast.showFinishShort("??????????????????????????????");
        finish();
    }

    /**
     * ???????????????????????????????????????????????????
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
            ds.setUnderlineText(false);    //???????????????????????????
        }
    }
}