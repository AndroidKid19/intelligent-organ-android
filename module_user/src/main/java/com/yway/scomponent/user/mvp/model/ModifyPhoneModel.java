package com.yway.scomponent.user.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.user.mvp.contract.ModifyPhoneContract;
import com.yway.scomponent.user.mvp.model.api.service.UserService;

import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * ================================================
 */
@ActivityScope
public class ModifyPhoneModel extends BaseModel implements ModifyPhoneContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ModifyPhoneModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResponse> sendSms(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .sendSms(params);
    }

    @Override
    public Observable<BaseResponse> modifyCellPhoneByUserId(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .modifyCellPhoneByUserId(params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}