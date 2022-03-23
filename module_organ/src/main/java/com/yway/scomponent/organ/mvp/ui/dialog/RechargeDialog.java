package com.yway.scomponent.organ.mvp.ui.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.RadioGroup;

import com.blankj.utilcode.util.StringUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ThirdViewUtil;
import com.yway.scomponent.commonres.dialog.IToast;
import com.yway.scomponent.commonres.view.layout.ClearEditText;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.R2;

import butterknife.BindView;
import butterknife.OnClick;
import razerdp.basepopup.BasePopupWindow;
import razerdp.util.SimpleAnimationUtils;


/**
 * 充值金额选择
 */
public final class RechargeDialog extends BasePopupWindow {

    private OnRechargeListener mOnRechargeListener;
    @BindView(R2.id.tv_cancel)
    View mCancelView;
    @BindView(R2.id.rg_recharge_amount)
    RadioGroup mRgRechargeAmount;
    @BindView(R2.id.btn_opt_adopt)
    View mBtnOptView;
    @BindView(R2.id.et_other_amount)
    ClearEditText mEtOhterAmount;
    private double amount = 0;

    public RechargeDialog(Context context) {
        super(context);
        //绑定 ButterKnife
        ThirdViewUtil.bindTarget(this, getContentView());
        mRgRechargeAmount.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    /**
     * 状态监听回调
     */
    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = (group, checkedId) -> {
        if (checkedId == R.id.rb_1) {//50
            amount = 50;
            mEtOhterAmount.getText().clear();
        } else if (checkedId == R.id.rb_2) {//100
            amount = 100;
            mEtOhterAmount.getText().clear();
        } else if (checkedId == R.id.rb_3) {//150
            amount = 150;
            mEtOhterAmount.getText().clear();
        } else if (checkedId == R.id.rb_4) {//200
            amount = 200;
            mEtOhterAmount.getText().clear();
        } else if (checkedId == R.id.rb_0) {//200
            amount = 0.01;
            mEtOhterAmount.getText().clear();
        }
    };

    @OnClick(R2.id.btn_opt_adopt)
    void onBtnOptView(View view){
        String otherAmount = mEtOhterAmount.getText().toString();
        if (!StringUtils.isEmpty(otherAmount)){
            amount = Integer.parseInt(otherAmount);
        }
        if (amount <= 0){
            IToast.showWarnShort("请选择充值金额");
            return;
        }
        mOnRechargeListener.onRecharge(amount);
        this.dismiss();
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.organ_dialog_recharge);
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return getTranslateVerticalAnimation(1f, 0, 500);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected Animation onCreateDismissAnimation() {
        return getTranslateVerticalAnimation(0, 1f, 500);
    }

    public int gravity = Gravity.BOTTOM;
    public boolean withAnchor;
    public boolean blur;

    @Override
    public void showPopupWindow() {
        Animation showAnimation = SimpleAnimationUtils.getDefaultScaleAnimation(true);
        Animation dismissAnimation = SimpleAnimationUtils.getDefaultScaleAnimation(false);
        float fromX = 0;
        float fromY = 0;
        float toX = 0;
        float toY = 0;
        switch (gravity & Gravity.VERTICAL_GRAVITY_MASK) {
            case Gravity.BOTTOM:
                fromY = withAnchor ? -1f : 1f;
                break;
        }
        if (fromX != 0 || fromY != 0) {
            showAnimation = createTranslateAnimation(fromX, toX, fromY, toY);
            dismissAnimation = createTranslateAnimation(toX, fromX, toY, fromY);
        }
        setBlurBackgroundEnable(blur);
        setPopupGravity(gravity);
        setShowAnimation(showAnimation);
        setDismissAnimation(dismissAnimation);
        setPopupWindowFullScreen(true);
        super.showPopupWindow();
    }


    private Animation createTranslateAnimation(float fromX, float toX, float fromY, float toY) {
        Animation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                fromX,
                Animation.RELATIVE_TO_SELF,
                toX,
                Animation.RELATIVE_TO_SELF,
                fromY,
                Animation.RELATIVE_TO_SELF,
                toY);
        animation.setDuration(300);
        animation.setInterpolator(new DecelerateInterpolator());
        return animation;
    }

    @Override
    public void showPopupWindow(View anchorView) {
        super.showPopupWindow(anchorView);
    }


    public static final class Builder implements View.OnClickListener {

        private RechargeDialog mRechargeDialog;
        private boolean mAutoDismiss = true;

        public Builder() {
            mRechargeDialog = new RechargeDialog(AppManager.getAppManager().getCurrentActivity() == null ? AppManager.getAppManager().getTopActivity() : AppManager.getAppManager().getCurrentActivity());
            mRechargeDialog.setPopupFadeEnable(false);
            mRechargeDialog.mCancelView.setOnClickListener(this);

        }


        public Builder setAutoDismiss(boolean dismiss) {
            mAutoDismiss = dismiss;
            return this;
        }

        public Builder setOnRechargeListener(OnRechargeListener listener) {
            mRechargeDialog.mOnRechargeListener = listener;
            return this;
        }

        /**
         * 显示
         */
        public Builder showPopupWindow() {
            mRechargeDialog.showPopupWindow();
            return this;
        }

        /**
         * {@link View.OnClickListener}
         */
        @Override
        public void onClick(View v) {
            if (mAutoDismiss) {
                mRechargeDialog.dismiss();
            }
            if (mRechargeDialog.mOnRechargeListener != null) {
                if (v == mRechargeDialog.mCancelView) {
                    mRechargeDialog.dismiss();
                }
            }
        }

    }

    public interface OnRechargeListener {
        /**
         * 充值回调
         */
        void onRecharge(double amount);
    }
}
