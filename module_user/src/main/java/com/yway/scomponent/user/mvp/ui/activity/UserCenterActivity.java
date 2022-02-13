package com.yway.scomponent.user.mvp.ui.activity;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import android.os.Build;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.CollectionUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.base.BaseActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;

import com.jess.arms.utils.ArmsUtils;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.jess.arms.utils.PermissionUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.yway.scomponent.commonres.dialog.IToast;
import com.yway.scomponent.commonres.dialog.PhotoDialog;
import com.yway.scomponent.commonres.dialog.ProgresDialog;
import com.yway.scomponent.commonres.utils.GlideEngine;
import com.yway.scomponent.commonres.view.layout.SettingBar;
import com.yway.scomponent.commonsdk.core.AddressCompanyBean;
import com.yway.scomponent.commonsdk.core.Constants;
import com.yway.scomponent.commonsdk.core.DictClassifyBean;
import com.yway.scomponent.commonsdk.core.EventBusHub;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.commonsdk.utils.CacheUtils;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.user.R2;
import com.yway.scomponent.user.di.component.DaggerUserCenterComponent;
import com.yway.scomponent.user.mvp.contract.UserCenterContract;
import com.yway.scomponent.user.mvp.presenter.UserCenterPresenter;
import com.yway.scomponent.user.R;

import org.simple.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人信息
 */
@Route(path = RouterHub.USER_USERCENTERACTIVITY)
public class UserCenterActivity extends BaseActivity<UserCenterPresenter> implements UserCenterContract.View {
    @BindView(R2.id.bar_name)
    SettingBar mBarName;
    @BindView(R2.id.iv_head_sculpture)
    AppCompatImageView mIvHeadImg;
    /**
     * 认证状态
     */
    @BindView(R2.id.bar_card)
    SettingBar mBarCard;
    /**
     * 昵称
     */
    @BindView(R2.id.bar_nickname)
    SettingBar mBarNickName;
    /**
     * 性别
     */
    @BindView(R2.id.bar_sex)
    SettingBar mBarSex;
    /**
     * 身份证号码
     */
    @BindView(R2.id.bar_card_number)
    SettingBar mBarCardNumber;
    /**
     * 手机号
     */
    @BindView(R2.id.bar_mobile)
    SettingBar mBarMobile;
    /**
     * 所在单位
     */
    @BindView(R2.id.bar_organ)
    SettingBar mBarOrgan;
    /**
     * 所任职务
     */
    @BindView(R2.id.bar_jop)
    SettingBar mBarJop;

