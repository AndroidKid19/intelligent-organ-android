package com.yway.scomponent.organ.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
 * 预约申请
 */
@Route(path = RouterHub.HOME_APPLYROOMACTIVITY)
public class ApplyRoomActivity extends BaseActivity<ApplyRoomPresenter> implements ApplyRoomContract.View {
    /**
     * title
     */
    @BindView(R2.id.bar_title)
    TitleBar mTitleBar;
    /**
     * 会议室名称
     */
    @BindView(R2.id.tv_room_name)
    AppCompatTextView mTvRoomName;
    /**
     * 会议室描述
     */
    @BindView(R2.id.tv_room_count)
    AppCompatTextView mTvRoomCount;
    /**
     * View 设备
     */
    @BindView(R2.id.recycle_view_device)
    RecyclerView mRecyclerView;
    /**
     * 开始时间 - 结束时间
     */
    @BindView(R2.id.bar_date)
    SettingBar mBarDate;
    /**
     * 参会领导
     */
    @BindView(R2.id.bar_choose_user)
    SettingBar mBarCheckedUser;
    /**
     * 报道时间
     */
    @BindView(R2.id.bar_report)
    SettingBar mBarReport;
    /**
     * 选择参会单位
     */
    @BindView(R2.id.bar_choose_company)
    SettingBar mBarChooseCompany;
    /**
     * 选择参会领导
     */
    @BindView(R2.id.recycle_view_company)
    RecyclerView mRecyclerViewCompany;
    /**
     * 选择附件
     */
    @BindView(R2.id.tv_file_choose)
    AppCompatTextView mTvFileChoose;
    /**
     * 选择附件
     */
    @BindView(R2.id.recycle_view_file)
    RecyclerView mRecyclerViewFile;
    /**
     * 会议主题
     */
    @BindView(R2.id.et_metting_subject)
    ClearEditText mEtMettingSubject;
    /**
     * 会议备注
     */
    @BindView(R2.id.et_remark)
    ClearEditText mEtRemark;
    /**
     * 会议设置
     */
    @BindView(R2.id.sc_set_notice1)
    SwitchCompat mScSetNotice1;
    /**
     * 会议设置
     */
    @BindView(R2.id.sc_set_notice2)
    SwitchCompat mScSetNotice2;
    /**
     * 会议设置
     */
    @BindView(R2.id.sc_set_notice3)
    SwitchCompat mScSetNotice3;
    /**
     * 会议设置
     */
    @BindView(R2.id.sc_set_notice4)
    SwitchCompat mScSetNotice4;

    /**
     * 注入列表管理器
     */
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    CheckBoxAdapter mAdapter;
    @Inject
    List<CheckBoxBean> mDataLs;

    /**
     * 记录当前选择的用户
     */
    private List<UserInfoBean> userInfoBeans = new ArrayList<>();

    /**
     * 记录当前选择的参会单位
     */
    @Inject
    List<AddressCompanyBean> mAddressCompanyBeans;
    @Inject
    ChooseCompanyAdapter mChooseCompanyAdapter;

    /**
     * 记录当前选择的文件
     */
    @Inject
    List<UploadFileBean> mFileDetailsBeans;
    @Inject
    ChooseFileAdapter mChooseFileAdapter;

    /**
     * 会议报道时间
     */
    private int meetingOption = 0;

    /**
     * 会议开始时间
     */
    private String meetingStartTime = "";
    /**
     * 会议结束时间
     */
    private String meetingEndTime = "";

    @Autowired(name = "roomDetailsBean")
    RoomDetailsBean mRoomDetailsBean;

    /**
     * 页面标记
     * 1 草稿箱
     * */
    @Autowired
    int pageFrom;

    /**
     * 草稿箱详细信息
     * */
    @Autowired(name = "meetingDetailsBean")
    MeetingDetailsBean mMeetingDetailsBean;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerApplyRoomComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.organ_activity_apply_room; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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


