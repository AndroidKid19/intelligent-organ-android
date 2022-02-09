package com.yway.scomponent.login.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.yway.scomponent.commonsdk.core.BaseResponse;

import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created on 10/24/2020 09:56
 * ================================================
 */
public interface ForgetPwdContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void sendSmsSuccess();

        void modifyForgetPasswordSuccess();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BaseResponse> sendSms(Map<String, Object> params);

        Observable<BaseResponse> modifyForgetPasswordByPhone(Map<String, Object> params);
    }
}
