package com.yway.scomponent.organ.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.organ.mvp.contract.MyMeetingContract;
import com.yway.scomponent.organ.mvp.model.api.service.HomeService;
import com.yway.scomponent.organ.mvp.model.entity.ConferenceBean;
import com.yway.scomponent.organ.mvp.model.entity.MeetingRecordBean;

import java.util.Map;

import io.reactivex.Observable;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/07 16:19
 * ================================================
 */
@ActivityScope
public class MyMeetingModel extends BaseModel implements MyMeetingContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MyMeetingModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
    @Override
    public Observable<BaseResponse<ConferenceBean>> queryMyMeetingPageList(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .queryMyMeetingPageList(params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}