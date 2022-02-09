package com.yway.scomponent.user.mvp.ui.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.TimeUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.ThirdViewUtil;
import com.yway.scomponent.user.R;
import com.yway.scomponent.user.R2;
import com.yway.scomponent.user.mvp.ui.adapter.CommonAdapter;
import com.yway.scomponent.user.mvp.ui.listener.OnViewItemClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import razerdp.basepopup.BasePopupWindow;
import razerdp.util.SimpleAnimationUtils;


/**
  *
  * @Description:      选择工具
  * @Author:         YIWUANYUAN
  * @CreateDate:     2020/4/16 11:25
  * @UpdateUser:     YIWUANYUAN
  * @UpdateDate:     2020/4/16 11:25
  * @UpdateRemark:
  * @Version:        1.0
  */
public final class FiltrateDialog extends BasePopupWindow {

    @BindView(R2.id.recycle_view)
    RecyclerView mRecyclerView;
    private CommonAdapter adapter;
    private OnViewItemClickListener mOnViewItemClickListener;

    public FiltrateDialog(Context context) {
        super(context);
        setOutSideDismiss(false);
        setAlignBackground(true);
        setAlignBackgroundGravity(Gravity.TOP);
        //绑定 ButterKnife
        ThirdViewUtil.bindTarget(this, getContentView());
        ArmsUtils.configRecyclerView(mRecyclerView,new LinearLayoutManager(context));
        adapter = new CommonAdapter(getMothIntegerLs());

        mRecyclerView.setAdapter(adapter);

    }

    /**
      * @description 获取月份列表
      * @date: 2020/7/17 19:01
      * @author: YIWUANYUAN
      * @return
      */
    private List<Integer> getMothIntegerLs(){
        List<Integer> list = new LinkedList<>();
        int month = TimeUtils.getValueByCalendarField(Calendar.MONTH)+1;
        for (int i = month; i >= 1; i--) {
            list.add(i);
        }
        return list;
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.user_dialog_filtrate);
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
        float fromX = 0;
        float fromY = -1f;
        float toX = 0;
        float toY = 0;
        Animation showAnimation = SimpleAnimationUtils.getDefaultScaleAnimation(true);
        Animation dismissAnimation = SimpleAnimationUtils.getDefaultScaleAnimation(false);
        if (fromX != 0 || fromY != 0) {
            showAnimation = createTranslateAnimation(fromX, toX, fromY, toY);
            dismissAnimation = createTranslateAnimation(toX, fromX, toY, fromY);
        }
        setPopupGravity(Gravity.BOTTOM);
        setShowAnimation(showAnimation);
        setDismissAnimation(dismissAnimation);
        super.showPopupWindow(anchorView);
    }

    @Override
    public void showPopupWindow() {
        super.showPopupWindow();
    }
    
    public static final class Builder{
        public FiltrateDialog mDialog;
        private Context mContext;
        public Builder() {
            Context context = AppManager.getAppManager().getCurrentActivity() == null ? AppManager.getAppManager().getTopActivity() : AppManager.getAppManager().getCurrentActivity();
            mDialog = new FiltrateDialog(context);
            mDialog.setPopupFadeEnable(false);
            this.mContext = context;
            mDialog.adapter.setOnItemClickListener((view, viewType, data, position) -> {
                mDialog.dismiss();
                if (mDialog.mOnViewItemClickListener != null) {
                    mDialog.mOnViewItemClickListener.onViewClick(view, (Integer) data);
                }
            });
        }

        /**
         * 显示
         */
        public Builder showPopupWindow(View view) {
            mDialog.setOutSideDismiss(true);
            mDialog.showPopupWindow(view);
            return this;
        }

        public Builder setOnViewItemClickListener(OnViewItemClickListener listener) {
            mDialog.mOnViewItemClickListener = listener;
            return this;
        }

    }
}
