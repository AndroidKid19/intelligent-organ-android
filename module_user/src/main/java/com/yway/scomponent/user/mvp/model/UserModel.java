package com.yway.scomponent.user.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.user.mvp.contract.UserContract;
import com.yway.scomponent.user.mvp.model.api.service.UserService;
import com.yway.scomponent.commonsdk.core.UserInfoBean;

import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description: TODO 我的
 * <p>
 * Created by yuanweiwei on 09/19/2019 16:42
 * <a href="mailto:yuanw9@sina.com">Contact me</a>
 * <a href="https://github.com/AndroidKid19">Follow me</a>
 * ================================================
 */
@FragmentScope
public class UserModel extends BaseModel implements UserContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public UserModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResponse<UserInfoBean>> queryloginUserInfoById(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .queryloginUserInfoById(params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}