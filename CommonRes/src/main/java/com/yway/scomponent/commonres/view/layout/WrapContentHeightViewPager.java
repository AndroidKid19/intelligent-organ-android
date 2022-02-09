package com.yway.scomponent.commonres.view.layout;

/**
 * @ProjectName: Android-Project
 * @Package: com.fatalsignal.outsourcing.commonres.view
 * @ClassName: WrapContentHeightViewPager
 * @Description:
 * @Author: Yuanweiwei
 * @CreateDate: 2019/10/12 16:23
 * @UpdateUser:
 * @UpdateDate: 2019/10/12 16:23
 * @UpdateRemark:
 * @Version: 1.0
 */
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class WrapContentHeightViewPager extends ViewPager {

    public WrapContentHeightViewPager(Context context) {
        super(context);
    }

    public WrapContentHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;
        //下面遍历所有child的高度
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) //采用最大的view的高度。
                height = h;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}