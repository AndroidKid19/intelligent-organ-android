package razerdp.basepopup;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.Map;
import java.util.WeakHashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import razerdp.blur.PopupBlurOption;
import razerdp.library.R;
import razerdp.util.KeyboardUtils;
import razerdp.util.PopupUiUtils;
import razerdp.util.PopupUtils;
import razerdp.util.log.PopupLog;

/**
 * Created by 大灯泡 on 2017/12/12.
 * <p>
 * PopupHelper，这货与Popup强引用哦~
 */
@SuppressWarnings("all")
final class BasePopupHelper implements KeyboardUtils.OnKeyboardChangeListener, BasePopupFlag, ClearMemoryObject {

    BasePopupWindow mPopupWindow;

    WeakHashMap<Object, BasePopupEvent.EventObserver> eventObserverMap;

    enum ShowMode {
        RELATIVE_TO_ANCHOR,
        SCREEN,
        POSITION
    }

    private static final int CONTENT_VIEW_ID = R.id.base_popup_content_root;

    ShowMode mShowMode = ShowMode.SCREEN;

    int contentRootId = CONTENT_VIEW_ID;

    int flag = IDLE;

    static int showCount;

    //animate
    Animation mShowAnimation;
    Animator mShowAnimator;
    Animation mDismissAnimation;
    Animator mDismissAnimator;

    long showDuration;
    long dismissDuration;

    int animationStyleRes;

    //callback
    BasePopupWindow.OnDismissListener mOnDismissListener;
    BasePopupWindow.OnBeforeShowCallback mOnBeforeShowCallback;
    BasePopupWindow.OnPopupWindowShowListener mOnPopupWindowShowListener;

    //option
    BasePopupWindow.GravityMode gravityMode = BasePopupWindow.GravityMode.RELATIVE_TO_ANCHOR;
    int popupGravity = Gravity.NO_GRAVITY;
    int offsetX;
    int offsetY;
    int preMeasureWidth;
    int preMeasureHeight;

    int popupViewWidth = 0;
    int popupViewHeight = 0;
    //锚点view的location
    Rect mAnchorViewBound;

    //模糊option(为空的话则不模糊）
    PopupBlurOption mBlurOption;
    //背景颜色
    Drawable mBackgroundDrawable = new ColorDrawable(BasePopupWindow.DEFAULT_BACKGROUND_COLOR);
    //背景对齐方向
    int alignBackgroundGravity = Gravity.TOP;
    //背景View
    View mBackgroundView;

    EditText mAutoShowInputEdittext;

    KeyboardUtils.OnKeyboardChangeListener mKeyboardStateChangeListener;

    int mSoftInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
    ViewGroup.MarginLayoutParams layoutParams;
    Point mTempOffset = new Point();

    int maxWidth, maxHeight, minWidth, minHeight;

    int keybaordAlignViewId;

    InnerShowInfo mShowInfo;

    GlobalLayoutListener mGlobalLayoutListener;
    LinkedViewLayoutChangeListenerWrapper mLinkedViewLayoutChangeListenerWrapper;

    View mLinkedTarget;

    BasePopupHelper(BasePopupWindow popupWindow) {
        mAnchorViewBound = new Rect();
        this.mPopupWindow = popupWindow;
        this.eventObserverMap = new WeakHashMap<>();
    }

    void observerEvent(Object who, BasePopupEvent.EventObserver observer) {
        eventObserverMap.put(who, observer);
    }

    void removeEventObserver(Object who) {
        eventObserverMap.remove(who);
    }

    void sendEvent(Message msg) {
        if (msg == null) return;
        if (msg.what < 0) return;
        for (Map.Entry<Object, BasePopupEvent.EventObserver> entry : eventObserverMap.entrySet()) {
            if (entry.getValue() != null) {
                entry.getValue().onEvent(msg);
            }
        }
    }

