package com.yway.scomponent.organ.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.UriUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;
import com.luck.picture.lib.tools.BitmapUtils;
import com.yway.scomponent.commonres.dialog.IToast;
import com.yway.scomponent.commonres.dialog.MessageDialog;
import com.yway.scomponent.commonres.dialog.ProgresDialog;
import com.yway.scomponent.commonres.view.layout.ClearEditText;
import com.yway.scomponent.commonres.view.layout.SettingBar;
import com.yway.scomponent.commonres.view.titlebar.OnTitleBarListener;
import com.yway.scomponent.commonres.view.titlebar.TitleBar;
import com.yway.scomponent.commonsdk.core.Constants;
import com.yway.scomponent.commonsdk.core.DictClassifyBean;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.core.UploadFileBean;
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.commonsdk.utils.CacheUtils;
import com.yway.scomponent.commonsdk.utils.FileUtils;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.di.component.DaggerApplyRoomComponent;
import com.yway.scomponent.organ.mvp.contract.ApplyRoomContract;
import com.yway.scomponent.commonsdk.core.AddressCompanyBean;
import com.yway.scomponent.organ.mvp.model.entity.CheckBoxBean;
import com.yway.scomponent.organ.mvp.model.entity.FileDetailsBean;
import com.yway.scomponent.organ.mvp.model.entity.MeetingDetailsBean;
import com.yway.scomponent.organ.mvp.model.entity.RoomDetailsBean;
import com.yway.scomponent.organ.mvp.model.entity.SubscribeTimeBean;
import com.yway.scomponent.organ.mvp.presenter.ApplyRoomPresenter;
import com.yway.scomponent.organ.mvp.ui.adapter.CheckBoxAdapter;
import com.yway.scomponent.organ.mvp.ui.adapter.ChooseCompanyAdapter;
import com.yway.scomponent.organ.mvp.ui.adapter.ChooseFileAdapter;
import com.yway.scomponent.organ.mvp.ui.dialog.TimerScheduleDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * ????????????
 */
@Route(path = RouterHub.HOME_APPLYROOMACTIVITY)
public class ApplyRoomActivity extends BaseActivity<ApplyRoomPresenter> implements ApplyRoomContract.View {
    /**
     * title
     */
    @BindView(R2.id.bar_title)
    TitleBar mTitleBar;
    /**
     * ???????????????
     */
    @BindView(R2.id.tv_room_name)
    AppCompatTextView mTvRoomName;
    /**
     * ???????????????
     */
    @BindView(R2.id.tv_room_count)
    AppCompatTextView mTvRoomCount;
    /**
     * View ??????
     */
    @BindView(R2.id.recycle_view_device)
    RecyclerView mRecyclerView;
    /**
     * ???????????? - ????????????
     */
    @BindView(R2.id.bar_date)
    SettingBar mBarDate;
    /**
     * ????????????
     */
    @BindView(R2.id.bar_choose_user)
    SettingBar mBarCheckedUser;
    /**
     * ????????????
     */
    @BindView(R2.id.bar_report)
    SettingBar mBarReport;
    /**
     * ??????????????????
     */
    @BindView(R2.id.bar_choose_company)
    SettingBar mBarChooseCompany;
    /**
     * ??????????????????
     */
    @BindView(R2.id.recycle_view_company)
    RecyclerView mRecyclerViewCompany;
    /**
     * ????????????
     */
    @BindView(R2.id.tv_file_choose)
    AppCompatTextView mTvFileChoose;
    /**
     * ????????????
     */
    @BindView(R2.id.recycle_view_file)
    RecyclerView mRecyclerViewFile;
    /**
     * ????????????
     */
    @BindView(R2.id.et_metting_subject)
    ClearEditText mEtMettingSubject;
    /**
     * ????????????
     */
    @BindView(R2.id.et_remark)
    ClearEditText mEtRemark;
    /**
     * ????????????
     */
    @BindView(R2.id.sc_set_notice1)
    SwitchCompat mScSetNotice1;
    /**
     * ????????????
     */
    @BindView(R2.id.sc_set_notice2)
    SwitchCompat mScSetNotice2;
    /**
     * ????????????
     */
    @BindView(R2.id.sc_set_notice3)
    SwitchCompat mScSetNotice3;
    /**
     * ????????????
     */
    @BindView(R2.id.sc_set_notice4)
    SwitchCompat mScSetNotice4;

