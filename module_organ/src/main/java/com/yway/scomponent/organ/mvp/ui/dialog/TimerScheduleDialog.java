package com.yway.scomponent.organ.mvp.ui.dialog;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ThirdViewUtil;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.mvp.ui.listener.OnTimepickerClickListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import razerdp.basepopup.BasePopupWindow;

/**
 * @Description: 时间选择
 */
public final class TimerScheduleDialog extends BasePopupWindow {

    @BindView(R2.id.tvTitle)
    TextView mTvTitle;
    @BindView(R2.id.btnSubmit)
    Button mBtnSubmit;
    @BindView(R2.id.tp_start_time)
    TimePicker tpLeft;
    @BindView(R2.id.tp_end_time)
    TimePicker tpRight;

    /**
     * 间隔15的数组，用来表示可设置的分钟值
     */
    private String[] minute = new String[]{"00", "15", "30", "45"};

    private OnTimepickerClickListener mOnTimepickerClickListener;

    public void setOnTimepickerClickListener(OnTimepickerClickListener onTimepickerClickListener) {
        mOnTimepickerClickListener = onTimepickerClickListener;
    }

    public TimerScheduleDialog(Context context) {
        super(context);
        //绑定 ButterKnife
        ThirdViewUtil.bindTarget(this, getContentView());
        //设置弹层外不可点击
        setOutSideDismiss(false);
        setAlignBackground(true);
        setAlignBackgroundGravity(Gravity.TOP);
        mTvTitle.setText("会议时间");
        mBtnSubmit.setVisibility(View.VISIBLE);
        initTimePicker(tpLeft);
        initTimePicker(tpRight);
    }

    @OnClick(R2.id.btnSubmit)
    void onSubmitClick(View view){
        String stratTime = tpLeft.getCurrentHour() +":"+ minute[tpLeft.getCurrentMinute()];
        String endTime = tpRight.getCurrentHour() +":"+ minute[tpRight.getCurrentMinute()];

        if (mOnTimepickerClickListener !=null){
            mOnTimepickerClickListener.onItemClick(stratTime,endTime);
        }
        this.dismiss();
    }

    /**
     * 初始化 时间选择器为24小时制
     * 设置 分钟为15min 间隔
     *
     * @param picker
     */
    private void initTimePicker(TimePicker picker) {
        picker.setIs24HourView(true);

        setNumberPickerTextSize(picker);
        setTimePickerTextClolr(picker);
    }

    /**
     * 查找timePicker里面的android.widget.NumberPicker组件
     * 并对其进行时间间隔设置
     *
     * @param viewGroup TimePicker timePicker
     */
    private void setNumberPickerTextSize(ViewGroup viewGroup) {
        List<NumberPicker> npList = findNumberPicker(viewGroup);

//        String[] arrHours = initHourRange();

        if (null != npList) {
            for (NumberPicker mMinuteSpinner : npList) {
                hideSpinner(mMinuteSpinner);

                if (mMinuteSpinner.toString().contains("id/minute")) {//对分钟进行间隔设置
                    mMinuteSpinner.setMinValue(0);
                    mMinuteSpinner.setMaxValue(minute.length - 1);
                    mMinuteSpinner.setDisplayedValues(minute);  //这里的minute是一个String数组，就是要显示的分钟值
                }

//                if (mMinuteSpinner.toString().contains("id/hour")) {//对小时进行间隔设置
//                    mMinuteSpinner.setMinValue(0);
//                    mMinuteSpinner.setMaxValue(arrHours.length - 1);
//                    mMinuteSpinner.setDisplayedValues(arrHours);  //这里的hours是一个String数组，就是要显示的小时值
//                }
            }
        }
    }