    View inflate(Context context, int layoutId) {
        try {
            FrameLayout tempLayout = new FrameLayout(context);
            View result = LayoutInflater.from(context).inflate(layoutId, tempLayout, false);
            ViewGroup.LayoutParams childParams = result.getLayoutParams();
            if (childParams != null) {
                checkAndSetGravity(childParams);
                if (childParams instanceof ViewGroup.MarginLayoutParams) {
                    layoutParams = new ViewGroup.MarginLayoutParams((ViewGroup.MarginLayoutParams) childParams);
                } else {
                    layoutParams = new ViewGroup.MarginLayoutParams(childParams);
                }

                if (popupViewWidth != 0 && layoutParams.width != popupViewWidth) {
                    layoutParams.width = popupViewWidth;
                }
                if (popupViewHeight != 0 && layoutParams.height != popupViewHeight) {
                    layoutParams.height = popupViewHeight;
                }
                tempLayout = null;
                return result;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    void preMeasurePopupView(View mContentView, int w, int h) {
        if (mContentView != null) {
            int measureWidth = View.MeasureSpec.makeMeasureSpec(Math.max(w, 0), w == ViewGroup.LayoutParams.WRAP_CONTENT ? View.MeasureSpec.UNSPECIFIED : View.MeasureSpec.EXACTLY);
            int measureHeight = View.MeasureSpec.makeMeasureSpec(Math.max(w, h), h == ViewGroup.LayoutParams.WRAP_CONTENT ? View.MeasureSpec.UNSPECIFIED : View.MeasureSpec.EXACTLY);
            mContentView.measure(measureWidth, measureHeight);
            setPreMeasureWidth(mContentView.getMeasuredWidth());
            setPreMeasureHeight(mContentView.getMeasuredHeight());
            mContentView.setFocusableInTouchMode(true);
        }
    }

    void checkAndSetGravity(ViewGroup.LayoutParams p) {
        if (p == null) return;
        if (p instanceof LinearLayout.LayoutParams) {
            setPopupGravity(gravityMode, ((LinearLayout.LayoutParams) p).gravity);
        } else if (p instanceof FrameLayout.LayoutParams) {
            setPopupGravity(gravityMode, ((FrameLayout.LayoutParams) p).gravity);
        }
    }

    //region Animation

    void startShowAnimate(int width, int height) {
        if (getShowAnimation(width, height) == null) {
            getShowAnimator(width, height);
        }
        if (mShowAnimation != null) {
            mShowAnimation.cancel();
            mPopupWindow.mDisplayAnimateView.startAnimation(mShowAnimation);
        } else if (mShowAnimator != null) {
            mShowAnimator.cancel();
            mShowAnimator.start();
        }
    }

    void startDismissAnimate(int width, int height) {
        if (getDismissAnimation(width, height) == null) {
            getDismissAnimator(width, height);
        }
        if (mDismissAnimation != null) {
            mDismissAnimation.cancel();
            mPopupWindow.mDisplayAnimateView.startAnimation(mDismissAnimation);
            if (mOnDismissListener != null) {
                mOnDismissListener.onDismissAnimationStart();
            }
            setFlag(CUSTOM_ON_ANIMATE_DISMISS, true);
        } else if (mDismissAnimator != null) {
            mDismissAnimator.cancel();
            mDismissAnimator.start();
            if (mOnDismissListener != null) {
                mOnDismissListener.onDismissAnimationStart();
            }
            setFlag(CUSTOM_ON_ANIMATE_DISMISS, true);
        }
    }

    Animation getShowAnimation(int width, int height) {
        if (mShowAnimation == null) {
            mShowAnimation = mPopupWindow.onCreateShowAnimation(width, height);
            if (mShowAnimation != null) {
                showDuration = PopupUtils.getAnimationDuration(mShowAnimation, 0);
                setToBlur(mBlurOption);
            }
        }
        return mShowAnimation;
    }

    Animator getShowAnimator(int width, int height) {
        if (mShowAnimator == null) {
            mShowAnimator = mPopupWindow.onCreateShowAnimator(width, height);
            if (mShowAnimator != null) {
                showDuration = PopupUtils.getAnimatorDuration(mShowAnimator, 0);
                setToBlur(mBlurOption);
            }
        }
        return mShowAnimator;
    }

    Animation getDismissAnimation(int width, int height) {
        if (mDismissAnimation == null) {
            mDismissAnimation = mPopupWindow.onCreateDismissAnimation(width, height);
            if (mDismissAnimation != null) {
                dismissDuration = PopupUtils.getAnimationDuration(mDismissAnimation, 0);
                setToBlur(mBlurOption);
            }
        }
        return mDismissAnimation;
    }

    Animator getDismissAnimator(int width, int height) {
        if (mDismissAnimator == null) {
            mDismissAnimator = mPopupWindow.onCreateDismissAnimator(width, height);
            if (mDismissAnimator != null) {
                dismissDuration = PopupUtils.getAnimatorDuration(mDismissAnimator, 0);
                setToBlur(mBlurOption);
            }
        }
        return mDismissAnimator;
    }


    void setToBlur(PopupBlurOption option) {
        this.mBlurOption = option;
        if (option != null) {
            if (option.getBlurInDuration() <= 0) {
                if (showDuration > 0) {
                    option.setBlurInDuration(showDuration);
                }
            }
            if (option.getBlurOutDuration() <= 0) {
                if (dismissDuration > 0) {
                    option.setBlurOutDuration(dismissDuration);
                }
            }
        }
    }


    void setShowAnimation(Animation showAnimation) {
        if (mShowAnimation == showAnimation) return;
        if (mShowAnimation != null) {
            mShowAnimation.cancel();
        }
        mShowAnimation = showAnimation;
        showDuration = PopupUtils.getAnimationDuration(mShowAnimation, 0);
        setToBlur(mBlurOption);
    }

    /**
     * animation优先级更高
     */
    void setShowAnimator(Animator showAnimator) {
        if (mShowAnimation != null || mShowAnimator == showAnimator) return;
        if (mShowAnimator != null) {
            mShowAnimator.cancel();
        }
        mShowAnimator = showAnimator;
        showDuration = PopupUtils.getAnimatorDuration(mShowAnimator, 0);
        setToBlur(mBlurOption);
    }

    void setDismissAnimation(Animation dismissAnimation) {
        if (mDismissAnimation == dismissAnimation) return;
        if (mDismissAnimation != null) {
            mDismissAnimation.cancel();
        }
        mDismissAnimation = dismissAnimation;
        dismissDuration = PopupUtils.getAnimationDuration(mDismissAnimation, 0);
        setToBlur(mBlurOption);
    }

    void setDismissAnimator(Animator dismissAnimator) {
        if (mDismissAnimation != null || mDismissAnimator == dismissAnimator) return;
        if (mDismissAnimator != null) {
            mDismissAnimator.cancel();
        }
        mDismissAnimator = dismissAnimator;
        dismissDuration = PopupUtils.getAnimatorDuration(mDismissAnimator, 0);
        setToBlur(mBlurOption);
    }

    //endregion

    BasePopupHelper setPopupViewWidth(int popupViewWidth) {
        if (popupViewWidth != 0) {
            getLayoutParams().width = popupViewWidth;
        }
        return this;
    }

    BasePopupHelper setPopupViewHeight(int popupViewHeight) {
        if (popupViewHeight != 0) {
            getLayoutParams().height = popupViewHeight;
        }
        return this;
    }

    int getPreMeasureWidth() {
        return preMeasureWidth;
    }

    BasePopupHelper setPreMeasureWidth(int preMeasureWidth) {
        this.preMeasureWidth = preMeasureWidth;
        return this;
    }

    int getPreMeasureHeight() {
        return preMeasureHeight;
    }

    BasePopupHelper setPreMeasureHeight(int preMeasureHeight) {
        this.preMeasureHeight = preMeasureHeight;
        return this;
    }

    boolean isPopupFadeEnable() {
        return (flag & FADE_ENABLE) != 0;
    }

    BasePopupHelper setPopupFadeEnable(boolean fadeEnable) {
        setFlag(FADE_ENABLE, fadeEnable);
        return this;
    }

    boolean isWithAnchor() {
        return (flag & WITH_ANCHOR) != 0;
    }

    boolean isFitsizable() {
        return (flag & FITSIZE) != 0;
    }

    BasePopupHelper withAnchor(boolean showAsDropDown) {
        setFlag(WITH_ANCHOR, showAsDropDown);
        return this;
    }

    BasePopupHelper setShowLocation(int x, int y) {
        mAnchorViewBound.set(x, y, x + 1, y + 1);
        return this;
    }

    BasePopupWindow.GravityMode getGravityMode() {
        return gravityMode;
    }

    int getPopupGravity() {
        return popupGravity;
    }

    BasePopupHelper setPopupGravity(BasePopupWindow.GravityMode mode, int popupGravity) {
        if (popupGravity == this.popupGravity && gravityMode == mode) return this;
        this.gravityMode = mode;
        this.popupGravity = popupGravity;
        return this;
    }

    BasePopupHelper setPopupGravityMode(BasePopupWindow.GravityMode mode) {
        this.gravityMode = mode;
        return this;
    }

    BasePopupHelper setClipChildren(boolean clipChildren) {
        setFlag(CLIP_CHILDREN, clipChildren);
        return this;
    }

    int getOffsetX() {
        return offsetX;
    }

    BasePopupHelper setOffsetX(int offsetX) {
        this.offsetX = offsetX;
        return this;
    }

    int getOffsetY() {
        return offsetY;
    }

    BasePopupHelper setOffsetY(int offsetY) {
        this.offsetY = offsetY;
        return this;
    }

    boolean isAutoShowInputMethod() {
        return (flag & AUTO_INPUT_METHOD) != 0;
    }

    BasePopupHelper autoShowInputMethod(boolean autoShowInputMethod) {
        setFlag(AUTO_INPUT_METHOD, autoShowInputMethod);
        return this;
    }

    BasePopupHelper setSoftInputMode(int inputMethodType) {
        mSoftInputMode = inputMethodType;
        return this;
    }

    boolean isAutoLocatePopup() {
        return (flag & AUTO_LOCATED) != 0;
    }

    BasePopupHelper autoLocatePopup(boolean autoLocatePopup) {
        setFlag(AUTO_LOCATED, autoLocatePopup);
        return this;
    }

    boolean isOutSideDismiss() {
        return (flag & OUT_SIDE_DISMISS) != 0;
    }

    BasePopupHelper dismissOutSideTouch(boolean dismissWhenTouchOutside) {
        setFlag(OUT_SIDE_DISMISS, dismissWhenTouchOutside);
        return this;
    }

    boolean isOutSideTouchable() {
        return (flag & OUT_SIDE_TOUCHABLE) != 0;
    }

    BasePopupHelper outSideTouchable(boolean touchAble) {
        setFlag(OUT_SIDE_TOUCHABLE, touchAble);
        return this;
    }

    BasePopupHelper setPopupAnimationStyle(int animationStyleRes) {
        this.animationStyleRes = animationStyleRes;
        return this;
    }

    BasePopupHelper getAnchorLocation(View v) {
        if (v == null) return this;
        v.getGlobalVisibleRect(mAnchorViewBound);
        return this;
    }

    public Rect getAnchorViewBound() {
        return mAnchorViewBound;
    }

    Point getTempOffset() {
        return mTempOffset;
    }

    Point getTempOffset(int x, int y) {
        mTempOffset.set(x, y);
        return mTempOffset;
    }

    boolean isBackPressEnable() {
        return (flag & BACKPRESS_ENABLE) != 0;
    }

    BasePopupHelper backPressEnable(boolean backPressEnable) {
        setFlag(BACKPRESS_ENABLE, backPressEnable);
        return this;
    }

    boolean isOverlayStatusbar() {
        return (flag & OVERLAY_STATUS_BAR) != 0;
    }

    BasePopupHelper overlayStatusbar(boolean overlay) {
        if (!overlay && PopupUiUtils.isActivityFullScreen(mPopupWindow.getContext())) {
            Log.e(BasePopupWindow.TAG, "setOverlayStatusbar: 全屏Activity下没有StatusBar，此处不能设置为false");
            overlay = true;
        }
        setFlag(OVERLAY_STATUS_BAR, overlay);
        return this;
    }

    PopupBlurOption getBlurOption() {
        return mBlurOption;
    }


    Drawable getPopupBackground() {
        return mBackgroundDrawable;
    }

    BasePopupHelper setPopupBackground(Drawable background) {
        mBackgroundDrawable = background;
        return this;
    }

    boolean isAlignBackground() {
        return (flag & ALIGN_BACKGROUND) != 0;
    }

    BasePopupHelper setAlignBackgound(boolean mAlignBackground) {
        setFlag(ALIGN_BACKGROUND, mAlignBackground);
        if (!mAlignBackground) {
            setAlignBackgroundGravity(Gravity.NO_GRAVITY);
        }
        return this;
    }

    int getAlignBackgroundGravity() {
        if (isAlignBackground() && alignBackgroundGravity == Gravity.NO_GRAVITY) {
            alignBackgroundGravity = Gravity.TOP;
        }
        return alignBackgroundGravity;
    }

    BasePopupHelper setAlignBackgroundGravity(int gravity) {
        this.alignBackgroundGravity = gravity;
        return this;
    }

    BasePopupHelper setForceAdjustKeyboard(boolean adjust) {
        setFlag(KEYBOARD_FORCE_ADJUST, adjust);
        return this;
    }

    boolean isAllowToBlur() {
        return mBlurOption != null && mBlurOption.isAllowToBlur();
    }


    boolean isClipChildren() {
        return (flag & CLIP_CHILDREN) != 0;
    }

    /**
     * non null
     */
    ViewGroup.MarginLayoutParams getLayoutParams() {
        if (layoutParams == null) {
            int w = popupViewWidth == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : popupViewWidth;
            int h = popupViewHeight == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : popupViewHeight;
            layoutParams = new ViewGroup.MarginLayoutParams(w, h);
        }
        return layoutParams;
    }

    int getShowCount() {
        return showCount;
    }


    BasePopupHelper setContentRootId(View contentRoot) {
        if (contentRoot == null) return this;
        if (contentRoot.getId() == View.NO_ID) {
            contentRoot.setId(CONTENT_VIEW_ID);
        }
        this.contentRootId = contentRoot.getId();
        return this;
    }

    int getContentRootId() {
        return contentRootId;
    }

    int getSoftInputMode() {
        return mSoftInputMode;
    }

    View getBackgroundView() {
        return mBackgroundView;
    }

    BasePopupHelper setBackgroundView(View backgroundView) {
        mBackgroundView = backgroundView;
        return this;
    }

    int getMaxWidth() {
        return maxWidth;
    }

    BasePopupHelper setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    int getMaxHeight() {
        return maxHeight;
    }

    BasePopupHelper setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    ShowMode getShowMode() {
        return mShowMode;
    }

    BasePopupHelper setShowMode(ShowMode showMode) {
        mShowMode = showMode;
        return this;
    }

    int getMinWidth() {
        return minWidth;
    }

    BasePopupHelper setMinWidth(int minWidth) {
        this.minWidth = minWidth;
        return this;
    }

    int getMinHeight() {
        return minHeight;
    }

    BasePopupHelper setMinHeight(int minHeight) {
        this.minHeight = minHeight;
        return this;
    }

    BasePopupHelper resize(boolean keep) {
        setFlag(FITSIZE, keep);
        return this;
    }

    boolean isResizeable() {
        return (flag & FITSIZE) != 0;
    }

    public BasePopupHelper linkTo(View anchorView) {
        if (anchorView == null) {
            if (mLinkedViewLayoutChangeListenerWrapper != null) {
                mLinkedViewLayoutChangeListenerWrapper.detach();
                mLinkedViewLayoutChangeListenerWrapper = null;
            }
            mLinkedTarget = null;
            return this;
        }
        mLinkedTarget = anchorView;
        return this;
    }

    //-----------------------------------------controller-----------------------------------------
    void prepare(View v, boolean positionMode) {
        if (mShowInfo == null) {
            mShowInfo = new InnerShowInfo(v, positionMode);
        } else {
            mShowInfo.mAnchorView = v;
            mShowInfo.positionMode = positionMode;
        }
        if (positionMode) {
            setShowMode(BasePopupHelper.ShowMode.POSITION);
        } else {
            setShowMode(v == null ? BasePopupHelper.ShowMode.SCREEN : BasePopupHelper.ShowMode.RELATIVE_TO_ANCHOR);
        }
        getAnchorLocation(v);
        applyToPopupWindow();
    }

    private void applyToPopupWindow() {
        if (mPopupWindow == null || mPopupWindow.mPopupWindowProxy == null) return;
        mPopupWindow.mPopupWindowProxy.setSoftInputMode(isAutoShowInputMethod() ? WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE : WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED);
        mPopupWindow.mPopupWindowProxy.setSoftInputMode(mSoftInputMode);
        mPopupWindow.mPopupWindowProxy.setAnimationStyle(animationStyleRes);
    }

    void onDismiss() {
        if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP ||
                android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1) {
            showCount--;
            showCount = Math.max(0, showCount);
        }
        if (isAutoShowInputMethod()) {
            KeyboardUtils.close(mPopupWindow.getContext());
        }

        if (mGlobalLayoutListener != null) {
            mGlobalLayoutListener.detach();
        }

        if (mLinkedViewLayoutChangeListenerWrapper != null) {
            mLinkedViewLayoutChangeListenerWrapper.detach();
        }
    }

    void setFlag(int flag, boolean added) {
        if (!added) {
            this.flag &= ~flag;
        } else {
            this.flag |= flag;
            if (flag == AUTO_LOCATED) {
                this.flag |= WITH_ANCHOR;
            }
        }
    }

    boolean onDispatchKeyEvent(KeyEvent event) {
        return mPopupWindow.onDispatchKeyEvent(event);
    }

    boolean onInterceptTouchEvent(MotionEvent event) {
        return mPopupWindow.onInterceptTouchEvent(event);
    }

    boolean onTouchEvent(MotionEvent event) {
        return mPopupWindow.onTouchEvent(event);
    }

    boolean onBackPressed() {
        return mPopupWindow.onBackPressed();
    }

    boolean onOutSideTouch() {
        return mPopupWindow.onOutSideTouch();
    }

    void onShow() {
        prepareShow();
        if ((flag & CUSTOM_ON_UPDATE) != 0) return;
        if (mShowAnimation == null || mShowAnimator == null) {
            mPopupWindow.mDisplayAnimateView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    startShowAnimate(mPopupWindow.mDisplayAnimateView.getWidth(), mPopupWindow.mDisplayAnimateView.getHeight());
                    mPopupWindow.mDisplayAnimateView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        } else {
            startShowAnimate(mPopupWindow.mDisplayAnimateView.getWidth(), mPopupWindow.mDisplayAnimateView.getHeight());
        }
        //针对官方的坑（两个popup切换页面后重叠）
        if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP ||
                android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1) {
            showCount++;
        }
    }

    void onAttachToWindow() {
        if (mPopupWindow != null) {
            mPopupWindow.onShowing();
        }
        if (mOnPopupWindowShowListener != null) {
            mOnPopupWindowShowListener.onShowing();
        }
    }

    void onPopupLayout(@NonNull Rect popupRect, @NonNull Rect anchorRect) {
        if (mPopupWindow != null) {
            mPopupWindow.onPopupLayout(popupRect, anchorRect);
        }
    }

    private void prepareShow() {
        if (mGlobalLayoutListener == null) {
            mGlobalLayoutListener = new GlobalLayoutListener();
        }
        mGlobalLayoutListener.attach();

        if (mLinkedTarget != null) {
            if (mLinkedViewLayoutChangeListenerWrapper == null) {
                mLinkedViewLayoutChangeListenerWrapper = new LinkedViewLayoutChangeListenerWrapper(mLinkedTarget);
            }
            if (!mLinkedViewLayoutChangeListenerWrapper.isAdded) {
                mLinkedViewLayoutChangeListenerWrapper.attach();
            }
        }
    }


    void dismiss(boolean animateDismiss) {
        if (mPopupWindow == null || mOnDismissListener != null && !mOnDismissListener.onBeforeDismiss()) {
            return;
        }
        if (mPopupWindow.mDisplayAnimateView == null || animateDismiss && (flag & CUSTOM_ON_ANIMATE_DISMISS) != 0) {
            return;
        }
        Message msg = BasePopupEvent.getMessage(BasePopupEvent.EVENT_DISMISS);
        if (animateDismiss) {
            startDismissAnimate(mPopupWindow.mDisplayAnimateView.getWidth(), mPopupWindow.mDisplayAnimateView.getHeight());
            msg.arg1 = 1;
            mPopupWindow.mDisplayAnimateView.removeCallbacks(dismissAnimationDelayRunnable);
            mPopupWindow.mDisplayAnimateView.postDelayed(dismissAnimationDelayRunnable, Math.max(dismissDuration, 0));
        } else {
            msg.arg1 = 0;
            mPopupWindow.superDismiss();
        }
        sendEvent(msg);
    }

    private Runnable dismissAnimationDelayRunnable = new Runnable() {
        @Override
        public void run() {
            flag &= ~CUSTOM_ON_ANIMATE_DISMISS;
            if (mPopupWindow != null) {
                //popup可能已经释放引用了
                mPopupWindow.superDismiss();
            }
        }
    };

    void forceDismiss() {
        if (mDismissAnimation != null) mDismissAnimation.cancel();
        if (mDismissAnimator != null) mDismissAnimator.cancel();
        if (mPopupWindow != null) {
            KeyboardUtils.close(mPopupWindow.getContext());
        }
        if (dismissAnimationDelayRunnable != null) {
            dismissAnimationDelayRunnable.run();
        }
    }

    void onAutoLocationChange(int oldGravity, int newGravity) {
        PopupLog.i("onAutoLocationChange", oldGravity, newGravity);
    }

    void onAnchorTop() {
    }

    void onAnchorBottom() {
    }

    @Override
    public void onKeyboardChange(Rect keyboardBounds, boolean isVisible) {
        if (mKeyboardStateChangeListener != null) {
            mKeyboardStateChangeListener.onKeyboardChange(keyboardBounds, isVisible);
        }
    }

    void update(View v, boolean positionMode) {
        if (!mPopupWindow.isShowing() || mPopupWindow.mContentView == null) return;
        prepare(v, positionMode);
        mPopupWindow.mPopupWindowProxy.update();
    }

    void onUpdate() {
        if (mShowInfo != null) {
            prepare(mShowInfo.mAnchorView == null ? null : mShowInfo.mAnchorView, mShowInfo.positionMode);
        }
    }

    void dispatchOutSideEvent(MotionEvent event) {
        if (mPopupWindow != null) {
            mPopupWindow.dispatchOutSideEvent(event);
        }
    }

    static class InnerShowInfo {
        View mAnchorView;
        boolean positionMode;

        InnerShowInfo(View mAnchorView, boolean positionMode) {
            this.mAnchorView = mAnchorView;
            this.positionMode = positionMode;
        }
    }

    class GlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        Rect rect = new Rect();
        Rect keyboardRect = new Rect();
        boolean lastVisible;
        int lastHeight;
        boolean isAdded;

        void attach() {
            if (isAdded) return;
            try {
                PopupUiUtils.safeAddGlobalLayoutListener(mPopupWindow.getContext().getWindow().getDecorView(), this);
                isAdded = true;
            } catch (Exception e) {
                PopupLog.e(e);
            }
        }

        void detach() {
            try {
                isAdded = false;
                rect.setEmpty();
                keyboardRect.setEmpty();
                lastVisible = false;
                lastHeight = 0;
                PopupUiUtils.safeRemoveGlobalLayoutListener(mPopupWindow.getContext().getWindow().getDecorView(), this);
            } catch (Exception e) {
                PopupLog.e(e);
            }
        }

        @Override
        public void onGlobalLayout() {
            try {
                View decor = mPopupWindow.getContext().getWindow().getDecorView();
                View content = decor.findViewById(android.R.id.content);
                decor.getWindowVisibleDisplayFrame(rect);
                int screenHeight = content == null ? decor.getHeight() : content.getHeight();
                keyboardRect.set(rect.left, rect.bottom, rect.right, screenHeight);
                boolean isVisible = keyboardRect.height() > (screenHeight >> 2) && KeyboardUtils.isOpen();
                if (isVisible == lastVisible && keyboardRect.height() == lastHeight) return;
                lastVisible = isVisible;
                lastHeight = keyboardRect.height();
                onKeyboardChange(keyboardRect, isVisible);
            } catch (Exception e) {
                PopupLog.e(e);
            }
        }
    }

