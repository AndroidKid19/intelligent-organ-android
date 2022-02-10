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
 * 会议详情
 */
@Route(path = RouterHub.HOME_MEETINGDETAILSACTIVITY)
public class MeetingDetailsActivity extends BaseActivity<MeetingDetailsPresenter> implements MeetingDetailsContract.View {
    /**
     * 会议主题
     */
    @BindView(R2.id.bar_metting_title)
    CommonLableBar mBarMettingTitle;
    /**
     * 会议室
     */
    @BindView(R2.id.bar_loaction)
    CommonLableBar mBarLocation;
    /**
     * 时间
     */
    @BindView(R2.id.bar_time)
    CommonLableBar mBarTime;
    /**
     * 结束时间
     */
    @BindView(R2.id.bar_end_time)
    CommonLableBar mBarEndTime;
    /**
     * 参会方
     */
    @BindView(R2.id.bar_organ)
    CommonLableBar mBarOrgan;
    /**
     * 备注
     */
    @BindView(R2.id.bar_remark)
    CommonLableBar mBarRemark;
    /**
     * 创建人
     */
    @BindView(R2.id.bar_create)
    CommonLableBar mBarCreate;
    /**
     * 创建人电话
     */
    @BindView(R2.id.bar_phone)
    CommonLableBar mBarPhone;
    /**
     * 参会人
     */
    @BindView(R2.id.fview_user)
    FlowLayout mFViewUser;
    /**
     * 会议室设备
     */
    @BindView(R2.id.fview_devices)
    FlowLayout mFViewDevices;
    /**
     * 文件
     */
    @BindView(R2.id.fview_files)
    FlowLayout mFViewFiles;
    /**
     * 取消/准备
     */
    @BindView(R2.id.btn_opt_prepare)
    AppCompatButton mBtnOptPrepare;


    /**
     * 操作
     */
    @BindView(R2.id.view_opt)
    View mViewOpt;
    /**
     * 准备
     */
    @BindView(R2.id.view_prepare)
    View mViewPrepare;

    /**
     * 会议ID
     */
    @Autowired
    String mettingId;

    /**
     * 页面标记
     * 1 我的预约 - 我发起的
     * 2 待审核
     * 3 已审核
     * 4 待准备
     * 5 已准备
     * 6 我的会议
     */
    @Autowired
    int pageFrom;

