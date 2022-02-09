package com.yway.scomponent.user.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.user.mvp.contract.MessageContract;
import com.yway.scomponent.user.mvp.model.api.Api;
import com.yway.scomponent.user.mvp.model.api.service.UserService;
import com.yway.scomponent.user.mvp.model.entity.MessageBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created on 07/21/2020 13:36
 * ================================================
 */
@ActivityScope
public class MessageModel extends BaseModel implements MessageContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MessageModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResponse<List<MessageBean>>> queryMessage(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .queryMessage(params);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}