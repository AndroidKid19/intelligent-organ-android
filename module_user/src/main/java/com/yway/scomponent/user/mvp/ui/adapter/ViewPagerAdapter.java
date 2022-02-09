package com.yway.scomponent.user.mvp.ui.adapter;

import android.annotation.SuppressLint;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.yway.scomponent.user.mvp.model.entity.ChannelBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Version: 1.0
 */
public  class ViewPagerAdapter extends FragmentPagerAdapter {


    private List<Fragment> mFragments;
    private List<ChannelBean> mTitles  = null;

    @SuppressLint("WrongConstant")
    public ViewPagerAdapter(FragmentManager fm, List<Fragment> mFragments, List<ChannelBean> mTitles) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mFragments = mFragments;
        this.mTitles = mTitles;
    }

    public List<Fragment> getFragments() {
        if (mFragments == null) {
            return new ArrayList<>();
        }
        return mFragments;
    }

    public void setFragments(List<Fragment> fragments) {
        mFragments = fragments;
    }

    /**
     * Create the page for the given position. The adapter is responsible for
     * adding the view to the container given here,
     * although it only must ensure this is done by the time it returns from finishUpdate(ViewGroup).
     * 这个同destroyItem（）相反，是对于给定的位置创建视图，适配器往container中添加
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = null;
        fragment = (Fragment) super.instantiateItem(container,position);
        return fragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position).getChannelText();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
}