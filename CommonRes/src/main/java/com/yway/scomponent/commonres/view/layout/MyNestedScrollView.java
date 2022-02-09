package com.yway.scomponent.commonres.view.layout;

/**
 * @ProjectName: Android-Project
 * @Package: com.fatalsignal.outsourcing.commonres.view
 * @ClassName: MyNestedScrollView
 * @Description:
 * @Author: Yuanweiwei
 * @CreateDate: 2020/2/14 13:59
 * @UpdateUser:
 * @UpdateDate: 2020/2/14 13:59
 * @UpdateRemark:
 * @Version: 1.0
 */
//MyNestedScrollView

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.core.widget.NestedScrollView;

/**
 * Created by cc on 17-7-27.
 */
public class MyNestedScrollView extends NestedScrollView {
    private int downX;
    private int downY;
    private int mTouchSlop;
    private ScrollInterface scrollInterface;

    public MyNestedScrollView(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override//事件拦截
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {//判定为滑动
                    return true;//返回true为拦截，父view消费滑动事件
                }
        }
        return super.onInterceptTouchEvent(e);
    }

    /*定义滑动接口*/
    public interface ScrollInterface{
        void onScrollChange(int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (scrollInterface != null) {
            scrollInterface.onScrollChange(l, t, oldl, oldt);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void setOnScrollChangeListener(ScrollInterface t) {
        this.scrollInterface = t;
    }

}