    class LinkedViewLayoutChangeListenerWrapper implements ViewTreeObserver.OnPreDrawListener {

        private View mTarget;
        private boolean isAdded;
        private float lastX, lastY;
        private int lastWidth, lastHeight, lastVisible;
        private boolean lastShowState, hasChange;
        Rect lastLocationRect = new Rect();
        Rect newLocationRect = new Rect();

        public LinkedViewLayoutChangeListenerWrapper(View target) {
            mTarget = target;
        }

        void attach() {
            if (mTarget == null || isAdded) return;
            mTarget.getGlobalVisibleRect(lastLocationRect);
            refreshViewParams();
            mTarget.getViewTreeObserver().addOnPreDrawListener(this);
            isAdded = true;
        }

        void detach() {
            if (mTarget == null || !isAdded) return;
            try {
                mTarget.getViewTreeObserver().removeOnPreDrawListener(this);
            } catch (Exception e) {
            }
            isAdded = false;
        }

        void refreshViewParams() {
            if (mTarget == null) return;

            //之所以不直接用getGlobalVisibleRect，是因为getGlobalVisibleRect需要不断的找到parent然后获取位置，因此先比较自身属性，然后进行二次验证
            float curX = mTarget.getX();
            float curY = mTarget.getY();
            int curWidth = mTarget.getWidth();
            int curHeight = mTarget.getHeight();
            int curVisible = mTarget.getVisibility();
            boolean isShow = mTarget.isShown();

            hasChange = (curX != lastX ||
                    curY != lastY ||
                    curWidth != lastWidth ||
                    curHeight != lastHeight ||
                    curVisible != lastVisible) && isAdded;
            if (!hasChange) {
                //不排除是recyclerview中那样子的情况，因此这里进行二次验证，获取view在屏幕中的位置
                mTarget.getGlobalVisibleRect(newLocationRect);
                if (!newLocationRect.equals(lastLocationRect)) {
                    lastLocationRect.set(newLocationRect);
                    //处理可能的在recyclerview回收的事情
                    if (!handleShowChange(mTarget, lastShowState, isShow)) {
                        hasChange = true;
                    }
                }
            }

            lastX = curX;
            lastY = curY;
            lastWidth = curWidth;
            lastHeight = curHeight;
            lastVisible = curVisible;
            lastShowState = isShow;
        }

