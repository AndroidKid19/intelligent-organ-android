package com.yway.scomponent.organ.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yway.scomponent.commonres.view.tablayout.SlidingTabLayout;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.di.component.DaggerMySubscribeComponent;
import com.yway.scomponent.organ.mvp.contract.MySubscribeContract;
import com.yway.scomponent.organ.mvp.presenter.MySubscribePresenter;
import com.yway.scomponent.organ.mvp.ui.adapter.ViewPagerAdapter;
import com.yway.scomponent.organ.mvp.ui.fragment.ApprovedFragment;
import com.yway.scomponent.organ.mvp.ui.fragment.DraftsFragment;
import com.yway.scomponent.organ.mvp.ui.fragment.MyInitiateFragment;
import com.yway.scomponent.organ.mvp.ui.fragment.SubscribeApplyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 我的预约
 */
@Route(path = RouterHub.HOME_MYSUBSCRIBEACTIVITY)
public class MySubscribeActivity extends BaseActivity<MySubscribePresenter> implements MySubscribeContract.View {
    @BindView(R2.id.tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R2.id.view_page_content)
    ViewPager mViewPageContent;

    private List<String> mTitles = null;
    private ViewPagerAdapter mViewPagerAdapter = null;
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMySubscribeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.organ_activity_my_subscribe; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
        mTitles.add("我发起的");
        mTitles.add("草稿箱");
        mFragments.add(MyInitiateFragment.newInstance());
        mFragments.add(DraftsFragment.newInstance());
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