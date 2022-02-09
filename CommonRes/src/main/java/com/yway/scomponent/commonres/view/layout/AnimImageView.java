package com.yway.scomponent.commonres.view.layout;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 *    author : Yuanww
 *    github : https://github.com
 *    time   : 2019/07/06
 *    desc   : 自动播放帧动画的 ImageView
 */
public final class AnimImageView extends AppCompatImageView {

    public AnimImageView(Context context) {
        super(context);
    }

    public AnimImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        // 默认开启帧动画
        startAnimation();
    }

    /**
     * 开启帧动画
     */
    public void startAnimation() {
        // 判断当前的 Drawable 是否为帧动画
        if (getDrawable() instanceof AnimationDrawable) {
            // 如果是的话自动播放动画
            ((AnimationDrawable) getDrawable()).start();
        }
    }

    /**
     * 停止帧动画
     */
    public void stopAnimation() {
        // 判断当前的 Drawable 是否为帧动画
        if (getDrawable() instanceof AnimationDrawable) {
            // 如果是的话自动播放动画
            ((AnimationDrawable) getDrawable()).stop();
        }
    }
}