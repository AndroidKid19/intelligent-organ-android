package com.yway.scomponent.user.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.user.R2;
import com.yway.scomponent.user.di.component.DaggerMessageDetailsComponent;
import com.yway.scomponent.user.mvp.contract.MessageDetailsContract;
import com.yway.scomponent.user.mvp.presenter.MessageDetailsPresenter;

import com.yway.scomponent.user.R;


import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 消息详情
 * <p>
 * Created on 07/21/2020 14:09
 * ================================================
 */
@Route(path = RouterHub.USER_MESSAGEDETAILSACTIVITY)
public class MessageDetailsActivity extends BaseActivity<MessageDetailsPresenter> implements MessageDetailsContract.View {

    @BindView(R2.id.tv_content)
    AppCompatTextView mTvContent;

    @Autowired(name = RouterHub.PARAM_MESSAGE_CONTENT)
    String messageContent;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMessageDetailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.user_activity_message_details; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).titleBar(R.id.bar_title).init();
        mTvContent.setText(messageContent);
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
        finish();
    }
}
