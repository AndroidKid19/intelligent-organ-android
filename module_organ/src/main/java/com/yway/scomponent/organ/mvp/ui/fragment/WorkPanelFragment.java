package com.yway.scomponent.organ.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.jwsd.libzxing.OnQRCodeListener;
import com.jwsd.libzxing.QRCodeManager;
import com.yway.scomponent.commonres.dialog.IToast;
import com.yway.scomponent.commonres.dialog.MessageDialog;
import com.yway.scomponent.commonres.dialog.ProgresDialog;
import com.yway.scomponent.commonsdk.core.Constants;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.utils.CacheUtils;
import com.yway.scomponent.commonsdk.utils.SystemUtils;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.di.component.DaggerWorkPanelComponent;
import com.yway.scomponent.organ.mvp.contract.WorkPanelContract;
import com.yway.scomponent.organ.mvp.model.entity.ConfigureBean;
import com.yway.scomponent.organ.mvp.presenter.WorkPanelPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 工作台
 */
public class WorkPanelFragment extends BaseFragment<WorkPanelPresenter> implements WorkPanelContract.View {

    public static WorkPanelFragment newInstance() {
        WorkPanelFragment fragment = new WorkPanelFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerWorkPanelComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.organ_fragment_work_panel, container, false);
    }

    /**
     * 在 onActivityCreate()时调用
     */
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        ImmersionBar.with(this).titleBar(R.id.view_arc_bg).statusBarDarkFont(true).init();

    }

    @OnClick(R2.id.tv_menu_subscribe)
    void onMenuSubscribeClick(View view){
        //预约人员
        ConfigureBean configureBean = CacheUtils.initMMKV().decodeParcelable(Constants.APP_COMMON_config, ConfigureBean.class);
        boolean isAuth = false;
        for (ConfigureBean config : configureBean.getList()) {
            if (config.getType() == 3 && config.getUserId().equals(CacheUtils.queryUserId())){
                //预约权限
                isAuth = true;
            }
        }
        if (isAuth){//有预约权限
            Utils.navigation(getActivity(), RouterHub.HOME_CONFERENCEROOMACTIVITY);
        }else{
            IToast.showFinishShort("您无预约权限");
        }

    }

    /**
     * 我的会议
     * */
    @OnClick(R2.id.tv_menu_my_metting)
    void onMyMerringClick(View view){
        Utils.navigation(getActivity(), RouterHub.HOME_MYMEETINGACTIVITY);
    }
    /**
     * 我的预约
     * */
    @OnClick(R2.id.tv_menu_my_subscribe)
    void onMySubscribeClick(View view){
        //预约人员
        ConfigureBean configureBean = CacheUtils.initMMKV().decodeParcelable(Constants.APP_COMMON_config, ConfigureBean.class);
        boolean isAuth = false;
        for (ConfigureBean config : configureBean.getList()) {
            if (config.getType() == 3 && config.getUserId().equals(CacheUtils.queryUserId())){
                //预约权限
                isAuth = true;
            }
        }
        if (isAuth){//有预约权限
            Utils.postcard(RouterHub.HOME_MYSUBSCRIBEACTIVITY).withInt("pageFrom",0).navigation(getActivity());
        }else{
            IToast.showFinishShort("您无预约权限");
        }

    }

    /**
     * 草稿箱
     * */
    @OnClick(R2.id.tv_menu_draft_box)
    void onDraftBoxClick(View view){
        //预约人员
        ConfigureBean configureBean = CacheUtils.initMMKV().decodeParcelable(Constants.APP_COMMON_config, ConfigureBean.class);
        boolean isAuth = false;
        for (ConfigureBean config : configureBean.getList()) {
            if (config.getType() == 3 && config.getUserId().equals(CacheUtils.queryUserId())){
                //预约权限
                isAuth = true;
            }
        }
        if (isAuth){//有预约权限
            Utils.postcard(RouterHub.HOME_MYSUBSCRIBEACTIVITY).withInt("pageFrom",1).navigation(getActivity());
        }else{
            IToast.showFinishShort("您无预约权限");
        }

    }



    /**
     * 我的审核
     * */
    @OnClick(R2.id.tv_menu_apply)
    void onApplyClick(View view){
        //审核人员
        ConfigureBean configureBean = CacheUtils.initMMKV().decodeParcelable(Constants.APP_COMMON_config, ConfigureBean.class);
        boolean isAuth = false;
        for (ConfigureBean config : configureBean.getList()) {
            if (config.getType() == 1 && config.getUserId().equals(CacheUtils.queryUserId())){
                //审核人员
                isAuth = true;
            }
        }
        if (isAuth){//有审核权限
            Utils.navigation(getActivity(), RouterHub.HOME_APPROVEACTIVITY);
        }else{
            IToast.showFinishShort("您无审核权限");
        }
    }

    /**
     * 准备会议
     * */
    @OnClick(R2.id.tv_menu_prepare)
    void onPrepareClick(View view){
        //会务员
        ConfigureBean configureBean = CacheUtils.initMMKV().decodeParcelable(Constants.APP_COMMON_config, ConfigureBean.class);
        boolean isAuth = false;
        for (ConfigureBean config : configureBean.getList()) {
            if (config.getType() == 2 && config.getUserId().equals(CacheUtils.queryUserId())){
                //审核人员
                isAuth = true;
            }
        }
        if (isAuth){//有审核权限
            Utils.navigation(getActivity(), RouterHub.HOME_PREPAREMETINGACTIVITY);
        }else{
            IToast.showFinishShort("您无会务员权限");
        }
    }


    /**
     * 访客记录
     * */
    @OnClick(R2.id.tv_visitor_record)
    void onVisitorRecordClick(View view){
        //访客记录
        ConfigureBean configureBean = CacheUtils.initMMKV().decodeParcelable(Constants.APP_COMMON_config, ConfigureBean.class);
        boolean isAuth = false;
        for (ConfigureBean config : configureBean.getList()) {
            if (config.getType() == 4 && config.getUserId().equals(CacheUtils.queryUserId())){
                //访客记录
                isAuth = true;
            }
        }
        if (isAuth){//有审核权限
            Utils.navigation(getActivity(), RouterHub.HOME_VISITORRECORDACTIVITY);
        }else{
            IToast.showFinishShort("您无访客记录查看权限");
        }
    }

    @OnClick({R2.id.tv_card})
    void onBtnCard(View view){
        PermissionUtil.launchCamera(mRequestPermission, mPresenter.getRxPermissions(getActivity()), ArmsUtils.obtainAppComponentFromContext(getActivity()).rxErrorHandler());
    }


    @OnClick({R2.id.tv_pay})
    void onBtnPay(View view){
        Utils.navigation(getActivity(), RouterHub.HOME_CANTEENACTIVITY);
    }



    private PermissionUtil.RequestPermission mRequestPermission = new PermissionUtil.RequestPermission() {
        @Override
        public void onRequestPermissionSuccess() {
            QRCodeManager.getInstance().with(getActivity()).setReqeustType(1).scanningQRCode(mOnQRCodeListener);
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
     * @description 扫描二维码监听
     * @date: 2020/12/7 18:57
     * @author: Yuan
     * @return
     */
    private OnQRCodeListener mOnQRCodeListener = new OnQRCodeListener() {
        @Override
        public void onCompleted(String result) {
            //获取二维码解析内容
           String transactionType = SystemUtils.getTransactionType(result);
            //支付
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("transactionType", transactionType);
            paramsMap.put("transactionPayType", transactionType);
            paramsMap.put("transactionStatus", 0);
            mPresenter.createAccountTransactionRecord(paramsMap);
        }

        @Override
        public void onError(Throwable errorMsg) {
            ArmsUtils.snackbarText(errorMsg.getMessage());
        }

        @Override
        public void onCancel() {

        }
    };


    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        ProgresDialog.getInstance(getActivity()).show();
    }

    @Override
    public void hideLoading() {
        ProgresDialog.getInstance(getActivity()).dismissDialog();
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

    public Fragment getFragment() {
        return this;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        QRCodeManager.getInstance().with(getActivity()).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void paymentCallBack() {
        new MessageDialog.Builder()
                .setTitle("支付提醒")
                .setMessage("您已支付成功,请取餐")
                .setOnViewItemClickListener(v -> {

                })
                .showPopupWindow();
    }
}