    /**
     * 初始化时间选择器颜色
     */
    private void setTimePickerTextClolr(TimePicker picker) {
        Resources system = Resources.getSystem();
        int hourId = system.getIdentifier("hour", "id", "android");//小时
        int minuteId = system.getIdentifier("minute", "id", "android");//分钟
        int dividerId = system.getIdentifier("divider", "id", "android");//冒号

        NumberPicker hourPicker = (NumberPicker) picker.findViewById(hourId);
        NumberPicker minutePicker = (NumberPicker) picker.findViewById(minuteId);
        TextView divider = (TextView) picker.findViewById(dividerId);
        divider.setText(":");//这个是时与分之间的分隔“：”

        final int color = getContext().getResources().getColor(android.R.color.black);   //要设置的颜色

        setTimePickerLoopColor(hourPicker, color);
        setTimePickerLoopColor(minutePicker, color);
        divider.setTextColor(color);
    }

    private void setTimePickerLoopColor(NumberPicker number_picker, int color) {
        final int count = number_picker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = number_picker.getChildAt(i);
            try {
                Field wheelpaint_field = number_picker.getClass().getDeclaredField("mSelectorWheelPaint");
                wheelpaint_field.setAccessible(true);

                ((Paint) wheelpaint_field.get(number_picker)).setColor(color);
                ((EditText) child).setTextColor(color);
                number_picker.invalidate();
            } catch (NoSuchFieldException e) {
            } catch (IllegalAccessException e) {
            } catch (IllegalArgumentException e) {
            }
        }
    }

    /**
     * 得到timePicker里面的android.widget.NumberPicker组件
     * （有两个android.widget.NumberPicker组件--hour，minute）
     *
     * @param viewGroup
     * @return
     */
    private List<NumberPicker> findNumberPicker(ViewGroup viewGroup) {
        List<NumberPicker> npList = new ArrayList<>();
        View child;

        if (null != viewGroup) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                child = viewGroup.getChildAt(i);
                if (child instanceof NumberPicker) {
                    npList.add((NumberPicker) child);
                } else if (child instanceof LinearLayout) {
                    List<NumberPicker> result = findNumberPicker((ViewGroup) child);
                    if (result.size() > 0) {
                        return result;
                    }
                }
            }
        }

        return npList;
    }

    /**
     * 利用反射修改NumberPicker中间分割线的颜色
     *
     * @param numberPicker
     */
    private void hideSpinner(NumberPicker numberPicker) {
        try {
            //分割线颜色
            Field dividerField = numberPicker.getClass().getDeclaredField("mSelectionDivider");
            dividerField.setAccessible(true);
            ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(getContext(), R.color.public_colorLine));
            dividerField.set(numberPicker, colorDrawable);

            //分割线高度
            Field heightField = numberPicker.getClass().getDeclaredField(
                    "mSelectionDividerHeight");
            heightField.setAccessible(true);
            heightField.set(numberPicker, 2);//px

            //重绘
            numberPicker.invalidate();

        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
        }
    }

    @OnClick(R2.id.btnCancel)
    void onBtnCancel(View view) {
        this.dismiss();
    }


    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.organ_dialog_comlib_schedule_time);
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return getTranslateVerticalAnimation(1f, 0, 500);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getTranslateVerticalAnimation(0, 1f, 500);
    }

    @Override
    public void showPopupWindow() {
        super.showPopupWindow();
    }

    public static final class Builder implements View.OnClickListener {
        public TimerScheduleDialog mDialog;

        public Builder() {
            Context context = AppManager.getAppManager().getCurrentActivity() == null ? AppManager.getAppManager().getTopActivity() : AppManager.getAppManager().getCurrentActivity();
            mDialog = new TimerScheduleDialog(context);
            mDialog.setPopupFadeEnable(false);
        }

        /**
         * 显示
         */
        public Builder showPopupWindow() {
            mDialog.setOutSideDismiss(true);
            mDialog.showPopupWindow();
            return this;
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        public Builder initHour(int hour, int minute) {
            mDialog.tpLeft.setHour(hour);  //设置当前小时
            if (minute == 30){
                minute = 2;
            }
            mDialog.tpLeft.setMinute(minute); //设置当前分（0-59）
            return this;
        }

        public Builder setOnTimepickerClickListener(OnTimepickerClickListener listener) {
            mDialog.mOnTimepickerClickListener = listener;
            return this;
        }

        @Override
        public void onClick(View v) {
            mDialog.dismiss();
        }
    }

}