    /**
     * 会议详情
     */
    private MeetingDetailsBean mMeetingDetailsBean;

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
        //根据会议ID查询会议详情
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("id", mettingId);
        mPresenter.queryByMeetingRecordDetails(paramsMap);
        //根据模块展示操作按钮
        if (pageFrom == 1) {
            mViewPrepare.setVisibility(View.VISIBLE);
            mBtnOptPrepare.setText("取消预约");
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
     * 驳回
     */
    @OnClick(R2.id.btn_opt_reject)
    void onOptReject(View view) {
        new MessageDialog.Builder()
                .setTitle("驳回提醒")
                .setMessage("确定要驳回当前会议预约")
                .setOnViewItemClickListener(v -> {
                    //驳回请求
                    Map<String, Object> paramsMap = new HashMap<>();
                    paramsMap.put("approvalResult", 1);
                    paramsMap.put("meetingRecordId", mettingId);
                    mPresenter.doMeetingRecordApproval(paramsMap);
                })
                .showPopupWindow();
    }

    /**
     * 通过
     */
    @OnClick(R2.id.btn_opt_adopt)
    void onOptReAdopt(View view) {
        //通过会议
        adoptMeting();
    }

    /**
     * 准备会议
     */
    @OnClick(R2.id.btn_opt_prepare)
    void onPrepare(View view) {
        if (pageFrom == 1) {
            //取消会议
            doCancelMeetingRecord();
        } else {
            //准备会议
            prepareMeting();
        }
    }

    /**
     * 通过会议
     */
    private void adoptMeting() {
        new MessageDialog.Builder()
                .setTitle("审核提醒")
                .setMessage("请及时通过当前会议预约")
                .setOnViewItemClickListener(v -> {
                    //通过请求
                    Map<String, Object> paramsMap = new HashMap<>();
                    paramsMap.put("approvalResult", 2);
                    paramsMap.put("meetingRecordId", mettingId);
                    mPresenter.doMeetingRecordApproval(paramsMap);
                })
                .showPopupWindow();
    }

    /**
     * 取消会议
     */
    private void doCancelMeetingRecord() {
        new MessageDialog.Builder()
                .setTitle("取消提醒")
                .setMessage("确定要取消当前预约会议")
                .setOnViewItemClickListener(v -> {
                    //通过请求
                    Map<String, Object> paramsMap = new HashMap<>();
                    paramsMap.put("meetingRecordId", mettingId);
                    mPresenter.doCancelMeetingRecord(paramsMap);
                })
                .showPopupWindow();
    }

    /**
     * 准备会议
     */
    private void prepareMeting() {
        new MessageDialog.Builder()
                .setTitle("准备提醒")
                .setMessage("请及时准备当前会议预约")
                .setOnViewItemClickListener(v -> {
                    //通过请求
                    Map<String, Object> paramsMap = new HashMap<>();
                    paramsMap.put("meetingRecordId", mettingId);
                    mPresenter.doMeetingRecordReady(paramsMap);
                })
                .showPopupWindow();
    }

    /**
     * 会议详情结果回调
     */
    @Override
    public void queryMessingDetailsSuccess(MeetingDetailsBean data) {
        this.mMeetingDetailsBean = data;
        //会议主题
        mBarMettingTitle.setRightText(data.getMeetingSubject());
        //会议室
        mBarLocation.setRightText(data.getMeetingRoomName());
        //开始时间
        mBarTime.setRightText(data.getMeetingStartTime());
        //结束时间
        mBarEndTime.setRightText(data.getMeetingEndTime());
        //参会方
        mBarOrgan.setRightText(composeOrg(data.getMeetingOrganizationRspBOList()));
        //展示参会方
        addMetingUserView(data.getMeetingPersonnelRspBOList());
        //展示附件
        addMetingFileView(data.getMeetingFileRspBOList());
        //展示设备
        addMetingDeviceView(data.getMeetingDeviceNeeds());
        //展示备注
        mBarRemark.setRightText(Utils.appendStr(data.getRemark(),
                "  请提前",
                CacheUtils.queryDictValue(CacheUtils.queryDictData().getDictMeetingCheckInTime(), data.getMeetingCheckInTime()),
                "到达会议室"));
        //发起方
        mBarCreate.setRightText(data.getCreateName());
        //发起方手机号
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
            //通过
            IToast.showFinishShort("审核通过");
        } else {
            //驳回
            IToast.showFinishShort("审核不通过");
        }
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void doMeetingRecordReadyCallBack() {
        IToast.showFinishShort("会议已准备完成");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void doCancelMeetingRecordCallBack() {
        IToast.showFinishShort("会议已取消");
        setResult(RESULT_OK);
        finish();
    }

    /**
     * 参会人展示
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
     * 附件列表展示
     */
    private void addMetingFileView(List<FileDetailsBean> data) {
        if (CollectionUtils.isEmpty(data)) return;
        for (FileDetailsBean fileDetailsBean : data) {
            AppCompatTextView mMetingFile = (AppCompatTextView) ArmsUtils.inflate(getActivity(), R.layout.organ_item_meting_file);
            mMetingFile.setText(fileDetailsBean.getFileName());
            mFViewFiles.addView(mMetingFile);
            mMetingFile.setOnClickListener(v -> {
                //预览附件
                String url = Utils.appendStr("https://view.officeapps.live.com/op/view.aspx?src=", fileDetailsBean.getFilePath());
                Utils.postcard(RouterHub.APP_AGENTWEBACTIVITY)
                        .withString(RouterHub.PARAM_WEBVIEWXURL, url)
                        .navigation(getActivity());
            });
        }
    }

    /**
     * 设备展示
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
     * 拼装参会方名称
     */
    private String composeOrg(List<AddressCompanyBean> data) {
        if (CollectionUtils.isEmpty(data)) return "";
        String orgNames = "";
        for (AddressCompanyBean companyBean : data) {
            orgNames = companyBean.getOrgName() + "、" + orgNames;
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