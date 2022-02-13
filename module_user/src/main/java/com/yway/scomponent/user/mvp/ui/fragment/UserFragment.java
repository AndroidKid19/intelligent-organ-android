package com.yway.scomponent.user.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.EventBusManager;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yway.scomponent.commonres.view.layout.NiceImageView;
import com.yway.scomponent.commonres.view.tablayout.listener.OnTabSelectListener;
import com.yway.scomponent.commonsdk.core.EventBusHub;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.commonsdk.utils.CacheUtils;
import com.yway.scomponent.commonsdk.utils.DrawablesUtil;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.user.R;
import com.yway.scomponent.user.R2;
import com.yway.scomponent.user.di.component.DaggerUserComponent;
import com.yway.scomponent.user.mvp.contract.UserContract;
import com.yway.scomponent.user.mvp.presenter.UserPresenter;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: TODO 我的
 * <p>
 * Created by yuanweiwei on 09/19/2019 16:42
 * <a href="mailto:yuanw9@sina.com">Contact me</a>
 * <a href="https://github.com/AndroidKid19">Follow me</a>
 * ===============================================
 */
public class UserFragment extends BaseFragment<UserPresenter> implements UserContract.View, OnTabSelectListener {

    @BindView(R2.id.refresh_layout)
    RefreshLayout mRefreshLayout;
    @BindView(R2.id.niv_head)
    NiceImageView mNivHead;
    @BindView(R2.id.tv_username)
    AppCompatTextView mTvUsername;
    @BindView(R2.id.tv_user_auth)
    AppCompatTextView mTvUserAuth;
    @BindView(R2.id.btn_auth)
    AppCompatButton mTBtnAuth;
    @BindView(R2.id.tv_jop)
    AppCompatTextView mTvJop;


    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerUserComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_fragment_user, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBusManager.getInstance().register(getActivity());
        ImmersionBar.with(this).titleBar(R.id.view_arc_bg).statusBarDarkFont(true).init();
        //禁用上拉下拉
        mRefreshLayout.setEnableLoadMore(false);
        //设置下拉刷新监听
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        initViewData();

        initUserInfoData();
    }

    /**
     * @description : TODO 下拉监听
     * @author : yuanweiwei
     */
    private OnRefreshListener mOnRefreshListener = refreshLayout -> {
        initUserInfoData();
    };

    private void initUserInfoData() {
        Map<String, Object> paramMap = new HashMap<>();
        mPresenter.queryloginUserInfoById(paramMap);
    }

    /**
     * @param view
     * @return void
     * @description 个人中心
     * @date: 2020/12/1 18:21
     * @author: Yuan
     */
    @OnClick({R2.id.niv_head})
    void onUserCenterClick(View view) {
        Utils.postcard(RouterHub.USER_USERCENTERACTIVITY)
                .navigation(getActivity(), 1);
    }


    /***
     * 提醒
     * */
    @OnClick(R2.id.bar_remind)
    void onAuthClick(View view) {
        Utils.navigation(getActivity(), RouterHub.USER_DOCTORAUTHACTIVITY);
    }

    /***
     * 收藏
     * */
    @OnClick(R2.id.bar_collections)
    void onEvaluateClick(View view) {
        Utils.navigation(getActivity(), RouterHub.USER_COLLECTACTIVITY);
    }

    /***
     * 实名认证
     * */
    @OnClick(R2.id.btn_auth)
    void onPerformanceClick(View view) {
        Utils.navigation(getActivity(), RouterHub.USER_CERTIFICATIONACTIVITY);
    }

    /**
     * @param view
     * @return
     * @description 设置
     * @date: 2020/7/2 19:29
     */
    @OnClick(R2.id.bar_setting)
    void onSettingClick(View view) {
        Utils.navigation(getActivity(), RouterHub.USER_SETTINGACTIVITY);
    }

    /**
     * @description : TODO 刷新个人信息
     */
    @Subscriber(tag = EventBusHub.EVENTBUS_TAG_USER_REFRESH, mode = ThreadMode.MAIN)
    public void onRefreshEventBus(int code) {
        //使用咱们接收过来的消息
        initViewData();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        mRefreshLayout.finishRefresh();
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

    /***
     * 查询用户信息
     * */
    @Override
    public void queryUserInfoSuccess(UserInfoBean data) {
        //缓存个人信息
        CacheUtils.cacheIsUserInfo(data);
        //刷新个人信息
        initViewData();
    }

    /**
     * @return
     * @description 初始化view 数据
     * @date: 2020/7/14 14:51
     * @author: YIWUANYUAN
     */
    private void initViewData() {
        //初始化个人信息
        mTvUsername.setText(CacheUtils.queryName());
        if (ObjectUtils.isEmpty(CacheUtils.queryDictData()) || CollectionUtils.isEmpty(CacheUtils.queryDictData().getDictJop())){
            mTvJop.setText(CacheUtils.queryUserInfo().getOrgTitle());
        }else{
            String jop = Utils.appendStr(CacheUtils.queryUserInfo().getOrgTitle(),"-",CacheUtils.queryDictValue(CacheUtils.queryDictData().getDictJop(),CacheUtils.queryUserInfo().getPosition()+""));
            mTvJop.setText(jop);
        }

        mPresenter.imageLoader(CacheUtils.queryUserInfo().getSysUserFilePath(), mNivHead);
        if (CacheUtils.isAuthUser()) {
            mTvUserAuth.setText("已完成实名认证");
            mTBtnAuth.setText("查看");
            DrawablesUtil.setLeftDrawable(mTvUserAuth, R.mipmap.user_ic_left_auth);
        } else {
            mTvUserAuth.setText("请完成实名认证");
            mTBtnAuth.setText("认证");
            DrawablesUtil.setLeftDrawable(mTvUserAuth, R.mipmap.user_ic_left_no_auth);
        }

    }


    @Override
    public void onPause() {
        super.onPause();
    }


}