        //初始化草稿箱会议信息
        initDraftsData();
    }


    /**
     * 初始化草稿箱
     */
    private void initDraftsData() {
        if(pageFrom != 1){
            //初始化预约开始时间
            mBarDate.setLeftText(mRoomDetailsBean.getMeetingDate()+" "+mRoomDetailsBean.getSubscribeTimeBean().getTime());
        }else{
            //草稿箱初始化信息

            //初始化会议室信息
            mTvRoomName.setText(mMeetingDetailsBean.getMeetingRoomName());
            mTvRoomCount.setText(Utils.appendStr(mMeetingDetailsBean.getLocation(), " · 可容纳", mMeetingDetailsBean.getSeatsNumber(), "人"));

            //初始化会议主题
            mEtMettingSubject.setText(mMeetingDetailsBean.getMeetingSubject());

            //初始化开始时间
            SubscribeTimeBean subscribeTimeBean = new SubscribeTimeBean();
            subscribeTimeBean.setTime(mRoomDetailsBean.getMeetingDate().split(" ")[1]);

            mRoomDetailsBean.setSubscribeTimeBean(subscribeTimeBean);

            mRoomDetailsBean.setMeetingDate(mRoomDetailsBean.getMeetingDate().split(" ")[0]);
            mBarDate.setLeftText(mRoomDetailsBean.getMeetingDate());

            //初始化报道时间

            //初始化参会领导
            List<UserInfoBean> meetingPersonnelRspBOList = mMeetingDetailsBean.getMeetingPersonnelRspBOList();
            //校验参会人员
            if(CollectionUtils.isNotEmpty(meetingPersonnelRspBOList)){
                //获取已选择用户信息
//                userInfoBeans = meetingPersonnelRspBOList;
                //展示参会领导
//                mBarCheckedUser.setLeftText(mPresenter.generateStrUserNames(userInfoBeans));
            }
            //初始化参会单位
            List<AddressCompanyBean> meetingOrganizationRspBOList = mMeetingDetailsBean.getMeetingOrganizationRspBOList();
            //校验参会单位
            if(CollectionUtils.isNotEmpty(meetingOrganizationRspBOList)){
                //获取已选择单位
                mAddressCompanyBeans.addAll(meetingOrganizationRspBOList);
                mChooseCompanyAdapter.notifyDataSetChanged();
            }

            //初始化会议附件
            List<FileDetailsBean> meetingFileRspBOList = mMeetingDetailsBean.getMeetingFileRspBOList();
            //校验文件
            if(CollectionUtils.isNotEmpty(meetingFileRspBOList)){
                for (FileDetailsBean fileDetailsBean :meetingFileRspBOList) {
                    UploadFileBean uploadFileBean = new UploadFileBean();
                    uploadFileBean.setName(fileDetailsBean.getFileName());
                    uploadFileBean.setUrl(fileDetailsBean.getFilePath());
                    mFileDetailsBeans.add(uploadFileBean);
                }
                mChooseFileAdapter.notifyDataSetChanged();
            }
            //初始化设备

            //初始化备注
            mEtRemark.setText(mMeetingDetailsBean.getRemark());
        }

    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        //初始化设备
        List<DictClassifyBean> dictDevice = CacheUtils.queryDictData().getDictDevice();
        for (DictClassifyBean dictClassifyBean : dictDevice) {
            CheckBoxBean checkBoxBean = new CheckBoxBean();
            checkBoxBean.setCheckBoxName(dictClassifyBean.getSysDictName());
            checkBoxBean.setSysDictCode(dictClassifyBean.getSysDictCode());
            mDataLs.add(checkBoxBean);
        }

        //初始化参会单位列表
        ArmsUtils.configRecyclerView(mRecyclerViewCompany, new LinearLayoutManager(this));
        mRecyclerViewCompany.setAdapter(mChooseCompanyAdapter);

        //初始化参会文件
        ArmsUtils.configRecyclerView(mRecyclerViewFile, new LinearLayoutManager(this));
        mRecyclerViewFile.setAdapter(mChooseFileAdapter);

        mTvRoomName.setText(mRoomDetailsBean.getName());
        mTvRoomCount.setText(Utils.appendStr(mRoomDetailsBean.getLocation(), " · 可容纳", mRoomDetailsBean.getSeatsNumber(), "人"));
    }


    /**
     * 选择参会人员
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
     * 选择参会单位
     */
    @OnClick(R2.id.bar_choose_company)
    void onBarChooseCompanyClick(View view) {
        Utils.postcard(RouterHub.HOME_CHOOSECOMPANYACTIVITY)
                .navigation(getActivity(), Constants.RESULT_CHOOSE_COMPANY_CODE);
    }

    /**
     * 报道时间选择
     */
    @OnClick(R2.id.bar_report)
    void onBarReportClick(View view) {
        chioseReportTime();
    }


    /**
     * 选择会议时间
     */
    @OnClick(R2.id.bar_date)
    void onBarDateClick(View view) {
        SubscribeTimeBean subscribeTimeBean = mRoomDetailsBean.getSubscribeTimeBean();
        int hour = Integer.parseInt(subscribeTimeBean.getTime().split(":")[0]);
        int minute = Integer.parseInt(subscribeTimeBean.getTime().split(":")[1]);
        new TimerScheduleDialog.Builder()
                .initHour(hour,minute)
                .setOnTimepickerClickListener((startTime, endTime) -> {
                    Timber.i(startTime + "---" + endTime);
                    startTime = startTime.length() == 4 ? Utils.appendStr("0",startTime) : startTime;
                    endTime = endTime.length() == 4 ? Utils.appendStr("0",endTime) : endTime;

                    this.meetingStartTime = mRoomDetailsBean.getMeetingDate()+" "+startTime;
                    this.meetingEndTime = mRoomDetailsBean.getMeetingDate()+" "+endTime;
                    mBarDate.setLeftText(Utils.appendStr(mRoomDetailsBean.getMeetingDate()," ",startTime, "-", endTime));
                })
                .showPopupWindow();
    }

    /**
     * 选择附件
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
     * 提交
     */
    @OnClick(R2.id.btn_commit)
    void onCommitClick(View view) {
        applyComit(1);

    }

    private void applyComit(int approvalStatusStrs) {
        //创建参数
        Map<String, Object> paramMap = new HashMap<>();
        //会议室ID
        if (pageFrom == 1){
            paramMap.put("meetingRoomId", mMeetingDetailsBean.getMeetingRoomId());
        }else{
            paramMap.put("meetingRoomId", mRoomDetailsBean.getId());
        }
        //会议主题
        String meetingSubject = mEtMettingSubject.getText().toString();
        if (StringUtils.isEmpty(meetingSubject)){
            IToast.showWarnShort("请输入会议主题");
            return;
        }
        paramMap.put("meetingSubject", meetingSubject);
        //会议开始时间
        if (StringUtils.isEmpty(meetingStartTime)){
            IToast.showWarnShort("请选择会议结束时间");
            return;
        }
        paramMap.put("meetingStartTime", meetingStartTime);

        if (StringUtils.isEmpty(meetingEndTime)){
            IToast.showWarnShort("请选择会议结束时间");
            return;
        }
        //会议结束时间
        paramMap.put("meetingEndTime",meetingEndTime);
        //会议报到时间（单位为分钟）字典code
        String meetingCheckInTime = CacheUtils.queryDictData().getDictMeetingCheckInTime().get(meetingOption).getSysDictCode();
        paramMap.put("meetingCheckInTime", meetingCheckInTime);
        //会议设备需求（数据来源于字典项，多个值已逗号分割）
        String meetingDeviceNeeds = mAdapter.getCheckedDevice();
        paramMap.put("meetingDeviceNeeds", meetingDeviceNeeds);
        //会议设置（数据来源于字典项，多个值已逗号分割）
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
        if (!StringUtils.isEmpty(meetingSet)){
            meetingSet = meetingSet.substring(0, meetingSet.length() - 1);
        }

        paramMap.put("meetingSet", meetingSet);

        //预约会议审批状态：0待提交审批（草稿），1已提交审批（待审批），2审批已通过，3审批被驳回
        paramMap.put("approvalStatus", approvalStatusStrs);
        //会议备注
        String remark = mEtRemark.getText().toString();
        paramMap.put("remark", remark);
        //参会领导（人员）
        List<Map<String, Object>> userList = new ArrayList<>();
        for (UserInfoBean userInfoBean : userInfoBeans) {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("userId", userInfoBean.getUserId());
            userList.add(userMap);
        }
        paramMap.put("meetingPersonnelReqBOList", userList);

        //参会单位
        List<Map<String, Object>> companyList = new ArrayList<>();
        for (AddressCompanyBean addressCompanyBean : mChooseCompanyAdapter.getInfos()) {
            Map<String, Object> companyMap = new HashMap<>();
            companyMap.put("orgId", addressCompanyBean.getOrgId());
            companyList.add(companyMap);
        }
        paramMap.put("meetingOrganizationReqBOList", companyList);
        //会议附件
        List<Map<String, Object>> fileList = new ArrayList<>();
        for (UploadFileBean uploadFileBean : mChooseFileAdapter.getInfos()) {
            Map<String, Object> fileMap = new HashMap<>();
            fileMap.put("filePath", uploadFileBean.getUrl());
            fileMap.put("fileName", uploadFileBean.getName());
            fileList.add(fileMap);
        }
        paramMap.put("meetingFileReqBOList", fileList);

        LogUtils.json(paramMap);
        String message = "确定提交当前会议室预约吗？";
        if (approvalStatusStrs == 0){
            message = "确定当前会议室预约信息存入草稿吗？";
        }
        new MessageDialog.Builder()
                .setTitle("预约提醒")
                .setMessage(message)
                .setOnViewItemClickListener(v -> {
                    if (pageFrom == 1){
                        //草稿提交
                        paramMap.put("id",mMeetingDetailsBean.getId());
                        mPresenter.draftSubmitMeetingRecord(paramMap);
                    }else{
                        //会议室预约提交
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

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        //设置doc,docx,ppt,pptx,pdf 5种类型
        intent.setType("*/*");
        /*intent.setAction(Intent.ACTION_GET_CONTENT);*/
//        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        //在API>=19之后设置多个类型采用以下方式，setType不再支持多个类型
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{DOC, DOCX, PPT, PPTX, PDF, XLS, XLSX});
        }
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "选择文件"), Constants.RESULT_CHOOSE_FILE_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            ArmsUtils.snackbarText("请安装一个文件浏览器.");
        }
    }


    /**
     * 选择报到时间
     */
    private void chioseReportTime() {
        List<DictClassifyBean> dictMeetingCheckInTime = CacheUtils.queryDictData().getDictMeetingCheckInTime();
        List<String> strArr = new ArrayList<>();
        for (DictClassifyBean dictClassifyBean :
                dictMeetingCheckInTime) {
            strArr.add(dictClassifyBean.getSysDictName());
        }
        if (CollectionUtils.isEmpty(strArr)) return;
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            this.meetingOption = options1;
            //返回的分别是三个级别的选中位置
            mBarReport.setLeftText(strArr.get(options1));
        })
                .setTitleText("选择报到时间")
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
                    //获取已选择用户信息
                    userInfoBeans = data.getParcelableArrayListExtra("userInfoBeans");
                    //展示参会领导
                    mBarCheckedUser.setLeftText(mPresenter.generateStrUserNames(userInfoBeans));
                }
                break;
            case Constants.RESULT_CHOOSE_COMPANY_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    AddressCompanyBean companyBean = data.getParcelableExtra("addressCompanyBean");
                    if (mPresenter.checkedCompany(companyBean, mAddressCompanyBeans)) {
                        ArmsUtils.snackbarText("当前组织已选择");
                        return;
                    }
                    //获取已选择用户信息
                    mAddressCompanyBeans.add(companyBean);
                    mChooseCompanyAdapter.notifyDataSetChanged();
                }
                break;

            case Constants.RESULT_CHOOSE_FILE_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Timber.e("文件Uri: " + uri.toString());
                    if (UriUtils.uri2File(uri) != null) {

                        List<File> fileList = new ArrayList<>();
                        fileList.add(UriUtils.uri2File(uri));
                        mPresenter.uploadFile(fileList);

                        Timber.e("选择的文件路径: " + UriUtils.uri2File(uri).getPath());
                    } else {
                        Timber.e("选择的文件路径: " + FileUtils.uri2FilePath(getActivity(), uri));
                    }
                }
                break;
        }
    }

    /**
     * 上传成功
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
     * 预约成功
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
     * 上传失败
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