    /**
     * ?????????????????????
     */
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    CheckBoxAdapter mAdapter;
    @Inject
    List<CheckBoxBean> mDataLs;

    /**
     * ???????????????????????????
     */
    private List<UserInfoBean> userInfoBeans = new ArrayList<>();

    /**
     * ?????????????????????????????????
     */
    @Inject
    List<AddressCompanyBean> mAddressCompanyBeans;
    @Inject
    ChooseCompanyAdapter mChooseCompanyAdapter;

    /**
     * ???????????????????????????
     */
    @Inject
    List<UploadFileBean> mFileDetailsBeans;
    @Inject
    ChooseFileAdapter mChooseFileAdapter;

    /**
     * ??????????????????
     */
    private int meetingOption = 0;

    /**
     * ??????????????????
     */
    private String meetingStartTime = "";
    /**
     * ??????????????????
     */
    private String meetingEndTime = "";

    @Autowired(name = "roomDetailsBean")
    RoomDetailsBean mRoomDetailsBean;

    /**
     * ????????????
     * 1 ?????????
     */
    @Autowired
    int pageFrom;

    /**
     * ?????????????????????
     */
    @Autowired(name = "meetingDetailsBean")
    MeetingDetailsBean mMeetingDetailsBean;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerApplyRoomComponent //??????????????????,?????????????????????
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.organ_activity_apply_room; //???????????????????????????????????? setContentView(id) ??????????????????,????????? 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).titleBar(R.id.bar_title).init();

