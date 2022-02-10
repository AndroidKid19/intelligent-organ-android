package com.yway.scomponent.user.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.blankj.utilcode.util.ActivityUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.yway.scomponent.commonres.dialog.IToast;
import com.yway.scomponent.commonres.dialog.MessageDialog;
import com.yway.scomponent.commonres.dialog.ProgresDialog;
import com.yway.scomponent.commonres.dialog.listener.OnViewItemClickListener;
import com.yway.scomponent.commonres.utils.InputTextHelper;
import com.yway.scomponent.commonres.view.layout.ClearEditText;
import com.yway.scomponent.commonres.view.layout.PasswordEditText;
import com.yway.scomponent.commonsdk.core.ParamsHelper;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.utils.CacheUtils;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.user.R2;
import com.yway.scomponent.user.di.component.DaggerModifyPwdComponent;
import com.yway.scomponent.user.mvp.contract.ModifyPwdContract;
import com.yway.scomponent.user.mvp.model.api.Api;
import com.yway.scomponent.user.mvp.presenter.ModifyPwdPresenter;

import com.yway.scomponent.user.R;


import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * ================================================
 */
@Route(path = RouterHub.USER_MODIFYPWDACTIVITY)
public class ModifyPwdActivity extends BaseActivity<ModifyPwdPresenter> implements ModifyPwdContract.View {

    @BindView(R2.id.et_modify_pwd)
    PasswordEditText mEtModifyPwd;
    @BindView(R2.id.et_modify_pwd_algin)
    PasswordEditText mEtModifyPwdAlgin;
    @BindView(R2.id.btn_commit)
    View mBtnCommit;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerModifyPwdComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.user_activity_modify_pwd; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).titleBar(R.id.bar_title).init();

        InputTextHelper.with(this)
                .addView(mEtModifyPwd)
                .addView(mEtModifyPwdAlgin)
                .setMain(mBtnCommit)
                .setListener(helper -> mEtModifyPwd.getText().toString().length() > 0)
                .build();
    }

    @OnClick(R2.id.btn_commit)
    void onCommitViewClick(View view) {
        String pwd = mEtModifyPwd.getText().toString();
        String pwdAlgin = mEtModifyPwdAlgin.getText().toString();
        if (!pwd.equals(pwdAlgin)){
            IToast.showShort(getString(R.string.user_hint_double_pwd), IToast.Type.WARN);
        }

        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("doctor_id", CacheUtils.queryUserId());
        paramMap.put("pwd", pwd);
        paramMap.put("repeatpwd", pwdAlgin);
        paramMap.putAll(ParamsHelper.getCommonParams());
        mPresenter.modifyPwd(paramMap);

    }

    /**
      * @description 密码修改成功
      * @date: 2020/7/15 15:11
      * @author: YIWUANYUAN
      * @return
      */
    @Override
    public void modifySuccess() {
        new MessageDialog.Builder()
                .setMessage(getString(R.string.user_hint_mes_content))
                .setCancel(null)
                .setOnViewItemClickListener(mOnViewItemClickListener)
                .showPopupWindow();
    }

    /**
      * @description 确认回掉
      * @date: 2020/7/15 15:36
      * @author: YIWUANYUAN
      * @return
      */
    private OnViewItemClickListener mOnViewItemClickListener = v -> {
        CacheUtils.clearCache(getApplicationContext());
        Utils.postcard(RouterHub.LOGGIN_LOGINACTIVITY).navigation(this, new NavCallback() {
            @Override
            public void onArrival(Postcard postcard) {
                ActivityUtils.finishAllActivities(true);
            }
        });
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    };

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

}
