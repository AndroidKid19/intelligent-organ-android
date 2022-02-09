
package com.yway.scomponent.login.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.login.mvp.model.entity.LoginBaseResponse;
import com.yway.scomponent.login.mvp.model.entity.LoginItemBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * ================================================
 * 展示 Contract 的用法
 *
 * @see <a href="https://github.com/AndroidKid19Coding/MVPArms/wiki#2.4.1">Contract wiki 官方文档</a>
 * Created by AndroidKid19 on 09/04/2016 10:47
 * <a href="mailto:yuanw9@sina.com">Contact me</a>
 * <a href="https://github.com/AndroidKid19Coding">Follow me</a>
 * ================================================
 */
public interface LoginHomeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loginSuccess(UserInfoBean data);
        void loginError(String msg);
        void queryDoctorInfoSuccess(UserInfoBean data);
    }
    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    interface Model extends IModel{
        Observable<LoginBaseResponse<List<LoginItemBean>>> getGirlList(int num, int page);

        Observable<BaseResponse<UserInfoBean>> login(Map<String,String> params);

        Observable<BaseResponse<UserInfoBean>> queryDoctorInfo(Map<String, Object> params);
    }
}
