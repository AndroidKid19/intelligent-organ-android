package com.yway.scomponent.login.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.login.mvp.contract.RegisterContract;
import com.yway.scomponent.login.mvp.model.api.service.LoginService;

import java.util.Map;

import io.reactivex.Observable;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/01 14:55
 * ================================================
 */
@ActivityScope
public class RegisterModel extends BaseModel implements RegisterContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public RegisterModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResponse> sendSms(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(LoginService.class)
                .sendSms(params);
    }

    @Override
    public Observable<BaseResponse> register(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(LoginService.class)
                .register(params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}