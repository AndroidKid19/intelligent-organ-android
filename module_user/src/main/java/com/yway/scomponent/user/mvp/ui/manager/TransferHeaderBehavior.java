package com.yway.scomponent.user.mvp.ui.manager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class TransferHeaderBehavior extends CoordinatorLayout.Behavior<View> {

    // 列表顶部和title底部重合时，列表的滑动距离。
    private float deltaY;

    public TransferHeaderBehavior() {
    }

    public TransferHeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof RelativeLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (deltaY == 0) {
            deltaY = dependency.getY()/4 - child.getHeight();
        }

        float dy = dependency.getY()/4 - child.getHeight();
        dy = dy < 0 ? 0 : dy;
        float alpha = 1 - (dy / deltaY);
        child.setAlpha(alpha);
        return true;
    }


}
