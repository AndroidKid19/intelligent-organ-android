package com.yway.scomponent.user.mvp.ui.activity;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.StringUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.base.BaseActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;

import com.jess.arms.utils.ArmsUtils;

import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import com.wildma.idcardcamera.camera.IDCardCamera;
import com.wildma.idcardcamera.utils.FileUtils;
import com.yway.scomponent.commonres.dialog.IToast;
import com.yway.scomponent.commonres.dialog.ProgresDialog;
import com.yway.scomponent.commonres.utils.InputTextHelper;
import com.yway.scomponent.commonres.view.layout.ClearEditText;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.core.UploadFileBean;
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.commonsdk.utils.CacheUtils;
import com.yway.scomponent.user.R2;
import com.yway.scomponent.user.di.component.DaggerCertificationComponent;
import com.yway.scomponent.user.mvp.contract.CertificationContract;
import com.yway.scomponent.user.mvp.presenter.CertificationPresenter;
import com.yway.scomponent.user.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 实名认证
 */
@Route(path = RouterHub.USER_CERTIFICATIONACTIVITY)
public class CertificationActivity extends BaseActivity<CertificationPresenter> implements CertificationContract.View {

    /**
     * 提醒lable
     */
    @BindView(R2.id.tv_certification_warn)
    AppCompatTextView mTvCertificationWarn;
    /**
     * 认证状态
     */
    @BindView(R2.id.tv_auth_status)
    AppCompatTextView mTvAuthStatus;
    /**
     * 认证失败原因
     */
    @BindView(R2.id.tv_auth_desc)
    AppCompatTextView mTvAuthDesc;
    /**
     * 身份证A面
     */
    @BindView(R2.id.iv_card_a)
    AppCompatImageView mIvCardA;

    /**
     * 身份证B面
     */
    @BindView(R2.id.iv_card_b)
    AppCompatImageView mIvCardB;

    /**
     * 姓名
     */
    @BindView(R2.id.et_user_name)
    ClearEditText mEtUserName;

    /**
     * 身份证号码
     */
    @BindView(R2.id.et_user_card_number)
    ClearEditText mEtUserCardNumber;

    /**
     * 提交View
     */
    @BindView(R2.id.btn_commit)
    AppCompatButton mBtnCommit;

    /**
     * 当前上传是正面还是反面
     * cardAB 1 正面 2 反面
     */
    int cardAB = 1;

