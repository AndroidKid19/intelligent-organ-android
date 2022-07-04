package com.yway.scomponent.user.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.user.mvp.model.entity.AppVersion;

import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVP on 10/09/2019 14:44
 * <a href="mailto:yuanw9@sina.com">Contact me</a>
 * ================================================
 */
public interface SettingContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void resultSuccess();

        void resultError(String msg);

        void upgradeAppBcakCall(AppVersion data);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BaseResponse<AppVersion>> queryLatestVersionByEntity(Map<String, Object> params);
    }
}