        private boolean handleShowChange(View target, boolean lastShowState, boolean isShow) {
            if (lastShowState && !isShow) {
                if (mPopupWindow.isShowing()) {
                    dismiss(false);
                    return true;
                }
            } else if (!lastShowState && isShow) {
                if (!mPopupWindow.isShowing()) {
                    mPopupWindow.tryToShowPopup(target, false);
                    return true;
                }
            }
            return false;
        }


        @Override
        public boolean onPreDraw() {
            if (mTarget == null) return true;
            refreshViewParams();
            if (hasChange) {
                update(mTarget, false);
            }
            return true;
        }
    }

    @Nullable
    static Activity findActivity(Object parent) {
        return findActivity(parent, true);
    }

    @Nullable
    static Activity findActivity(Object parent, boolean returnTopIfNull) {
        Activity act = null;
        if (parent instanceof Context) {
            act = PopupUtils.getActivity((Context) parent);
        } else if (parent instanceof Fragment) {
            act = ((Fragment) parent).getActivity();
        } else if (parent instanceof Dialog) {
            act = PopupUtils.getActivity(((Dialog) parent).getContext());
        }
        if (act == null && returnTopIfNull) {
            act = BasePopupSDK.getInstance().getTopActivity();
        }
        return act;
    }

    @Nullable
    static View findDecorView(Object parent) {
        View decorView = null;
        Window window = null;
        if (parent instanceof Dialog) {
            window = ((Dialog) parent).getWindow();
        } else if (parent instanceof DialogFragment) {
            if (((DialogFragment) parent).getDialog() == null) {
                decorView = ((DialogFragment) parent).getView();
            } else {
                window = ((DialogFragment) parent).getDialog().getWindow();
            }
        } else if (parent instanceof Fragment) {
            decorView = ((Fragment) parent).getView();
        } else if (parent instanceof Context) {
            Activity act = PopupUtils.getActivity((Context) parent);
            decorView = act == null ? null : act.findViewById(android.R.id.content);
        }

        if (decorView != null) {
            return decorView;
        } else {
            return window == null ? null : window.getDecorView();
        }

    }

