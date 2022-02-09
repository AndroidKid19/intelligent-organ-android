package com.yway.scomponent.commonres.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatTextView;

import com.blankj.utilcode.util.StringUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.yway.scomponent.commonres.R;
import com.yway.scomponent.commonres.dialog.listener.OnViewItemClickListener;

import razerdp.basepopup.BasePopupWindow;

import static com.blankj.utilcode.util.ActivityUtils.getTopActivity;
import static com.blankj.utilcode.util.StringUtils.getString;


/**
 * @Description: 消息提示框
 * @Author: Yuanweiwei
 * @Version: 1.0
 */
public final class MessageDialog extends BasePopupWindow {

    private OnViewItemClickListener mViewItemClickListener;
    private TextView mTitleView;
    private TextView mMessageView;
    private AppCompatTextView mCancelView;
    private AppCompatTextView mConfirmView;
    private View mLayoutBtn;

    public MessageDialog(Context context) {
        super(context);
        setOutSideDismiss(false);
        setBackPressEnable(false);
        bindEvent();
    }

    private void bindEvent() {
        mTitleView = findViewById(R.id.tv_message_title);
        mMessageView = findViewById(R.id.tv_message_message);
        mCancelView  = findViewById(R.id.tv_message_cancel);
        mConfirmView  = findViewById(R.id.tv_message_confirm);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.public_dialog_message);
    }
    @Override
    protected Animation onCreateShowAnimation() {
        return getDefaultScaleAnimation();
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getDefaultScaleAnimation(false);
    }

    @Override
    public void showPopupWindow() {
        setShowAnimation(getDefaultScaleAnimation());
        setDismissAnimation(getDefaultScaleAnimation(false));
        super.showPopupWindow();
    }

    @Override
    public void showPopupWindow(View anchorView) {
        initAnimate();
        super.showPopupWindow(anchorView);
    }

    private void initAnimate() {
        int gravity = getPopupGravity();
        float in_fromX = 0;
        float in_toX = 0;
        float in_fromY = 0;
        float in_toY = 0;
        float exit_fromX = 0;
        float exit_toX = 0;
        float exit_fromY = 0;
        float exit_toY = 0;
        switch (gravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
            case Gravity.LEFT:
            case Gravity.START:
                in_fromX = 1f;
                exit_toX = 1f;
                break;
            case Gravity.RIGHT:
            case Gravity.END:
                in_fromX = -1f;
                exit_toX = -1f;
                break;
            case Gravity.CENTER_HORIZONTAL:
                break;
            default:
                break;
        }
        switch (gravity & Gravity.VERTICAL_GRAVITY_MASK) {
            case Gravity.TOP:
                in_fromY = 1f;
                exit_toY = 1f;
                break;
            case Gravity.BOTTOM:
                in_fromY = -1f;
                exit_toY = -1f;
                break;
            case Gravity.CENTER_VERTICAL:
                break;
            default:
                break;
        }
        setShowAnimation(createTranslateAnimate(in_fromX, in_toX, in_fromY, in_toY));
        setDismissAnimation(createTranslateAnimate(exit_fromX, exit_toX, exit_fromY, exit_toY));
    }
    public Animation createTranslateAnimate(float fromX, float toX, float fromY, float toY) {
        Animation result = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
                fromX,
                Animation.RELATIVE_TO_PARENT,
                toX,
                Animation.RELATIVE_TO_PARENT,
                fromY,
                Animation.RELATIVE_TO_PARENT,
                toY);
        result.setDuration(300);
        return result;
    }


    public static final class Builder implements View.OnClickListener {

        public MessageDialog mMessageDialog;
        private boolean mAutoDismiss = true;

        public Builder() {
            Activity activity = AppManager.getAppManager().getCurrentActivity() == null ? getTopActivity() : AppManager.getAppManager().getCurrentActivity();
            mMessageDialog = new MessageDialog(activity);
            mMessageDialog.setPopupFadeEnable(false);
            mMessageDialog.mCancelView.setOnClickListener(this);
            mMessageDialog.mConfirmView.setOnClickListener(this);

        }

        public Builder setTitle(@StringRes int id) {
            return setTitle(getString(id));
        }
        public Builder setTitle(CharSequence text) {
            if(!StringUtils.isEmpty(text)){
                mMessageDialog.mTitleView.setText(text);
            }
            return this;
        }

        public Builder setTitleColor(int color) {
            mMessageDialog.mTitleView.setTextColor(color);
            return this;
        }

        public Builder setMessage(@StringRes int id) {
            return setMessage(getString(id));
        }
        public Builder setMessage(CharSequence text) {
            mMessageDialog.mMessageView.setText(text);
            return this;
        }

        public Builder setMessageColor(int color) {
            mMessageDialog.mMessageView.setTextColor(color);
            return this;
        }

        public Builder setCancel(@StringRes int id) {
            return setCancel(getString(id));
        }
        public Builder setCancel(CharSequence text) {
            mMessageDialog.mCancelView.setText(text);
            mMessageDialog.mCancelView.setVisibility((text == null || "".equals(text.toString())) ? View.GONE : View.VISIBLE);
            return this;
        }

        public Builder setConfirm(@StringRes int id) {
            return setConfirm(getString(id));
        }
        public Builder setConfirm(CharSequence text) {
            mMessageDialog.mConfirmView.setText(text);
            mMessageDialog.mConfirmView.setVisibility((text == null || "".equals(text.toString())) ? View.GONE : View.VISIBLE);
            if(text == null || "".equals(text.toString())){
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0, 0, ArmsUtils.dip2px(mMessageDialog.mLayoutBtn.getContext(),8), 0);
                mMessageDialog.mLayoutBtn.setLayoutParams(lp);
            }
            return this;
        }


        public Builder setAutoDismiss(boolean dismiss) {
            mAutoDismiss = dismiss;
            return this;
        }

        public Builder setOnViewItemClickListener(OnViewItemClickListener listener) {
            mMessageDialog.mViewItemClickListener = listener;
            return this;
        }

        /**
         * 显示
         */
        public Builder showPopupWindow() {
            mMessageDialog.showPopupWindow();
            return this;
        }

        /**
         * {@link View.OnClickListener}
         */
        @Override
        public void onClick(View v) {
            if (mAutoDismiss) {
                mMessageDialog.dismiss();
            }
            if (mMessageDialog.mViewItemClickListener != null) {
                if (v == mMessageDialog.mConfirmView) {
                    mMessageDialog.mViewItemClickListener.onViewClick(v);
                }
            }
        }

    }
}