        initRecyclerView();
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {

            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                applyComit(0);
            }
        });


        //??????????????????????????????
        initDraftsData();
    }


    /**
     * ??????????????????
     */
    private void initDraftsData() {
        if (pageFrom != 1) {
            //???????????????????????????
            mBarDate.setLeftText(mRoomDetailsBean.getMeetingDate() + " " + mRoomDetailsBean.getSubscribeTimeBean().getTime());
        } else {
            //????????????????????????

            //????????????????????????
            mTvRoomName.setText(mMeetingDetailsBean.getMeetingRoomName());
            mTvRoomCount.setText(Utils.appendStr(mMeetingDetailsBean.getLocation(), " ?? ?????????", mMeetingDetailsBean.getSeatsNumber(), "???"));

            //?????????????????????
            mEtMettingSubject.setText(mMeetingDetailsBean.getMeetingSubject());

            //?????????????????????
            SubscribeTimeBean subscribeTimeBean = new SubscribeTimeBean();
            subscribeTimeBean.setTime(mRoomDetailsBean.getMeetingDate().split(" ")[1]);

            mRoomDetailsBean.setSubscribeTimeBean(subscribeTimeBean);

            mRoomDetailsBean.setMeetingDate(mRoomDetailsBean.getMeetingDate().split(" ")[0]);
            mBarDate.setLeftText(mRoomDetailsBean.getMeetingDate());

            //?????????????????????

            //?????????????????????
            List<UserInfoBean> meetingPersonnelRspBOList = mMeetingDetailsBean.getMeetingPersonnelRspBOList();
            //??????????????????
            if (CollectionUtils.isNotEmpty(meetingPersonnelRspBOList)) {
                //???????????????????????????
//                userInfoBeans = meetingPersonnelRspBOList;
                //??????????????????
//                mBarCheckedUser.setLeftText(mPresenter.generateStrUserNames(userInfoBeans));
            }
            //?????????????????????
            List<AddressCompanyBean> meetingOrganizationRspBOList = mMeetingDetailsBean.getMeetingOrganizationRspBOList();
            //??????????????????
            if (CollectionUtils.isNotEmpty(meetingOrganizationRspBOList)) {
                //?????????????????????
                mAddressCompanyBeans.addAll(meetingOrganizationRspBOList);
                mChooseCompanyAdapter.notifyDataSetChanged();
            }

            //?????????????????????
            List<FileDetailsBean> meetingFileRspBOList = mMeetingDetailsBean.getMeetingFileRspBOList();
            //????????????
            if (CollectionUtils.isNotEmpty(meetingFileRspBOList)) {
                for (FileDetailsBean fileDetailsBean : meetingFileRspBOList) {
                    UploadFileBean uploadFileBean = new UploadFileBean();
                    uploadFileBean.setName(fileDetailsBean.getFileName());
                    uploadFileBean.setUrl(fileDetailsBean.getFilePath());
                    mFileDetailsBeans.add(uploadFileBean);
                }
                mChooseFileAdapter.notifyDataSetChanged();
            }
            //???????????????

            //???????????????
            mEtRemark.setText(mMeetingDetailsBean.getRemark());
        }

    }

    /**
     * ?????????RecyclerView
     */
    private void initRecyclerView() {
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        //???????????????
        List<DictClassifyBean> dictDevice = CacheUtils.queryDictData().getDictDevice();
        for (DictClassifyBean dictClassifyBean : dictDevice) {
            CheckBoxBean checkBoxBean = new CheckBoxBean();
            checkBoxBean.setCheckBoxName(dictClassifyBean.getSysDictName());
            checkBoxBean.setSysDictCode(dictClassifyBean.getSysDictCode());
            mDataLs.add(checkBoxBean);
        }

        //???????????????????????????
        ArmsUtils.configRecyclerView(mRecyclerViewCompany, new LinearLayoutManager(this));
        mRecyclerViewCompany.setAdapter(mChooseCompanyAdapter);

        //?????????????????????
        ArmsUtils.configRecyclerView(mRecyclerViewFile, new LinearLayoutManager(this));
        mRecyclerViewFile.setAdapter(mChooseFileAdapter);

        mTvRoomName.setText(mRoomDetailsBean.getName());
        mTvRoomCount.setText(Utils.appendStr(mRoomDetailsBean.getLocation(), " ?? ?????????", mRoomDetailsBean.getSeatsNumber(), "???"));
    }


    /**
     * ??????????????????
     */
    @OnClick(R2.id.bar_choose_user)
    void onBarChooseUserClick(View view) {
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setList(userInfoBeans);
        Utils.postcard(RouterHub.HOME_CHOOSEUSERACTIVITY)
                .withParcelable(Constants.APP_USER_LIST, userInfoBean)
                .navigation(getActivity(), Constants.RESULT_COMMON_CODE);
    }

    /**
     * ??????????????????
     */
    @OnClick(R2.id.bar_choose_company)
    void onBarChooseCompanyClick(View view) {
        Utils.postcard(RouterHub.HOME_CHOOSECOMPANYACTIVITY)
                .navigation(getActivity(), Constants.RESULT_CHOOSE_COMPANY_CODE);
    }

    /**
     * ??????????????????
     */
    @OnClick(R2.id.bar_report)
    void onBarReportClick(View view) {
        chioseReportTime();
    }


    /**
     * ??????????????????
     */
    @OnClick(R2.id.bar_date)
    void onBarDateClick(View view) {
        SubscribeTimeBean subscribeTimeBean = mRoomDetailsBean.getSubscribeTimeBean();
        int hour = Integer.parseInt(subscribeTimeBean.getTime().split(":")[0]);
        int minute = Integer.parseInt(subscribeTimeBean.getTime().split(":")[1]);
        new TimerScheduleDialog.Builder()
                .initHour(hour, minute)
                .setOnTimepickerClickListener((startTime, endTime) -> {
                    Timber.i(startTime + "---" + endTime);
                    startTime = startTime.length() == 4 ? Utils.appendStr("0", startTime) : startTime;
                    endTime = endTime.length() == 4 ? Utils.appendStr("0", endTime) : endTime;

                    this.meetingStartTime = mRoomDetailsBean.getMeetingDate() + " " + startTime;
                    this.meetingEndTime = mRoomDetailsBean.getMeetingDate() + " " + endTime;
                    mBarDate.setLeftText(Utils.appendStr(mRoomDetailsBean.getMeetingDate(), " ", startTime, "-", endTime));
                })
                .showPopupWindow();
    }

    /**
     * ????????????
     */
    @OnClick(R2.id.tv_file_choose)
    void onBarChooseFileClick(View view) {
        PermissionUtil.externalStorage(mRequestPermission, mPresenter.getRxPermissions(this), ArmsUtils.obtainAppComponentFromContext(this).rxErrorHandler());

    }


    private PermissionUtil.RequestPermission mRequestPermission = new PermissionUtil.RequestPermission() {
        @Override
        public void onRequestPermissionSuccess() {
            showFileChooser();
        }

        @Override
        public void onRequestPermissionFailure(List<String> permissions) {
            PermissionUtil.launchCamera(mRequestPermission, mPresenter.getRxPermissions(getActivity()), ArmsUtils.obtainAppComponentFromContext(getActivity()).rxErrorHandler());
        }

        @Override
        public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
            ArmsUtils.snackbarText(getString(R.string.public_common_permission_fail));
        }
    };

    /**
     * ??????
     */
    @OnClick(R2.id.btn_commit)
    void onCommitClick(View view) {
        applyComit(1);

    }

    private void applyComit(int approvalStatusStrs) {
        //????????????
        Map<String, Object> paramMap = new HashMap<>();
        //?????????ID
        if (pageFrom == 1) {
            paramMap.put("meetingRoomId", mMeetingDetailsBean.getMeetingRoomId());
        } else {
            paramMap.put("meetingRoomId", mRoomDetailsBean.getId());
        }
        //????????????
        String meetingSubject = mEtMettingSubject.getText().toString();
        if (StringUtils.isEmpty(meetingSubject)) {
            IToast.showWarnShort("?????????????????????");
            return;
        }
        paramMap.put("meetingSubject", meetingSubject);
        //??????????????????
        if (StringUtils.isEmpty(meetingStartTime)) {
            IToast.showWarnShort("???????????????????????????");
            return;
        }
        paramMap.put("meetingStartTime", meetingStartTime);

        if (StringUtils.isEmpty(meetingEndTime)) {
            IToast.showWarnShort("???????????????????????????");
            return;
        }
        //??????????????????
        paramMap.put("meetingEndTime", meetingEndTime);
        //?????????????????????????????????????????????code
        String meetingCheckInTime = CacheUtils.queryDictData().getDictMeetingCheckInTime().get(meetingOption).getSysDictCode();
        paramMap.put("meetingCheckInTime", meetingCheckInTime);
        //???????????????????????????????????????????????????????????????????????????
        String meetingDeviceNeeds = mAdapter.getCheckedDevice();
        paramMap.put("meetingDeviceNeeds", meetingDeviceNeeds);
        //?????????????????????????????????????????????????????????????????????
        String meetingSet = "";
        if (mScSetNotice1.isChecked()) {
            meetingSet = Utils.appendStr("0", ",", meetingSet);
        }

        if (mScSetNotice2.isChecked()) {
            meetingSet = Utils.appendStr("1", ",", meetingSet);
        }

        if (mScSetNotice3.isChecked()) {
            meetingSet = Utils.appendStr("2", ",", meetingSet);
        }

        if (mScSetNotice4.isChecked()) {
            meetingSet = Utils.appendStr("3", ",", meetingSet);
        }
        if (!StringUtils.isEmpty(meetingSet)) {
            meetingSet = meetingSet.substring(0, meetingSet.length() - 1);
        }

        paramMap.put("meetingSet", meetingSet);

        //???????????????????????????0??????????????????????????????1?????????????????????????????????2??????????????????3???????????????
        paramMap.put("approvalStatus", approvalStatusStrs);
        //????????????
        String remark = mEtRemark.getText().toString();
        paramMap.put("remark", remark);
        //????????????????????????
        List<Map<String, Object>> userList = new ArrayList<>();
        for (UserInfoBean userInfoBean : userInfoBeans) {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("userId", userInfoBean.getUserId());
            userList.add(userMap);
        }
        paramMap.put("meetingPersonnelReqBOList", userList);

        //????????????
        List<Map<String, Object>> companyList = new ArrayList<>();
        for (AddressCompanyBean addressCompanyBean : mChooseCompanyAdapter.getInfos()) {
            Map<String, Object> companyMap = new HashMap<>();
            companyMap.put("orgId", addressCompanyBean.getOrgId());
            companyList.add(companyMap);
        }
        paramMap.put("meetingOrganizationReqBOList", companyList);
        if (CollectionUtils.isEmpty(companyList)) {
            IToast.showWarnShort("?????????????????????");
            return;
        }

        //????????????
        List<Map<String, Object>> fileList = new ArrayList<>();
        for (UploadFileBean uploadFileBean : mChooseFileAdapter.getInfos()) {
            Map<String, Object> fileMap = new HashMap<>();
            fileMap.put("filePath", uploadFileBean.getUrl());
            fileMap.put("fileName", uploadFileBean.getName());
            fileList.add(fileMap);
        }
        paramMap.put("meetingFileReqBOList", fileList);

        LogUtils.json(paramMap);
        String message = "???????????????????????????????????????";
        if (approvalStatusStrs == 0) {
            message = "???????????????????????????????????????????????????";
        }
        new MessageDialog.Builder()
                .setTitle("????????????")
                .setMessage(message)
                .setOnViewItemClickListener(v -> {
                    if (pageFrom == 1) {
                        //????????????
                        paramMap.put("id", mMeetingDetailsBean.getId());
                        mPresenter.draftSubmitMeetingRecord(paramMap);
                    } else {
                        //?????????????????????
                        mPresenter.createMeetingRecord(paramMap);
                    }
                })
                .showPopupWindow();
    }

    public static final String DOC = "application/msword";
    public static final String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    public static final String XLS = "application/vnd.ms-excel application/x-excel";
    public static final String XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String PPT = "application/vnd.ms-powerpoint";
    public static final String PPTX = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    public static final String PDF = "application/pdf";
    public static final String TEXT = "text/plain";

    private void showFileChooser() {
//        Uri uri = Uri.parse("content://com.android.externalstorage.documents/document/primary:");
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri);
//        intent.putExtra("android.content.extra.SHOW_ADVANCED", false);
//        intent.putExtra(" android.content.extra.FANCY", false);
//        intent.putExtra("android.content.extra.SHOW_FILESIZE", false);
//        //??????doc,docx,ppt,pptx,pdf 5?????????
//        intent.setType("*/*");
//        /*intent.setAction(Intent.ACTION_GET_CONTENT);*/
////        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//        //???API>=19?????????????????????????????????????????????setType????????????????????????
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{DOC, DOCX, PPT, PPTX, PDF, XLS, XLSX, TEXT});
//        }
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        try {
//            startActivityForResult(Intent.createChooser(intent, "????????????"), Constants.RESULT_CHOOSE_FILE_CODE);
//        } catch (android.content.ActivityNotFoundException ex) {
//            ArmsUtils.snackbarText("??????????????????????????????.");
//        }

        new LFilePicker()
                .withActivity(getActivity())
                .withRequestCode(Constants.RESULT_CHOOSE_FILE_CODE)
//                .withStartPath("/storage/emulated/0/Download")
//                .withIsGreater(false)
//                .withFileSize(500 * 1024)
                .withMutilyMode(false)
                .withIconStyle(Constant.ICON_STYLE_BLUE)
                .withTitle("????????????")
                .withFileFilter(new String[]{".txt", ".docx", ".doc", ".pdf", ".xls", ".xlsx", ".pptx"})
                .start();
    }


    /**
     * ??????????????????
     */
    private void chioseReportTime() {
        List<DictClassifyBean> dictMeetingCheckInTime = CacheUtils.queryDictData().getDictMeetingCheckInTime();
        List<String> strArr = new ArrayList<>();
        for (DictClassifyBean dictClassifyBean :
                dictMeetingCheckInTime) {
            strArr.add(dictClassifyBean.getSysDictName());
        }
        if (CollectionUtils.isEmpty(strArr)) return;
        //???????????????
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            this.meetingOption = options1;
            //?????????????????????????????????????????????
            mBarReport.setLeftText(strArr.get(options1));
        })
                .setTitleText("??????????????????")
                .build();
        pvOptions.setPicker(strArr);
        pvOptions.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RESULT_COMMON_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    //???????????????????????????
                    userInfoBeans = data.getParcelableArrayListExtra("userInfoBeans");
                    //??????????????????
                    mBarCheckedUser.setLeftText(mPresenter.generateStrUserNames(userInfoBeans));
                }
                break;
            case Constants.RESULT_CHOOSE_COMPANY_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    AddressCompanyBean companyBean = data.getParcelableExtra("addressCompanyBean");
                    if (mPresenter.checkedCompany(companyBean, mAddressCompanyBeans)) {
                        ArmsUtils.snackbarText("?????????????????????");
                        return;
                    }
                    //???????????????????????????
                    mAddressCompanyBeans.add(companyBean);
                    mChooseCompanyAdapter.notifyDataSetChanged();
                }
                break;

            case Constants.RESULT_CHOOSE_FILE_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    // Get the Uri of the selected file
