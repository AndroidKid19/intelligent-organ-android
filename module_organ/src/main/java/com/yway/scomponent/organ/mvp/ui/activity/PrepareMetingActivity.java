package com.yway.scomponent.organ.mvp.ui.activity;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.base.BaseActivity;
import static com.jess.arms.utils.Preconditions.checkNotNull;
import com.jess.arms.utils.ArmsUtils;
import android.content.Intent;
import com.yway.scomponent.commonres.view.tablayout.SlidingTabLayout;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.di.component.DaggerPrepareMetingComponent;
import com.yway.scomponent.organ.mvp.contract.PrepareMetingContract;
import com.yway.scomponent.organ.mvp.presenter.PrepareMetingPresenter;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.mvp.ui.adapter.ViewPagerAdapter;
import com.yway.scomponent.organ.mvp.ui.fragment.AfterPrepareFragment;
import com.yway.scomponent.organ.mvp.ui.fragment.ApprovedFragment;
import com.yway.scomponent.organ.mvp.ui.fragment.BeforePrepareFragment;
import com.yway.scomponent.organ.mvp.ui.fragment.SubscribeApplyFragment;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import timber.log.Timber;

/**
 * 准备会议
 */
@Route(path = RouterHub.HOME_PREPAREMETINGACTIVITY)
public class PrepareMetingActivity extends BaseActivity<PrepareMetingPresenter> implements PrepareMetingContract.View {
    @BindView(R2.id.tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R2.id.view_page_content)
    ViewPager mViewPageContent;

    private List<String> mTitles = null;
    private ViewPagerAdapter mViewPagerAdapter = null;
    private ArrayList<Fragment> mFragments = new ArrayList<>();


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPrepareMetingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.organ_activity_prepare_meting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).titleBar(R.id.bar_title).init();

        initViewPager();
    }

    /**
     * 初始化viewpager
     **/
    private void initViewPager() {
        mTitles = new ArrayList<>();
        mTitles.add("待准备");
        mTitles.add("已准备");
        mFragments.add(BeforePrepareFragment.newInstance());
        mFragments.add(AfterPrepareFragment.newInstance());
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mViewPageContent.setAdapter(mViewPagerAdapter);
        mTabLayout.setViewPager(mViewPageContent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int indext = 0; indext < fragmentManager.getFragments().size(); indext++) {
            Fragment fragment = fragmentManager.getFragments().get(indext); //找到第一层Fragment
            if (fragment == null)
                Timber.w("Activity result no fragment exists for index: 0x"
                        + Integer.toHexString(requestCode));
            else
                handleResult(fragment, requestCode, resultCode, data);
        }
    }

    /**
     * 递归调用，对所有的子Fragment生效
     *
     * @param fragment
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleResult(Fragment fragment, int requestCode, int resultCode, Intent data) {
        fragment.onActivityResult(requestCode, resultCode, data);//调用每个Fragment的onActivityResult
        Timber.w("handleResult");
        List<Fragment> childFragment = fragment.getChildFragmentManager().getFragments(); //找到第二层Fragment
        if (childFragment != null)
            for (Fragment f : childFragment)
                if (f != null) {
                    handleResult(f, requestCode, resultCode, data);
                }
        if (childFragment == null)
            Timber.e("MyBaseFragmentActivity1111");
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