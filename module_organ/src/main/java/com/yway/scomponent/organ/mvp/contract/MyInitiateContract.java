package com.yway.scomponent.organ.mvp.contract;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/01/01 11:01
 * ================================================
 */
public interface MyInitiateContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        Fragment getFragment();

        Activity getActivity();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

    }
}