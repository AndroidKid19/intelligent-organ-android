package com.yway.scomponent.login.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.login.mvp.contract.ForgetPwdContract;
import com.yway.scomponent.login.mvp.model.api.service.LoginService;

import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created on 10/24/2020 09:56
 * ================================================
 */
@ActivityScope
public class ForgetPwdModel extends BaseModel implements ForgetPwdContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ForgetPwdModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResponse> sendSms(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(LoginService.class)
                .sendSms(params);
    }

    @Override
    public Observable<BaseResponse> modifyForgetPasswordByPhone(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(LoginService.class)
                .modifyForgetPasswordByPhone(params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}