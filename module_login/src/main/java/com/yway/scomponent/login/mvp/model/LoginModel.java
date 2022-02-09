
package com.yway.scomponent.login.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.login.mvp.contract.LoginHomeContract;
import com.yway.scomponent.login.mvp.model.api.service.LoginService;
import com.yway.scomponent.login.mvp.model.entity.LoginBaseResponse;
import com.yway.scomponent.login.mvp.model.entity.LoginItemBean;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * ================================================
 * 展示 Model 的用法
 */
@ActivityScope
public class LoginModel extends BaseModel implements LoginHomeContract.Model {

    @Inject
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<LoginBaseResponse<List<LoginItemBean>>> getGirlList(int num, int page) {
        return mRepositoryManager
                .obtainRetrofitService(LoginService.class)
                .getGirlList(num, page);
    }


    @Override
    public Observable<BaseResponse<UserInfoBean>> login(Map<String,String> params) {
        return mRepositoryManager
                .obtainRetrofitService(LoginService.class)
                .login(params);
    }

    @Override
    public Observable<BaseResponse<UserInfoBean>> queryDoctorInfo(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(LoginService.class)
                .queryDoctorInfo(params);
    }


}
