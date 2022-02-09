package com.yway.scomponent.commonsdk.utils;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.content.ContextCompat;

/**
 * @ProjectName: ITOC-Android
 * @Package: com.fatalsignal.outsourcing.commonsdk.utils
 * @ClassName: DrawablesUtil
 * @Description:
 * @Author: Yuanweiwei
 * @CreateDate: 2019/9/20 15:33
 * @UpdateUser:
 * @UpdateDate: 2019/9/20 15:33
 * @UpdateRemark:
 * @Version: 1.0
 */
public class DrawablesUtil {
    public static void setTopDrawable(TextView vId, int icId) {
        // 使用代码设置drawableleft
        Drawable drawable = ContextCompat.getDrawable(vId.getContext(),
                icId);
        // / 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        vId.setCompoundDrawables(null, drawable, null, null);
    }

    public static void setRightDrawable(TextView vId, int icId) {
        // 使用代码设置drawableleft
        Drawable drawable = ContextCompat.getDrawable(vId.getContext(),
                icId);
        // / 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        vId.setCompoundDrawables(null, null, drawable, null);
    }

    public static void setLeftDrawable(TextView vId, int icId) {
        // 使用代码设置drawableleft
        Drawable drawable = ContextCompat.getDrawable(vId.getContext(),
                icId);
        // / 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        vId.setCompoundDrawables(drawable,null,null,null);
    }

    public static void setRightDrawable(AppCompatCheckBox vId, int icId) {
        // 使用代码设置drawableleft
        Drawable drawable = ContextCompat.getDrawable(vId.getContext(),
                icId);
        // / 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        vId.setCompoundDrawables(null, null, drawable, null);
    }
}