    /**
     * 认证提交参数
     **/
    private Map<String, Object> mapParams = new HashMap<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCertificationComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.user_activity_certification; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).titleBar(R.id.bar_title).init();
        //校验身份名字，未输入姓名、号码禁用按钮状态
        InputTextHelper.with(this)
                .addView(mEtUserName)
                .addView(mEtUserCardNumber)
                .setMain(mBtnCommit)
                .build();
        //初始化 认证lable
        String html = "";
        if (StringUtils.isEmpty(CacheUtils.queryName())) {
            html = "请确保清晰完整，属于 <font color='#F12249'>**" + CacheUtils.queryCellPhone().substring(CacheUtils.queryCellPhone().length() - 4) + "</font> 本人";
        } else {
            html = "请确保清晰完整，属于 <font color='#F12249'>**" + CacheUtils.queryName().substring(CacheUtils.queryName().length() - 1) + "</font> 本人";
        }
        mTvCertificationWarn.setText(Html.fromHtml(html));


        initCertifiedStatus();

    }

    /**
     * 初始化认证状态
     */
    private void initCertifiedStatus() {
        //校验是否实名认证
        //certifiedStatus：认证状态：0待提交认证，1已提交认证（认证中），2认证已通过，3认证被驳回
        switch (CacheUtils.queryCertifiedStatus()) {
            case 0:
                break;
            case 1:
                initUserInfo();
                //认证通过隐藏提交按钮
                mBtnCommit.setVisibility(View.GONE);
                mTvAuthStatus.setVisibility(View.VISIBLE);
                mTvAuthStatus.setText("已提交认证，正在审核");
                mEtUserName.setEnabled(false);
                mEtUserCardNumber.setEnabled(false);
                break;
            case 2:
                initUserInfo();
                //认证通过隐藏提交按钮
                mBtnCommit.setVisibility(View.GONE);
                mTvAuthStatus.setVisibility(View.VISIBLE);
                mTvAuthStatus.setText("已完成身份认证");
                mEtUserName.setEnabled(false);
                mEtUserCardNumber.setEnabled(false);
                break;
            case 3:
                initUserInfo();
                mTvAuthStatus.setVisibility(View.VISIBLE);
                mTvAuthStatus.setText("身份认证被驳回");
                break;
        }
    }

    /**
     * 展示个人证件信息
     */
    private void initUserInfo() {
        //获取个人信息
        UserInfoBean userInfoBean = CacheUtils.queryUserInfo();
        //姓名
        mEtUserName.setText(userInfoBean.getName());
        //身份证号
        mEtUserCardNumber.setText(userInfoBean.getCertificateNumber());
        //正面
        mPresenter.imageLoader(userInfoBean.getFrontIdCardUrl(), mIvCardA);
        //反面
        mPresenter.imageLoader(userInfoBean.getAfterIdCardUrl(), mIvCardB);
    }


    @OnClick(R2.id.iv_card_a)
    void onCardAClick(View view) {
        //认证通过禁止上传照片
        if (CacheUtils.isAuthUser())return;
        //正在审核中禁止上传照片
        if (CacheUtils.queryCertifiedStatus() == 1)return;
        IDCardCamera.create(this).openCamera(IDCardCamera.TYPE_IDCARD_FRONT);

    }

    @OnClick(R2.id.iv_card_b)
    void onCardBClick(View view) {
        //认证通过禁止上传照片
        if (CacheUtils.isAuthUser())return;
        //正在审核中禁止上传照片
        if (CacheUtils.queryCertifiedStatus() == 1)return;
        IDCardCamera.create(this).openCamera(IDCardCamera.TYPE_IDCARD_BACK);

    }


    @OnClick(R2.id.btn_commit)
    public void onCommitClick(View v) {
        String mUserName = mEtUserName.getText().toString();
        String mUserCardNumber = mEtUserCardNumber.getText().toString();
        mapParams.put("certifiedStatus", 1);
        mapParams.put("name", mUserName);
        mapParams.put("certificateNumber", mUserCardNumber);
        mPresenter.modifyAppUserInfoById(mapParams);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == IDCardCamera.RESULT_CODE) {
            //获取图片路径，显示图片
            final String path = IDCardCamera.getImagePath(data);
            if (!TextUtils.isEmpty(path)) {
                if (requestCode == IDCardCamera.TYPE_IDCARD_FRONT) { //身份证正面
                    uploadCard(path, 1);
                } else if (requestCode == IDCardCamera.TYPE_IDCARD_BACK) {  //身份证反面
                    uploadCard(path, 2);
                }
            }
        }
    }

    private void uploadCard(String path, int cardAB) {
        this.cardAB = cardAB;
        List<File> files = new ArrayList<>();
        File file = new File(path);
        files.add(file);
        mPresenter.uploadPicture(files);
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
        FileUtils.clearCache(this);
    }

    public Activity getActivity() {
        return this;
    }

    /**
     * 上传成功
     */
    @Override
    public void uploadSuccess(UploadFileBean data) {
        if (CollectionUtils.isEmpty(data.getFileList())) return;
        if (cardAB == 1) {
            mPresenter.imageLoader(data.getFileList().get(0).getUrl(), mIvCardA);
            mapParams.put("frontIdCardUrl", data.getFileList().get(0).getUrl());
        } else {
            mPresenter.imageLoader(data.getFileList().get(0).getUrl(), mIvCardB);
            mapParams.put("afterIdCardUrl", data.getFileList().get(0).getUrl());
        }
    }

    /**
     * 上传失败
     */
    @Override
    public void uploadError(UploadFileBean data) {

    }

    @Override
    public void modifySuccess() {
        IToast.showFinishShort("个人信息已提交");
    }
}