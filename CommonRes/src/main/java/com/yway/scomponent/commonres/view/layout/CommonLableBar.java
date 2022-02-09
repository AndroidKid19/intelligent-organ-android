package com.yway.scomponent.commonres.view.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;

import com.yway.scomponent.commonres.R;


/**
 * author : Yuanww
 * github : https://github.com
 * time   : 2019/01/23
 * desc   : 设置条自定义控件
 */
public final class CommonLableBar extends LinearLayoutCompat {

    private final TextView mLeftView;
    private final TextView mRightView;
    private final View mLineView;

    public CommonLableBar(Context context) {
        this(context, null);
    }

    public CommonLableBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonLableBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.public_widget_common_lable_bar, this);
        mLeftView = findViewById(R.id.tv_setting_bar_left);
        mRightView = findViewById(R.id.tv_setting_bar_right);
        mLineView = findViewById(R.id.v_setting_bar_line);

        final TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.SettingBar);

        // 文本设置
        if (array.hasValue(R.styleable.SettingBar_bar_leftText)) {
            setLeftText(array.getString(R.styleable.SettingBar_bar_leftText));
        }

        if (array.hasValue(R.styleable.SettingBar_bar_rightText)) {
            setRightText(array.getString(R.styleable.SettingBar_bar_rightText));
        }

        // 提示设置
        if (array.hasValue(R.styleable.SettingBar_bar_leftHint)) {
            setLeftHint(array.getString(R.styleable.SettingBar_bar_leftHint));
        }

        if (array.hasValue(R.styleable.SettingBar_bar_rightHint)) {
            setRightHint(array.getString(R.styleable.SettingBar_bar_rightHint));
        }

        // 图标设置
        if (array.hasValue(R.styleable.SettingBar_bar_leftIcon)) {
            setLeftIcon(array.getDrawable(R.styleable.SettingBar_bar_leftIcon));
        }

        if (array.hasValue(R.styleable.SettingBar_bar_rightIcon)) {
            setRightIcon(array.getDrawable(R.styleable.SettingBar_bar_rightIcon));
        }

        // 文字颜色设置
        if (array.hasValue(R.styleable.SettingBar_bar_leftColor)) {
            setLeftColor(array.getColor(R.styleable.SettingBar_bar_leftColor, 0));
        }

        if (array.hasValue(R.styleable.SettingBar_bar_rightColor)) {
            setRightColor(array.getColor(R.styleable.SettingBar_bar_rightColor, 0));
        }

        // 文字大小设置
        if (array.hasValue(R.styleable.SettingBar_bar_leftSize)) {
            setLeftSize(TypedValue.COMPLEX_UNIT_PX, array.getDimensionPixelSize(R.styleable.SettingBar_bar_leftSize, 0));
        }

        if (array.hasValue(R.styleable.SettingBar_bar_rightSize)) {
            setRightSize(TypedValue.COMPLEX_UNIT_PX, array.getDimensionPixelSize(R.styleable.SettingBar_bar_rightSize, 0));
        }

        // 分割线设置
        if (array.hasValue(R.styleable.SettingBar_bar_lineColor)) {
            setLineDrawable(array.getDrawable(R.styleable.SettingBar_bar_lineColor));
        }

        if (array.hasValue(R.styleable.SettingBar_bar_lineVisible)) {
            setLineVisible(array.getBoolean(R.styleable.SettingBar_bar_lineVisible, true));
        }

        if (array.hasValue(R.styleable.SettingBar_bar_lineSize)) {
            setLineSize(array.getDimensionPixelSize(R.styleable.SettingBar_bar_lineSize, 0));
        }

        if (array.hasValue(R.styleable.SettingBar_bar_lineMargin)) {
            setLineMargin(array.getDimensionPixelSize(R.styleable.SettingBar_bar_lineMargin, 0));
        }

        // 设置默认背景选择器
        if (getBackground() == null) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.public_widget_bg_settting_bar_selector);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setBackground(drawable);
            } else {
                setBackgroundDrawable(drawable);
            }
        }

        // 回收TypedArray
        array.recycle();
    }

    /**
     * 设置左边的标题
     */
    public CommonLableBar setLeftText(@StringRes int id) {
        return setLeftText(getResources().getString(id));
    }

    public CommonLableBar setLeftText(CharSequence text) {
        mLeftView.setText(text);
        return this;
    }

    public CharSequence getLeftText() {
        return mLeftView.getText();
    }

    /**
     * 设置左边的提示
     */
    public CommonLableBar setLeftHint(@StringRes int id) {
        return setLeftHint(getResources().getString(id));
    }

    public CommonLableBar setLeftHint(CharSequence hint) {
        mLeftView.setHint(hint);
        return this;
    }

    /**
     * 设置右边的标题
     */
    public CommonLableBar setRightText(@StringRes int id) {
        setRightText(getResources().getString(id));
        return this;
    }

    public CommonLableBar setRightText(CharSequence text) {
        mRightView.setText(text);
        return this;
    }

    public CharSequence getRightText() {
        return mRightView.getText();
    }

    /**
     * 设置右边的提示
     */
    public CommonLableBar setRightHint(@StringRes int id) {
        return setRightHint(getResources().getString(id));
    }

    public CommonLableBar setRightHint(CharSequence hint) {
        mRightView.setHint(hint);
        return this;
    }

    /**
     * 设置左边的图标
     */
    public CommonLableBar setLeftIcon(@DrawableRes int id) {
        setLeftIcon(ContextCompat.getDrawable(getContext(), id));
        return this;
    }

    public CommonLableBar setLeftIcon(Drawable drawable) {
        mLeftView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        return this;
    }

    public Drawable getLeftIcon() {
        return mLeftView.getCompoundDrawables()[0];
    }

    /**
     * 设置右边的图标
     */
    public CommonLableBar setRightIcon(@DrawableRes int id) {
        setRightIcon(ContextCompat.getDrawable(getContext(), id));
        return this;
    }

    public CommonLableBar setRightIcon(Drawable drawable) {
        mRightView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        return this;
    }

    public Drawable getRightIcon() {
        return mRightView.getCompoundDrawables()[2];
    }

    /**
     * 设置左标题颜色
     */
    public CommonLableBar setLeftColor(@ColorInt int color) {
        mLeftView.setTextColor(color);
        return this;
    }

    /**
     * 设置右标题颜色
     */
    public CommonLableBar setRightColor(@ColorInt int color) {
        mRightView.setTextColor(color);
        return this;
    }

    /**
     * 设置左标题的文本大小
     */
    public CommonLableBar setLeftSize(int unit, float size) {
        mLeftView.setTextSize(unit, size);
        return this;
    }

    /**
     * 设置右标题的文本大小
     */
    public CommonLableBar setRightSize(int unit, float size) {
        mRightView.setTextSize(unit, size);
        return this;
    }

    /**
     * 设置分割线是否显示
     */
    public CommonLableBar setLineVisible(boolean visible) {
        mLineView.setVisibility(visible ? VISIBLE : GONE);
        return this;
    }

    /**
     * 设置分割线的颜色
     */
    public CommonLableBar setLineColor(@ColorInt int color) {
        return setLineDrawable(new ColorDrawable(color));
    }

    public CommonLableBar setLineDrawable(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mLineView.setBackground(drawable);
        } else {
            mLineView.setBackgroundDrawable(drawable);
        }
        return this;
    }

    /**
     * 设置分割线的大小
     */
    public CommonLableBar setLineSize(int size) {
        ViewGroup.LayoutParams layoutParams = mLineView.getLayoutParams();
        layoutParams.height = size;
        mLineView.setLayoutParams(layoutParams);
        return this;
    }

    /**
     * 设置分割线边界
     */
    public CommonLableBar setLineMargin(int margin) {
        LayoutParams params = (LayoutParams) mLineView.getLayoutParams();
        params.leftMargin = margin;
        params.rightMargin = margin;
        mLineView.setLayoutParams(params);
        return this;
    }

    /**
     * 获取左标题View对象
     */
    public TextView getLeftView() {
        return mLeftView;
    }

    /**
     * 获取右标题View对象
     */
    public TextView getRightView() {
        return mRightView;
    }

    /**
     * 获取分割线View对象
     */
    public View getLineView() {
        return mLineView;
    }
}