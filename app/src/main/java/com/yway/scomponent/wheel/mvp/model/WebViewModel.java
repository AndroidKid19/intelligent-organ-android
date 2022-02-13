package com.yway.scomponent.wheel.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.wheel.mvp.contract.WebViewContract;
import com.yway.scomponent.wheel.mvp.model.service.AppService;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created on 07/29/2020 12:07
 * ================================================
 */
@ActivityScope
public class WebViewModel extends BaseModel implements WebViewContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public WebViewModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Observable<BaseResponse> createArticleFavorites(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(AppService.class)
                .createArticleFavorites(params);
    }


    @Override
    public Observable<BaseResponse> cancelArticleFavorites(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(AppService.class)
                .cancelArticleFavorites(params);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}