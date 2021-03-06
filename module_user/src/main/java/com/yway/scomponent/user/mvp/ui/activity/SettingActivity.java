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
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yway.scomponent.commonres.dialog.IToast;
import com.yway.scomponent.commonres.dialog.ProgresDialog;
import com.yway.scomponent.commonres.dialog.UpgradeDialog;
import com.yway.scomponent.commonres.view.layout.SettingBar;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.utils.CacheDataManager;
import com.yway.scomponent.commonsdk.utils.CacheUtils;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.user.R;
import com.yway.scomponent.user.R2;
import com.yway.scomponent.user.di.component.DaggerSettingComponent;
import com.yway.scomponent.user.mvp.contract.SettingContract;
import com.yway.scomponent.user.mvp.model.entity.AppVersion;
import com.yway.scomponent.user.mvp.presenter.SettingPresenter;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVP on 10/09/2019 14:44
 * <a href="mailto:yuanw9@sina.com">Contact me</a>
 * ================================================
 */
@Route(path = RouterHub.USER_SETTINGACTIVITY)
public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingContract.View {

    @BindView(R2.id.bar_update)
    SettingBar mBarUpdate;
    @BindView(R2.id.bar_clear_cache)
    SettingBar mBarCache;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSettingComponent //??????????????????,?????????????????????
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.user_activity_setting; //???????????????????????????????????? setContentView(id) ??????????????????,????????? 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).titleBar(R.id.bar_title).init();
        mBarUpdate.setRightText("V"+AppUtils.getAppVersionName());
        mBarCache.setRightText(CacheDataManager.getTotalCacheSize(this));
    }

    /***
     * ???????????????
     * */
    @OnClick(R2.id.bar_modify_phone)
    void onModifyPhoneClick(View view) {
        Utils.navigation(this, RouterHub.USER_MODIFYPHONEACTIVITY);
    }

    /***
     * ????????????
     * */
    @OnClick(R2.id.bar_modify_pwd)
    void onModifyPwdClick(View view) {
        Utils.navigation(this, RouterHub.USER_MODIFYPWDACTIVITY);
    }

    /***
     * ????????????
     * */
    @OnClick(R2.id.bar_common)
    void onCommonClick(View view) {
        Utils.navigation(this, RouterHub.USER_FEEDBACKACTIVITY);
    }

    /***
     * ????????????
     * */
    @OnClick(R2.id.bar_about)
    void onSettingClick(View view) {
        Utils.navigation(this, RouterHub.USER_ABOUTACTIVITY);
    }

    /***
     * ????????????
     * */
    @OnClick(R2.id.bar_update)
    void onUpdateClick(View view) {

        mPresenter.upgradeApp();
    }
    /***
     * ????????????
     * */
    @OnClick(R2.id.bar_clear_cache)
    void onCacheClick(View view) {
        CacheDataManager.clearAllCache(getApplicationContext());
        mBarCache.setRightText(CacheDataManager.getTotalCacheSize(getApplicationContext()));
    }

    /***
     * ????????????
     * */
    @OnClick(R2.id.btn_exit)
    void onExitClick(View view) {
        CacheUtils.clearCache(getApplicationContext());
        Utils.postcard(RouterHub.LOGGIN_LOGINACTIVITY).navigation(this, new NavCallback() {
            @Override
            public void onArrival(Postcard postcard) {
                ActivityUtils.finishAllActivities(true);
            }
        });
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
    public void resultSuccess() {

    }

    @Override
    public void resultError(String msg) {
        ArmsUtils.snackbarText(msg);
    }

    @Override
    public void upgradeAppBcakCall(AppVersion data) {
        upgradeApp(data);
    }

    /**
     * @description ??????
     * @date: 2021/3/5 17:48
     * @author: Yuan
     * @return
     */
    private UpgradeDialog.Builder mUpdateDialog;

    private void upgradeApp(AppVersion data) {
        if (Long.parseLong(data.getVersion()) > AppUtils.getAppVersionCode()) {
            RxPermissions mRxPermissions = new RxPermissions(this);
            if (!ObjectUtils.isEmpty(mUpdateDialog) && mUpdateDialog.isShowing()) return;
            mUpdateDialog = new UpgradeDialog.Builder(this, mRxPermissions)
                    // ?????????
                    .setVersionName(Utils.appendStr("?????????V", data.getUserVersion(), "?????????"))
                    // ??????????????????
                    .setForceUpdate(data.getUpgradeMode() == 1 ? true : false)
                    // ????????????
                    .setUpdateLog(data.getRemark().replace("##", "\n"))
                    // ?????? url
                    .setDownloadUrl(data.getPackageDownloadLink())
                    // ????????????
                    .setFileSize(Utils.appendStr("?????????", data.getPackageSize(), "M"))
                    .showPopupWindow();
        }else{
            IToast.showWarnShort("??????????????????????????????");
        }
    }


}
