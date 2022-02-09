package com.yway.scomponent.commonres.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

import com.jess.arms.integration.AppManager;
import com.yway.scomponent.commonres.R;

import java.lang.reflect.Field;

import razerdp.basepopup.BasePopupWindow;
import razerdp.util.SimpleAnimationUtils;


/**
 * @ProjectName: Android-Project
 * @Package: com.fatalsignal.outsourcing.commonres.dialog
 * @ClassName: UpgradeDialog
 * @Description: 相册选择
 * @Author: Yuanweiwei
 * @CreateDate: 2019/9/26 13:46
 * @UpdateUser:
 * @UpdateDate: 2019/9/26 13:46
 * @UpdateRemark:
 * @Version: 1.0
 */
public final class PhotoDialog extends BasePopupWindow {

    private OnListener mListener;

    private View mCancelView;
    private View mViewCamera;
    private View mViewAlbum;


    public PhotoDialog(Context context) {
        super(context);
        bindEvent();
    }

    private void bindEvent() {
        mCancelView  = findViewById(R.id.tv_cancel);
        mViewCamera = findViewById(R.id.tv_camera);
        mViewAlbum  = findViewById(R.id.tv_album);


    }
    public Field getDeclaredField(String name)throws NoSuchFieldException,SecurityException{

        return  null;
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.public_phone_message);
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

        private PhotoDialog mPhotoDialog;
        private boolean mAutoDismiss = true;

        public Builder() {
            mPhotoDialog = new PhotoDialog(AppManager.getAppManager().getCurrentActivity() == null ? AppManager.getAppManager().getTopActivity() : AppManager.getAppManager().getCurrentActivity());
            mPhotoDialog.setPopupFadeEnable(false);
            mPhotoDialog.mCancelView.setOnClickListener(this);
            mPhotoDialog.mViewAlbum.setOnClickListener(this);
            mPhotoDialog.mViewCamera.setOnClickListener(this);

        }


        public Builder setAutoDismiss(boolean dismiss) {
            mAutoDismiss = dismiss;
            return this;
        }

        public Builder setListener(OnListener listener) {
            mPhotoDialog.mListener = listener;
            return this;
        }

        /**
         * 显示
         */
        public Builder showPopupWindow() {
            mPhotoDialog.showPopupWindow();
            return this;
        }

        /**
         * {@link View.OnClickListener}
         */
        @Override
        public void onClick(View v) {
            if (mAutoDismiss) {
                mPhotoDialog.dismiss();
            }
            if (mPhotoDialog.mListener != null) {
                if (v == mPhotoDialog.mViewCamera) {
                    mPhotoDialog.mListener.onCamera(mPhotoDialog);
                    mPhotoDialog.dismiss();
                } else if (v == mPhotoDialog.mCancelView) {
                    mPhotoDialog.dismiss();
                } else if (v == mPhotoDialog.mViewAlbum) {
                    mPhotoDialog.mListener.onAlbum(mPhotoDialog);
                    mPhotoDialog.dismiss();
                }
            }
        }

    }
    public interface OnListener {
        /**
         * 点击相册时回调
         */
        void onCamera(PhotoDialog dialog);
        /**
         * 点击相机回调
         */
        void onAlbum(PhotoDialog dialog);
    }
}
