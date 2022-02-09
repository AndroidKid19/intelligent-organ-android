package com.yway.scomponent.wheel.mvp.ui.adapter;

import android.annotation.SuppressLint;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * @ProjectName: Android-Wheel
 * @Package: com.hash.code.wheel.mvp.ui.adapter
 * @ClassName: ViewPageAdapter
 * @Description:
 * @Author: Yuanweiwei
 * @CreateDate: 2019/12/11 10:23
 * @UpdateUser:
 * @UpdateDate: 2019/12/11 10:23
 * @UpdateRemark:
 * @Version: 1.0
 */
public class ViewPageAdapter  extends FragmentPagerAdapter {
    private List<Fragment> data;

    @SuppressLint("WrongConstant")
    public ViewPageAdapter(FragmentManager fm, List<Fragment> data) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.data = data;
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

}
