package com.yway.scomponent.user.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yway.scomponent.commonres.dialog.IToast;
import com.yway.scomponent.commonres.dialog.ProgresDialog;
import com.yway.scomponent.commonres.utils.InputTextHelper;
import com.yway.scomponent.commonres.view.layout.ClearEditText;
import com.yway.scomponent.commonres.view.layout.CountdownView;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.utils.CacheUtils;
import com.yway.scomponent.user.R;
import com.yway.scomponent.user.R2;
import com.yway.scomponent.user.di.component.DaggerModifyPhoneComponent;
import com.yway.scomponent.user.mvp.contract.ModifyPhoneContract;
import com.yway.scomponent.user.mvp.presenter.ModifyPhonePresenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 修改手机号
 * ================================================
 */
@Route(path = RouterHub.USER_MODIFYPHONEACTIVITY)
public class ModifyPhoneActivity extends BaseActivity<ModifyPhonePresenter> implements ModifyPhoneContract.View {

    @BindView(R2.id.et_modify_phone)
    ClearEditText mEtModifyPhone;
    @BindView(R2.id.et_sms_code)
    ClearEditText mEtSmsCode;
    @BindView(R2.id.tv_mobile)
    AppCompatTextView mTvMobile;
    @BindView(R2.id.btn_commit)
    View mBtnCommit;
    /**
     * 发送验证码View
     **/
    @BindView(R2.id.cv_password_forget_countdown)
    CountdownView mCountdownView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerModifyPhoneComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.user_activity_modify_phone; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).titleBar(R.id.bar_title).init();

        InputTextHelper.with(this)
                .addView(mEtModifyPhone)
                .addView(mEtSmsCode)
                .setMain(mBtnCommit)
                .setListener(helper -> mEtModifyPhone.getText().toString().length() > 1)
                .build();

        mTvMobile.setText(CacheUtils.queryCellPhone());
    }

    /**
     * 获取验证码点击事件
     */
    @OnClick(R2.id.cv_password_forget_countdown)
    void onSendCodeClick(View v) {
        String mPhone = mEtModifyPhone.getText().toString();
        // 获取验证码
        if (mPhone.length() <= 0) {
            // 重置验证码倒计时控件
            mCountdownView.resetState();
            IToast.showErrorShort(getString(R.string.user_hint_input_phone));
            return;
        }
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("mobile", mPhone);
        mapParams.put("type", 1);
        mPresenter.sendSms(mapParams);
    }

    /**
     * 修改手机号
     */
    @OnClick(R2.id.btn_commit)
    public void onRegisterClick(View v) {
        String mLoginPhone = mEtModifyPhone.getText().toString();
        String mCountryCode = mEtSmsCode.getText().toString();

        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("cellPhone", mLoginPhone);
        mapParams.put("mobileCode", mCountryCode);
        mPresenter.modifyCellPhoneByUserId(mapParams);

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
    public void modifySuccess() {
        IToast.showFinishShort("修改成功");
    }
}
