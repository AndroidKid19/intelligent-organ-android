package com.yway.scomponent.commonres.view.banner;

import android.content.Context;
import android.widget.Scroller;

/**
 * @description : TODO
 * @author : yuanweiwei
 * @date : 2019/2/18 17:23
 */
public class BGABannerScroller extends Scroller {
    private int mDuration = 1000;

    public BGABannerScroller(Context context, int duration) {
        super(context);
        mDuration = duration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}