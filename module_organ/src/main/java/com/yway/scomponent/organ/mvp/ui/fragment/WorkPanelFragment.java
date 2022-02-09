package com.yway.scomponent.organ.mvp.ui.fragment;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import static com.jess.arms.utils.Preconditions.checkNotNull;
import com.jess.arms.utils.ArmsUtils;
import android.content.Intent;

import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.di.component.DaggerWorkPanelComponent;
import com.yway.scomponent.organ.mvp.contract.WorkPanelContract;
import com.yway.scomponent.organ.mvp.presenter.WorkPanelPresenter;
import com.yway.scomponent.organ.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2021/11/15 11:31
 *
 * @author YWW
 * module name is WorkPanelFragment
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
        Utils.navigation(getActivity(), RouterHub.HOME_CONFERENCEROOMACTIVITY);
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
        Utils.navigation(getActivity(), RouterHub.HOME_MYSUBSCRIBEACTIVITY);
    }

    /**
     * 我的审核
     * */
    @OnClick(R2.id.tv_menu_apply)
    void onApplyClick(View view){
        Utils.navigation(getActivity(), RouterHub.HOME_APPROVEACTIVITY);
    }

    /**
     * 准备会议
     * */
    @OnClick(R2.id.tv_menu_prepare)
    void onPrepareClick(View view){
        Utils.navigation(getActivity(), RouterHub.HOME_APPROVEACTIVITY);
    }


    /**
     * 访客记录
     * */
    @OnClick(R2.id.tv_visitor_record)
    void onVisitorRecordClick(View view){
        Utils.navigation(getActivity(), RouterHub.HOME_VISITORRECORDACTIVITY);
    }

    @Override
    public void setData(@Nullable Object data) {

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

    public Fragment getFragment() {
        return this;
    }
}