package com.yway.scomponent.organ.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.StringUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yway.scomponent.commonres.dialog.IToast;
import com.yway.scomponent.commonres.dialog.MessageDialog;
import com.yway.scomponent.commonres.dialog.ProgresDialog;
import com.yway.scomponent.commonres.view.layout.CommonLableBar;
import com.yway.scomponent.commonres.view.layout.FlowLayout;
import com.yway.scomponent.commonsdk.core.AddressCompanyBean;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.commonsdk.utils.CacheUtils;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.di.component.DaggerMeetingDetailsComponent;
import com.yway.scomponent.organ.mvp.contract.MeetingDetailsContract;
import com.yway.scomponent.organ.mvp.model.entity.FileDetailsBean;
import com.yway.scomponent.organ.mvp.model.entity.MeetingDetailsBean;
import com.yway.scomponent.organ.mvp.presenter.MeetingDetailsPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * ????????????
 */
@Route(path = RouterHub.HOME_MEETINGDETAILSACTIVITY)
public class MeetingDetailsActivity extends BaseActivity<MeetingDetailsPresenter> implements MeetingDetailsContract.View {
    /**
     * ????????????
     */
    @BindView(R2.id.bar_metting_title)
    CommonLableBar mBarMettingTitle;
    /**
     * ?????????
     */
    @BindView(R2.id.bar_loaction)
    CommonLableBar mBarLocation;
    /**
     * ??????
     */
    @BindView(R2.id.bar_time)
    CommonLableBar mBarTime;
    /**
     * ????????????
     */
    @BindView(R2.id.bar_end_time)
    CommonLableBar mBarEndTime;
    /**
     * ?????????
     */
    @BindView(R2.id.bar_organ)
    CommonLableBar mBarOrgan;
    /**
     * ??????
     */
    @BindView(R2.id.bar_remark)
    CommonLableBar mBarRemark;
    /**
     * ?????????
     */
    @BindView(R2.id.bar_create)
    CommonLableBar mBarCreate;
    /**
     * ???????????????
     */
    @BindView(R2.id.bar_phone)
    CommonLableBar mBarPhone;
    /**
     * ?????????
     */
    @BindView(R2.id.fview_user)
    FlowLayout mFViewUser;
    /**
     * ???????????????
     */
    @BindView(R2.id.fview_devices)
    FlowLayout mFViewDevices;
    /**
     * ??????
     */
    @BindView(R2.id.fview_files)
    FlowLayout mFViewFiles;
    /**
     * ??????/??????
     */
    @BindView(R2.id.btn_opt_prepare)
    AppCompatButton mBtnOptPrepare;


    /**
     * ??????
     */
    @BindView(R2.id.view_opt)
    View mViewOpt;
    /**
     * ??????
     */
    @BindView(R2.id.view_prepare)
    View mViewPrepare;

    /**
     * ??????ID
     */
    @Autowired
    String mettingId;

    /**
     * ????????????
     * 1 ???????????? - ????????????
     * 2 ?????????
     * 3 ?????????
     * 4 ?????????
     * 5 ?????????
     * 6 ????????????
     */
    @Autowired
    int pageFrom;

    /**
     * ????????????
     */
    private MeetingDetailsBean mMeetingDetailsBean;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMeetingDetailsComponent //??????????????????,?????????????????????
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.organ_activity_meeting_details; //???????????????????????????????????? setContentView(id) ??????????????????,????????? 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).titleBar(R.id.bar_title).init();
        //????????????ID??????????????????
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("id", mettingId);
        mPresenter.queryByMeetingRecordDetails(paramsMap);
        //??????????????????????????????
        if (pageFrom == 1) {
            mViewPrepare.setVisibility(View.VISIBLE);
            mBtnOptPrepare.setText("????????????");
        }

        if (pageFrom == 2) {
            mViewOpt.setVisibility(View.VISIBLE);
        }