//                    Uri uri = data.getData();
//                    Timber.e("??????Uri: " + uri.toString());
                    //??????????????????????????????????????????????????????????????????????????????
                    //List<String> list = data.getStringArrayListExtra(Constant.RESULT_INFO);//Constant.RESULT_INFO == "paths"
                    List<String> list = data.getStringArrayListExtra("paths");
                    //?????????????????????????????????????????????????????????????????????
                    String path = data.getStringExtra("path");
                    if (CollectionUtils.isNotEmpty(list)) {
                        List<File> fileList = new ArrayList<>();
                        fileList.add(new File(list.get(0)));
                        mPresenter.uploadFile(fileList);
                    } else {
                        ArmsUtils.snackbarText("?????????????????????????????????");
                    }
                }
                break;
        }
    }

    /**
     * ????????????
     */
    @Override
    public void uploadSuccess(UploadFileBean data) {
        if (CollectionUtils.isEmpty(data.getFileList()) || ObjectUtils.isEmpty(data.getFileList().get(0)))
            return;
        UploadFileBean uploadFileBean = data.getFileList().get(0);
        mFileDetailsBeans.add(uploadFileBean);
        mChooseFileAdapter.notifyDataSetChanged();
    }

    /**
     * ????????????
     */
    @Override
    public void createMeetingSuccess() {
        Utils.postcard(RouterHub.HOME_SUBSCRIBESUCCEESACTIVITY).navigation(this, new NavCallback() {
            @Override
            public void onArrival(Postcard postcard) {
                finish();
            }
        });
    }

    /**
     * ????????????
     */
    @Override
    public void uploadError(UploadFileBean data) {

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