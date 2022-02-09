package com.yway.scomponent.commonres.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.jess.arms.integration.AppManager;
import com.yway.scomponent.commonres.R;

/**
 * @ClassName: IToast
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class IToast {
    private static final String NULL = "null";
    private static Toast mToast;

    /**
     * 显示的类型
     */
    public enum Type {
        // 完成，错误，警告
        FINISH, ERROR, WARN
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param text The text.
     */
    public static void showShort(final CharSequence text, Type type) {
        show(text == null ? NULL : text, Toast.LENGTH_SHORT, type);
    }


    /**
     * Show the toast for a long period of time.
     *
     * @param text The text.
     */
    public static void showLong(final CharSequence text, Type type) {
        show(text, Toast.LENGTH_LONG, type);
    }


    /**
     * WARN
     * Show the toast for a short period of time.WARN
     *
     * @param text The text.
     */
    public static void showWarnShort(final CharSequence text) {
        show(text == null ? NULL : text, Toast.LENGTH_SHORT, Type.WARN);
    }

    /**
     * FINISH
     * Show the toast for a short period of time.WARN
     *
     * @param text The text.
     */
    public static void showFinishShort(final CharSequence text) {
        show(text == null ? NULL : text, Toast.LENGTH_SHORT, Type.FINISH);
    }

    /**
     * ERROR
     * Show the toast for a short period of time.WARN
     *
     * @param text The text.
     */
    public static void showErrorShort(final CharSequence text) {
        show(text == null ? NULL : text, Toast.LENGTH_SHORT, Type.ERROR);
    }

    private static void show(final CharSequence text, int duration, Type type) {
        Context context = AppManager.getAppManager().getCurrentActivity() == null ? AppManager.getAppManager().getTopActivity() : AppManager.getAppManager().getCurrentActivity();
        //使用布局加载器，将编写的toast_layout布局加载进来
        View view = LayoutInflater.from(context).inflate(R.layout.public_dialog_toast, null);
        //获取ImageView
        AppCompatImageView image = view.findViewById(R.id.iv_toast_icon);
        //设置图片
        switch (type) {
            case FINISH:
                image.setImageResource(R.drawable.public_ic_dialog_finish);
                break;
            case ERROR:
                image.setImageResource(R.drawable.public_ic_dialog_error);
                break;
            case WARN:
                image.setImageResource(R.drawable.public_ic_dialog_warning);
                break;
        }

        //获取TextView
        AppCompatTextView title = view.findViewById(R.id.tv_toast_message);
        //设置显示的内容
        title.setText(text);
        if (mToast == null) {
            mToast = new Toast(context);
            //设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移70个单位，
            mToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER, 0, 70);
            //设置显示时间
            mToast.setDuration(duration);
        }
        mToast.setView(view);
        mToast.show();
    }
}