        if (pageFrom == 4) {
            mViewPrepare.setVisibility(View.VISIBLE);
        }
    }

    //    https://docs.google.com/viewer?url=
    //    https://view.officeapps.live.com/op/view.aspx?src=

    @OnClick(R2.id.fview_files)
    void onBarFileClick(View view) {
        String url = "https://view.officeapps.live.com/op/view.aspx?src=" + "https://bdp-dev-bucket.oss-cn-beijing.aliyuncs.com/app/%E3%80%8A%E7%85%A4%E6%9C%BA%E8%A3%85%E5%A4%87%E7%BD%91%E3%80%8B%E7%AB%8B%E9%A1%B9%E5%BB%BA%E8%AE%AE%E4%B9%A6.doc";
        Utils.postcard(RouterHub.APP_AGENTWEBACTIVITY)
                .withString(RouterHub.PARAM_WEBVIEWXURL, url)
                .navigation(getActivity());

    }

    /**
     * ??????
     */
    @OnClick(R2.id.btn_opt_reject)
    void onOptReject(View view) {
        new MessageDialog.Builder()
                .setTitle("????????????")
                .setMessage("?????????????????????????????????")
                .setOnViewItemClickListener(v -> {
                    //????????????
                    Map<String, Object> paramsMap = new HashMap<>();
                    paramsMap.put("approvalResult", 1);
                    paramsMap.put("meetingRecordId", mettingId);
                    mPresenter.doMeetingRecordApproval(paramsMap);
                })
                .showPopupWindow();
    }

    /**
     * ??????
     */
    @OnClick(R2.id.btn_opt_adopt)
    void onOptReAdopt(View view) {
        //????????????
        adoptMeting();
    }

    /**
     * ????????????
     */
    @OnClick(R2.id.btn_opt_prepare)
    void onPrepare(View view) {
        if (pageFrom == 1) {
            //????????????
            doCancelMeetingRecord();
        } else {
            //????????????
            prepareMeting();
        }
    }

    /**
     * ????????????
     */
    private void adoptMeting() {
        new MessageDialog.Builder()
                .setTitle("????????????")
                .setMessage("?????????????????????????????????")
                .setOnViewItemClickListener(v -> {
                    //????????????
                    Map<String, Object> paramsMap = new HashMap<>();
                    paramsMap.put("approvalResult", 2);
                    paramsMap.put("meetingRecordId", mettingId);
                    mPresenter.doMeetingRecordApproval(paramsMap);
                })
                .showPopupWindow();
    }

    /**
     * ????????????
     */
    private void doCancelMeetingRecord() {
        new MessageDialog.Builder()
                .setTitle("????????????")
                .setMessage("?????????????????????????????????")
                .setOnViewItemClickListener(v -> {
                    //????????????
                    Map<String, Object> paramsMap = new HashMap<>();
                    paramsMap.put("meetingRecordId", mettingId);
                    mPresenter.doCancelMeetingRecord(paramsMap);
                })
                .showPopupWindow();
    }

    /**
     * ????????????
     */
    private void prepareMeting() {
        new MessageDialog.Builder()
                .setTitle("????????????")
                .setMessage("?????????????????????????????????")
                .setOnViewItemClickListener(v -> {
                    //????????????
                    Map<String, Object> paramsMap = new HashMap<>();
                    paramsMap.put("meetingRecordId", mettingId);
                    mPresenter.doMeetingRecordReady(paramsMap);
                })
                .showPopupWindow();
    }

    /**
     * ????????????????????????
     */
    @Override
    public void queryMessingDetailsSuccess(MeetingDetailsBean data) {
        this.mMeetingDetailsBean = data;
        //????????????
        mBarMettingTitle.setRightText(data.getMeetingSubject());
        //?????????
        mBarLocation.setRightText(data.getMeetingRoomName());
        //????????????
        mBarTime.setRightText(data.getMeetingStartTime());
        //????????????
        mBarEndTime.setRightText(data.getMeetingEndTime());
        //?????????
        mBarOrgan.setRightText(composeOrg(data.getMeetingOrganizationRspBOList()));
        //???????????????
        addMetingUserView(data.getMeetingPersonnelRspBOList());
        //????????????
        addMetingFileView(data.getMeetingFileRspBOList());
        //????????????
        addMetingDeviceView(data.getMeetingDeviceNeeds());
        //????????????
        mBarRemark.setRightText(Utils.appendStr(data.getRemark(),
                "  ?????????",
                CacheUtils.queryDictValue(CacheUtils.queryDictData().getDictMeetingCheckInTime(), data.getMeetingCheckInTime()),
                "???????????????"));
        //?????????
        mBarCreate.setRightText(data.getCreateName());
        //??????????????????
        mBarPhone.setRightText(data.getCreateCellPhone());

        if (!data.getApprovalStatus().equals("1")) {
            mViewPrepare.setVisibility(View.GONE);
        }
    }

    @OnClick(R2.id.bar_phone)
    void onCallPhone(View view) {
        if (StringUtils.isEmpty(mMeetingDetailsBean.getCreateCellPhone())) return;
        PhoneUtils.dial(mMeetingDetailsBean.getCreateCellPhone());
    }

    @Override
    public void approvalResultsCallBack(Integer approvalResult) {
        if (approvalResult == 2) {
            //??????
            IToast.showFinishShort("????????????");
        } else {
            //??????
            IToast.showFinishShort("???????????????");
        }
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void doMeetingRecordReadyCallBack() {
        IToast.showFinishShort("?????????????????????");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void doCancelMeetingRecordCallBack() {
        IToast.showFinishShort("???????????????");
        setResult(RESULT_OK);
        finish();
    }

    /**
     * ???????????????
     */
    private void addMetingUserView(List<UserInfoBean> data) {
        if (CollectionUtils.isEmpty(data)) return;
        for (UserInfoBean userInfoBean : data) {
            LinearLayoutCompat view = (LinearLayoutCompat) ArmsUtils.inflate(getActivity(), R.layout.organ_item_meting_user);
            AppCompatTextView appCompatTextView = view.findViewById(R.id.tv_user_name);
            appCompatTextView.setText(userInfoBean.getUserName());
            AppCompatImageView appCompatImageView = view.findViewById(R.id.iv_head);
            mPresenter.imageLoader(userInfoBean.getSysUserFilePath(), appCompatImageView);
            mFViewUser.addView(view);
        }
    }

    /**
     * ??????????????????
     */
    private void addMetingFileView(List<FileDetailsBean> data) {
        if (CollectionUtils.isEmpty(data)) return;
        for (FileDetailsBean fileDetailsBean : data) {
            AppCompatTextView mMetingFile = (AppCompatTextView) ArmsUtils.inflate(getActivity(), R.layout.organ_item_meting_file);
            mMetingFile.setText(fileDetailsBean.getFileName());
            mFViewFiles.addView(mMetingFile);
            mMetingFile.setOnClickListener(v -> {
                //????????????
                String url = Utils.appendStr("https://view.officeapps.live.com/op/view.aspx?src=", fileDetailsBean.getFilePath());
                Utils.postcard(RouterHub.APP_AGENTWEBACTIVITY)
                        .withString(RouterHub.PARAM_WEBVIEWXURL, url)
                        .navigation(getActivity());
            });
        }
    }

    /**
     * ????????????
     */
    private void addMetingDeviceView(String meetingDeviceNeeds) {
        String[] meeing = meetingDeviceNeeds.split(",");
        for (String device : meeing) {
            AppCompatTextView mMetingDevice = (AppCompatTextView) ArmsUtils.inflate(getActivity(), R.layout.organ_item_meting_device);
            mMetingDevice.setText(CacheUtils.queryDictValue(CacheUtils.queryDictData().getDictDevice(), device));
            mFViewDevices.addView(mMetingDevice);
        }
    }

    /**
     * ?????????????????????
     */
    private String composeOrg(List<AddressCompanyBean> data) {
        if (CollectionUtils.isEmpty(data)) return "";
        String orgNames = "";
        for (AddressCompanyBean companyBean : data) {
            orgNames = companyBean.getOrgName() + "???" + orgNames;
        }
        orgNames = orgNames.substring(0, orgNames.length() - 1);
        return orgNames;
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

}