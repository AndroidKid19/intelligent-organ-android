package com.yway.scomponent.user.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.user.mvp.contract.ModifyUserInfoContract;
import com.yway.scomponent.user.mvp.model.api.service.UserService;

import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created on 07/03/2020 11:53
 * ================================================
 */
@ActivityScope
public class ModifyUserInfoModel extends BaseModel implements ModifyUserInfoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ModifyUserInfoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResponse> modifyAppUserInfoById(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .modifyAppUserInfoById(params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}