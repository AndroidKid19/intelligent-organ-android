package com.yway.scomponent.user.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.base.BaseActivity;
import static com.jess.arms.utils.Preconditions.checkNotNull;
import com.jess.arms.utils.ArmsUtils;
import android.content.Intent;
import android.view.View;

import com.jess.arms.utils.PermissionUtil;
import com.yway.scomponent.commonres.dialog.IToast;
import com.yway.scomponent.commonres.dialog.MessageDialog;
import com.yway.scomponent.commonres.view.layout.NiceImageView;
import com.yway.scomponent.commonres.view.layout.SettingBar;
import com.yway.scomponent.commonsdk.core.Constants;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.commonsdk.utils.CacheUtils;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.user.R2;
import com.yway.scomponent.user.di.component.DaggerUserDetailsComponent;
import com.yway.scomponent.user.mvp.contract.UserDetailsContract;
import com.yway.scomponent.user.mvp.presenter.UserDetailsPresenter;
import com.yway.scomponent.user.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人信息
 */
@Route(path = RouterHub.USER_USERDETAILSACTIVITY)
public class UserDetailsActivity extends BaseActivity<UserDetailsPresenter> implements UserDetailsContract.View {

    /**
     * 头像
     */
    @BindView(R2.id.niv_head)
    NiceImageView mNiceImageView;
    /**
     * 姓名
     */
    @BindView(R2.id.tv_username)
    AppCompatTextView mTvUserName;
    /**
     * 岗位
     */
    @BindView(R2.id.tv_jop)
    AppCompatTextView mTvUserOffice;
    /**
     * 部门
     */
    @BindView(R2.id.bar_class)
    SettingBar mBarClass;
    /**
     * 电话
     */
    @BindView(R2.id.bar_phone)
    SettingBar mBarPhone;


    @Autowired(name = Constants.APP_USER_INFO)
    UserInfoBean mUserInfoBean;

    @Autowired(name = Constants.APP_USER_ORGAN)
    String mOrganName;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUserDetailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.user_activity_user_details; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.imageLoader(mUserInfoBean.getSysUserFilePath(),mNiceImageView);
        mTvUserName.setText(mUserInfoBean.getName());
        mBarPhone.setLeftText(mUserInfoBean.getCellPhone());

        if (ObjectUtils.isEmpty(CacheUtils.queryDictData()) || CollectionUtils.isEmpty(CacheUtils.queryDictData().getDictJop())){
        }else{
            String jop = Utils.appendStr(CacheUtils.queryDictValue(CacheUtils.queryDictData().getDictJop(),mUserInfoBean.getPosition()+""));
            mTvUserOffice.setText(jop);
        }
        mBarClass.setLeftText(mOrganName);
    }

    @OnClick({R2.id.btn_sendmsg})
    void onBtnSendMsg(View view){
        IToast.showWarnShort("功能正在开发中");
    }


    /**
     * 拨打电话
     */
    @OnClick(R2.id.btn_call_user)
    void onCallPhoneUserOnClick(View view) {
        if (StringUtils.isEmpty(mUserInfoBean.getCellPhone())) {
            IToast.showWarnShort("暂未查询到手机号");
            return;
        }
        callPhone();
//        PermissionUtil.callPhone(mRequestPermission, mPresenter.getRxPermissions(this), ArmsUtils.obtainAppComponentFromContext(this).rxErrorHandler());
    }

    private PermissionUtil.RequestPermission mRequestPermission = new PermissionUtil.RequestPermission() {
        @Override
        public void onRequestPermissionSuccess() {
            callPhone();
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

    private void callPhone() {
        new MessageDialog.Builder()
                .setTitle("拨号提醒")
                .setMessage(Utils.appendStr("确定拨打", mUserInfoBean.getCellPhone(), "号码吗?"))
                .setOnViewItemClickListener(v -> {UserInfoBean callRecordsBean = CacheUtils.initMMKV().decodeParcelable("call_records", UserInfoBean.class);
                    List<UserInfoBean> userInfoBeans = new ArrayList<>();
                    if (ObjectUtils.isNotEmpty(callRecordsBean) && CollectionUtils.isNotEmpty(callRecordsBean.getList())) {
                        userInfoBeans.addAll(callRecordsBean.getList());
                    }
                    //创建记录
//                    mUserInfoBean.setCallDate(TimeUtils.getNowString(new SimpleDateFormat("yyyy/MM/dd HH:mm")));
//                    userInfoBeans.add(mUserInfoBean);
//
//                    UserInfoBean userInfoBean = new UserInfoBean();
//                    userInfoBean.setList(userInfoBeans);
//                    //缓存拨号记录
//                    CacheUtils.initMMKV().encode("call_records", userInfoBean);
                    //拨号
                    PhoneUtils.dial(mUserInfoBean.getCellPhone());

                })
                .showPopupWindow();
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