    @Override
    public void clear(boolean destroy) {
        if (mPopupWindow != null && mPopupWindow.mDisplayAnimateView != null) {
            //神奇的是，这个方式有可能失效，runnable根本就没有被remove掉
            mPopupWindow.mDisplayAnimateView.removeCallbacks(dismissAnimationDelayRunnable);
        }
        if (eventObserverMap != null) {
            eventObserverMap.clear();
        }
        if (mShowAnimation != null) {
            mShowAnimation.cancel();
            mShowAnimation.setAnimationListener(null);
        }
        if (mDismissAnimation != null) {
            mDismissAnimation.cancel();
            mDismissAnimation.setAnimationListener(null);
        }
        if (mShowAnimator != null) {
            mShowAnimator.cancel();
            mShowAnimator.removeAllListeners();
        }
        if (mDismissAnimator != null) {
            mDismissAnimator.cancel();
            mDismissAnimator.removeAllListeners();
        }
        if (mBlurOption != null) {
            mBlurOption.clear();
        }
        if (mShowInfo != null) {
            mShowInfo.mAnchorView = null;
        }
        if (mGlobalLayoutListener != null) {
            mGlobalLayoutListener.detach();
        }

        if (mLinkedViewLayoutChangeListenerWrapper != null) {
            mLinkedViewLayoutChangeListenerWrapper.detach();
        }

        dismissAnimationDelayRunnable = null;
        mShowAnimation = null;
        mDismissAnimation = null;
        mShowAnimator = null;
        mDismissAnimator = null;
        eventObserverMap = null;
        mPopupWindow = null;
        mOnPopupWindowShowListener = null;
        mOnDismissListener = null;
        mOnBeforeShowCallback = null;
        mBlurOption = null;
        mBackgroundDrawable = null;
        mBackgroundView = null;
        mAutoShowInputEdittext = null;
        mKeyboardStateChangeListener = null;
        mShowInfo = null;
        mLinkedViewLayoutChangeListenerWrapper = null;
        mLinkedTarget = null;
        mGlobalLayoutListener = null;
    }
}