    //定义选择相机头像
    private List<LocalMedia> selectList = new ArrayList<>();
    //定义头像路径
    private String pathHead;
    //记录选择的性别
    private int sex = 0;
    //记录选择的岗位
    private int jop = 0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUserCenterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.user_activity_user_center; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).titleBar(R.id.bar_title).init();
        //初始化认证状态
        initCertifiedStatus();
        //初始化个人信息
        initUserData();
    }

    private void initUserData() {
        //显示个人信息
        mBarName.setRightText(CacheUtils.queryName());
        mBarNickName.setRightText(CacheUtils.queryNickName());
        mBarMobile.setRightText(CacheUtils.queryCellPhone());
        mBarCardNumber.setRightText(CacheUtils.queryUserInfo().getCertificateNumber());
        mPresenter.imageLoader(CacheUtils.queryUserInfo().getSysUserFilePath(), mIvHeadImg);
        mBarSex.setRightText(CacheUtils.queryUserInfo().getSex() == 0 ? "女" : "男");
        mBarOrgan.setRightText(CacheUtils.queryUserInfo().getOrgTitle());
        mBarJop.setRightText(CacheUtils.queryDictValue(CacheUtils.queryDictData().getDictJop(),CacheUtils.queryUserInfo().getPosition()+""));
    }

    @OnClick(R2.id.bar_organ)
    void onOrganClick(){
        Utils.postcard(RouterHub.HOME_CHOOSECOMPANYACTIVITY)
                .navigation(getActivity(), Constants.RESULT_CHOOSE_COMPANY_CODE);
    }

    @OnClick(R2.id.bar_jop)
    void onJopClick(){
        chioseJop();
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
                mBarCard.setRightText("正在审核");
                break;
            case 2:
                mBarCard.setRightText("已完成身份认证");
                break;
            case 3:
                mBarCard.setRightText("身份认证被驳回");
                break;
        }
    }

    @OnClick({R2.id.bar_nickname})
    void onBarNameClick(View view) {
        Utils.postcard(RouterHub.USER_MODIFYUSERINFOACTIVITY)
                .navigation(getActivity(), Constants.RESULT_COMMON_CODE);
    }

    /***
     * 用户信息
     * */
    @OnClick({R2.id.iv_head_sculpture})
    void onUserInfoClick(View view) {
        PermissionUtil.launchCamera(mRequestPermission, mPresenter.getRxPermissions(this), ArmsUtils.obtainAppComponentFromContext(this).rxErrorHandler());

    }

    /***
     * 修改手机号
     * */
    @OnClick({R2.id.bar_mobile})
    void onBarMobileClick(View view) {
        Utils.navigation(this, RouterHub.USER_MODIFYPHONEACTIVITY);
    }

    /***
     * 性别修改
     * */
    @OnClick({R2.id.bar_sex})
    void onBarSexClick(View view) {
        chioseSex();
    }

    /**
     * @return
     * @description 选择性别
     * @date: 2020/12/4 14:37
     * @author: YIWUANYUAN
     */
    private void chioseSex() {
        List<String> strArr = new ArrayList<>();
        strArr.add("女");
        strArr.add("男");
        if (CollectionUtils.isEmpty(strArr)) return;
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            //返回的分别是三个级别的选中位置
            this.sex = options1;
            mBarSex.setRightText(strArr.get(options1));
            Map<String, Object> mapParams = new HashMap<>();
            mapParams.put("sex", options1);
            mPresenter.modifyAppUserInfoById(mapParams);
        })
                .setTitleText("选择性别")
                .build();
        pvOptions.setPicker(strArr);
        pvOptions.show();
    }

    /**
     * @return
     * @description 选择岗位
     * @date: 2020/12/4 14:37
     * @author: YIWUANYUAN
     */
    private void chioseJop() {
        List<DictClassifyBean> dictJop = CacheUtils.queryDictData().getDictJop();
        List<String> strArr = new ArrayList<>();
        for (DictClassifyBean dictClassifyBean :
                dictJop) {
            strArr.add(dictClassifyBean.getSysDictName());
        }
        if (CollectionUtils.isEmpty(strArr)) return;
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            //返回的分别是三个级别的选中位置
            this.jop = options1;
            mBarJop.setRightText(strArr.get(options1));
            Map<String, Object> mapParams = new HashMap<>();
            mapParams.put("position", dictJop.get(options1).getSysDictCode());
            mPresenter.modifyAppUserInfoById(mapParams);
        })
                .setTitleText("选择岗位")
                .build();
        pvOptions.setPicker(strArr);
        pvOptions.show();
    }

    private PermissionUtil.RequestPermission mRequestPermission = new PermissionUtil.RequestPermission() {
        @Override
        public void onRequestPermissionSuccess() {
            choicePhoto();
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
     * 选择图片，从图库、相机
     */
    public void choicePhoto() {
        new PhotoDialog.Builder()
                .setAutoDismiss(false)
                .setListener(new PhotoDialog.OnListener() {
                    @Override
                    public void onCamera(PhotoDialog dialog) {
                        openCamera();
                    }

                    @Override
                    public void onAlbum(PhotoDialog dialog) {
                        openAlbum();
                    }
                }).showPopupWindow();
    }


    private void openCamera() {
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                .isCompress(true)// 是否压缩
                .compressQuality(25)// 图片压缩后输出质量 0~ 100
                .forResult(onResultCallbackListener);
    }

    /**
     * 选择图片
     */
    private void openAlbum() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .theme(R.style.public_picture_white_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
                .isWeChatStyle(false)// 是否开启微信图片选择风格
                .isUseCustomCamera(false)// 是否使用自定义相机
                .isWithVideoImage(false)// 图片和视频是否可以同选
                .maxSelectNum(1)// 最大图片选择数量
                .maxVideoSelectNum(0) // 视频最大选择数量，如果没有单独设置的需求则可以不设置，同用maxSelectNum字段
                .imageSpanCount(4)// 每行显示个数
                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                //.isAndroidQTransform(false)// 是否需要处理Android Q 拷贝至应用沙盒的操作，只针对compress(false); && enableCrop(false);有效,默认处理
                .isOriginalImageControl(false)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .isSingleDirectReturn(true)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isPreviewImage(true)// 是否可预览图片
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .isEnableCrop(false)// 是否裁剪
                .isCompress(true)// 是否压缩
                .compressQuality(25)// 图片压缩后输出质量 0~ 100
                .synOrAsy(false)//同步false或异步true 压缩 默认同步
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .isOpenClickSound(false)// 是否开启点击声音
                .selectionData(selectList)// 是否传入已选图片
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(onResultCallbackListener);
    }

    /**
     * 图片选择结果回调
     */
    private OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
        @Override
        public void onResult(List result) {
            // 图片选择结果回调
            selectList = result;
            // 例如 LocalMedia 里面返回五种path
            // 1.media.getPath(); 为原图path
            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
            // 4.media.getOriginalPath()); media.isOriginal());为true时此字段才有值
            // 5.media.getAndroidQToPath();为Android Q版本特有返回的字段，此字段有值就用来做上传使用
            // 如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩
            for (LocalMedia media : selectList) {
                Log.i(TAG, "是否压缩:" + media.isCompressed());
                Log.i(TAG, "压缩:" + media.getCompressPath());
                Log.i(TAG, "原图:" + media.getPath());
                Log.i(TAG, "是否裁剪:" + media.isCut());
                Log.i(TAG, "裁剪:" + media.getCutPath());
                Log.i(TAG, "是否开启原图:" + media.isOriginal());
                Log.i(TAG, "原图路径:" + media.getOriginalPath());
                Log.i(TAG, "Android Q 特有Path:" + media.getAndroidQToPath());
                File file = null;
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
                    file = new File(media.getAndroidQToPath());
                    if (!file.exists()) {
                        file = new File(media.getPath());
                    }
                } else {
                    file = new File(media.getCompressPath());
                    if (!file.exists()) {
                        file = new File(media.getPath());
                    }
                }
                //初始化图片上传状态
                List<File> files = new ArrayList<>();
                files.add(file);
                mPresenter.modifyHeadPic(files);
            }

        }

        @Override
        public void onCancel() {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RESULT_CHOOSE_COMPANY_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    AddressCompanyBean companyBean = data.getParcelableExtra("addressCompanyBean");
                    //获取已选择用户信息
                    mBarOrgan.setRightText(companyBean.getOrgTitle());

                    Map<String, Object> mapParams = new HashMap<>();
                    mapParams.put("orgId", companyBean.getOrgId());
                    mPresenter.modifyAppUserInfoById(mapParams);
                }
                break;

            case Constants.RESULT_COMMON_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    initUserData();
                }
                break;

        }
    }

    @Override
    public void modifyHeadPicSuccess() {
        UserInfoBean userInfoBean = CacheUtils.queryUserInfo();
        userInfoBean.setSysUserFilePath(mPresenter.getSysUserFilePath());
        //更新缓存昵称
        CacheUtils.cacheIsUserInfo(userInfoBean);
        initUserData();
        //刷新个人信息
        EventBus.getDefault().post(1, EventBusHub.EVENTBUS_TAG_USER_REFRESH);
        IToast.showFinishShort("修改成功");
    }

    @Override
    public void modifySuccess() {
        UserInfoBean userInfoBean = CacheUtils.queryUserInfo();
        userInfoBean.setSex(sex);
        //更新缓存昵称
        CacheUtils.cacheIsUserInfo(userInfoBean);
        initUserData();
        IToast.showFinishShort("修改成功");
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

    @Override
    public void uploadSuccess(String url) {

    }
}