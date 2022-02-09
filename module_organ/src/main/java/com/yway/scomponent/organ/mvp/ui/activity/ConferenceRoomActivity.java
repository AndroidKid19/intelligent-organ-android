package com.yway.scomponent.organ.mvp.ui.activity;
import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.os.Bundle;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.base.BaseActivity;
import static com.jess.arms.utils.Preconditions.checkNotNull;
import com.jess.arms.utils.ArmsUtils;
import android.content.Intent;
import android.view.View;
import com.yway.scomponent.commonres.view.titlebar.OnTitleBarListener;
import com.yway.scomponent.commonres.view.titlebar.TitleBar;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.di.component.DaggerConferenceRoomComponent;
import com.yway.scomponent.organ.mvp.contract.ConferenceRoomContract;
import com.yway.scomponent.organ.mvp.presenter.ConferenceRoomPresenter;
import com.yway.scomponent.organ.R;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 智能会议室
 */
@Route(path = RouterHub.HOME_CONFERENCEROOMACTIVITY)
public class ConferenceRoomActivity extends BaseActivity<ConferenceRoomPresenter> implements ConferenceRoomContract.View {

    @BindView(R2.id.bar_title)
    TitleBar mTitleBar;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerConferenceRoomComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.organ_activity_conference_room; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).titleBar(R.id.bar_title).init();
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {

            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                Utils.postcard(RouterHub.HOME_MYSUBSCRIBEACTIVITY)
                        .navigation(getActivity());
            }
        });
    }

    /**
     * 预定会议室
     * */
    @OnClick(R2.id.view_online_subscribe)
    void onuSubscribeClick(View view){
        Utils.navigation(getActivity(), RouterHub.HOME_ONLINESUBSCRIBEROOMACTIVITY);
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