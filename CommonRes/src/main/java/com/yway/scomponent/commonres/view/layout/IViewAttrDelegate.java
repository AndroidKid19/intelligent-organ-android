package com.yway.scomponent.commonres.view.layout;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

/**
 * @ProjectName: Android-Project
 * @Package: com.fatalsignal.outsourcing.commonres.view
 * @ClassName: IViewAttrDelegate
 * @Description:
 * @Author: Yuanweiwei
 * @CreateDate: 2019/10/10 14:40
 * @UpdateUser:
 * @UpdateDate: 2019/10/10 14:40
 * @UpdateRemark:
 * @Version: 1.0
 */
public interface IViewAttrDelegate {

    public void initAttr(Context context, @Nullable AttributeSet attrs, int defStyleAttr);
}
