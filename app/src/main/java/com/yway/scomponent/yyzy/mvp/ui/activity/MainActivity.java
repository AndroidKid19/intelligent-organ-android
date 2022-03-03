/*
 * Copyright 2018 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yway.scomponent.yyzy.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.yway.scomponent.commonres.view.bottomnavigationviewex.BottomNavigationViewEx;
import com.yway.scomponent.commonsdk.core.EventBusHub;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonservice.home.service.OrganInfoService;
import com.yway.scomponent.commonservice.user.service.UserInfoService;
import com.yway.scomponent.yyzy.R;
import com.yway.scomponent.yyzy.mvp.ui.adapter.ViewPageAdapter;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import timber.log.Timber;

/**
 * ================================================
 * 宿主 App 的主页
 * ================================================
 */
@Route(path = RouterHub.APP_MAINACTIVITY)
public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {
    @BindView(R.id.navigation_view)
    BottomNavigationViewEx mBottomNavigationViewEx;
    @BindView(R.id.public_main_container)
    ViewPager mViewPager;


    @Autowired(name = RouterHub.HOME_SERVICE_HOMEINFOSERVICE)
    OrganInfoService mOrganInfoService;
    @Autowired(name = RouterHub.USER_SERVICE_USERINFOSERVICE)
    UserInfoService mUserInfoService;

    private long mPressedTime;
    // collections
    private SparseIntArray items;// used for change ViewPager selected item
    private List<Fragment> fragments;// used for ViewPager adapter
    private ViewPageAdapter adapter;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        //这里想展示组件向外提供服务的功能, 模拟下组件向宿主提供一些必要的信息, 这里为了简单就直接返回本地数据不请求网络了
        //set attribute
        mBottomNavigationViewEx.enableItemShiftingMode(false);
        mBottomNavigationViewEx.enableShiftingMode(false);
        mBottomNavigationViewEx.enableAnimation(false);

        //set fragments
        fragments = new ArrayList<>();
        items = new SparseIntArray();
        //当非集成调试阶段, 宿主 App 由于没有依赖其他组件, 所以使用不了对应组件提供的服务
//        if (mHomeInfoService == null) {
//            return;
//        }
        fragments.add(mOrganInfoService.getInfo().getFragment());
        fragments.add(mOrganInfoService.getAddressBookInfo().getFragment());
        fragments.add(mOrganInfoService.getWorkPanelInfo().getFragment());
        fragments.add(mUserInfoService.getInfo().getFragment());

        // add to items for change ViewPager item
        items.put(R.id.i_home, 0);
        items.put(R.id.i_ai, 1);
        items.put(R.id.i_stats, 2);
        items.put(R.id.i_user, 3);

        // set adapter
        adapter = new ViewPageAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(fragments.size());
        initEvent();
    }

    /**
     * set listeners
     */
    private void initEvent() {
        // set listener to change the current item of view pager when click bottom nav item
        mBottomNavigationViewEx.setOnNavigationItemSelectedListener(this);
        // set listener to change the current checked item of bottom nav when scroll view pager
        mViewPager.addOnPageChangeListener(this);
        // center item click listener
//        mFloatingActionButton.setOnClickListener(view -> {
//        });
    }



    /**
     * @description : TODO 跳转页面
     */
    @Subscriber(tag = EventBusHub.EVENTBUS_TAG_HOME_CURRENTITEM_REFRESH, mode = ThreadMode.MAIN)
    public void onCurrentItemEventBus(int code) {
        //使用咱们接收过来的消息
        mViewPager.setCurrentItem(code);
    }

    @Override
    public void onBackPressed() {
        //获取第一次按键时间
        long mNowTime = System.currentTimeMillis();
        //比较两次按键时间差
        if ((mNowTime - mPressedTime) > 2000) {
            ArmsUtils.makeText(getApplicationContext(),
                    "再按一次退出" + ArmsUtils.getString(getApplicationContext(), R.string.public_app_name));
            mPressedTime = mNowTime;
        } else {
            super.onBackPressed();
        }
    }


    /**
     * @Description: 底部菜单切换
     * @Author: Yuanweiwei
     * @CreateDate: 2019/12/10 20:09
     * @Version: 1.0
     */
    private int previousPosition = -1;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // you can write as above.
        // I recommend this method. You can change the item order or counts without update code here.
        int position = items.get(menuItem.getItemId());
        boolean statusBarDarkFont = false;
        switch (menuItem.getItemId()) {
            case R.id.i_home:
            case R.id.i_ai:
            case R.id.i_stats:
                statusBarDarkFont = true;
                break;
            case R.id.i_user:
                statusBarDarkFont = false;
                break;
        }
        //设置共同沉浸式样式
        ImmersionBar.with(this)
                .statusBarColor(R.color.picture_color_transparent)
                .statusBarDarkFont(statusBarDarkFont)
                .init();
        if (previousPosition != position) {
            previousPosition = position;
            mViewPager.setCurrentItem(position, false);
        }

        return true;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * @method
     * set listener to change the current checked item of bottom nav when scroll view pager
     * @description Viewpage 滑动选中
     * @date: 2019/12/10 20:41
     * @author: Yuanweiwei
     * @param position 滑动选中下标
     * @return
     */
    @Override
    public void onPageSelected(int position) {
        mBottomNavigationViewEx.setCurrentItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == 139 ||keyCode == 280) {
            if (event.getRepeatCount() == 0) {

            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
