package com.yway.scomponent.organ.mvp.ui.activity;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.base.BaseActivity;
import static com.jess.arms.utils.Preconditions.checkNotNull;
import com.jess.arms.utils.ArmsUtils;
import android.content.Intent;
import android.view.View;

import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.di.component.DaggerSubscribeSucceesComponent;
import com.yway.scomponent.organ.mvp.contract.SubscribeSucceesContract;
import com.yway.scomponent.organ.mvp.presenter.SubscribeSucceesPresenter;
import com.yway.scomponent.organ.R;

import butterknife.OnClick;

/**
 * 预约成功
 */
@Route(path = RouterHub.HOME_SUBSCRIBESUCCEESACTIVITY)
public class SubscribeSucceesActivity extends BaseActivity<SubscribeSucceesPresenter> implements SubscribeSucceesContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSubscribeSucceesComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.organ_activity_subscribe_succees; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).titleBar(R.id.bar_title).init();

    }

    @OnClick(R2.id.btn_back_home)
    void onBackHomeClick(View view){
        Utils.postcard(RouterHub.APP_MAINACTIVITY).navigation(this, new NavCallback() {
            @Override
            public void onArrival(Postcard postcard) {
                finish();
            }
        });
    }

    @OnClick(R2.id.btn_query_details)
    void onDetailsClick(View view){

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