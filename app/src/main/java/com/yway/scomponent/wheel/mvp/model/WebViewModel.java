package com.yway.scomponent.wheel.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.yway.scomponent.wheel.mvp.contract.WebViewContract;


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
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}