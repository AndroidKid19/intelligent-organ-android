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

import com.yway.scomponent.commonres.view.layout.CommonLableBar;
import com.yway.scomponent.commonres.view.layout.FlowLayout;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.di.component.DaggerMeetingDetailsComponent;
import com.yway.scomponent.organ.mvp.contract.MeetingDetailsContract;
import com.yway.scomponent.organ.mvp.presenter.MeetingDetailsPresenter;
import com.yway.scomponent.organ.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 会议详情
 */
@Route(path = RouterHub.HOME_MEETINGDETAILSACTIVITY)
public class MeetingDetailsActivity extends BaseActivity<MeetingDetailsPresenter> implements MeetingDetailsContract.View {
    /**
     * 会议主题
     * */
    @BindView(R2.id.bar_metting_title)
    CommonLableBar mBarMettingTitle;
    /**
     * 会议室
     * */
    @BindView(R2.id.bar_loaction)
    CommonLableBar mBarLocation;
    /**
     * 时间
     * */
    @BindView(R2.id.bar_time)
    CommonLableBar mBarTime;
    /**
     * 参会方
     * */
    @BindView(R2.id.bar_organ)
    CommonLableBar mBarOrgan;
    /**
     * 备注
     * */
    @BindView(R2.id.bar_remark)
    CommonLableBar mBarRemark;
    /**
     * 创建人
     * */
    @BindView(R2.id.bar_create)
    CommonLableBar mBarCreate;
    /**
     * 创建人电话
     * */
    @BindView(R2.id.bar_phone)
    CommonLableBar mBarPhone;
    /**
     * 参会人
     * */
    @BindView(R2.id.fview_user)
    FlowLayout mFViewUser;
    /**
     * 会议室设备
     * */
    @BindView(R2.id.fview_devices)
    FlowLayout mFViewDevices;
    /**
     * 文件
     * */
    @BindView(R2.id.fview_files)
    FlowLayout mFViewFiles;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMeetingDetailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.organ_activity_meeting_details; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).titleBar(R.id.bar_title).init();


    }

    @OnClick(R2.id.fview_files)
    void onBarFileClick(View view){
        String url = "https://view.officeapps.live.com/op/view.aspx?src="+"https://bdp-dev-bucket.oss-cn-beijing.aliyuncs.com/app/%E3%80%8A%E7%85%A4%E6%9C%BA%E8%A3%85%E5%A4%87%E7%BD%91%E3%80%8B%E7%AB%8B%E9%A1%B9%E5%BB%BA%E8%AE%AE%E4%B9%A6.doc";
        Utils.postcard(RouterHub.APP_AGENTWEBACTIVITY)
                .withString(RouterHub.PARAM_WEBVIEWXURL,url)
                .navigation(getActivity());
    }
//    https://docs.google.com/viewer?url=
//    https://view.officeapps.live.com/op/view.aspx?src=

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