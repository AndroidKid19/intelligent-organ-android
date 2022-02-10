package com.yway.scomponent.organ.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.organ.mvp.contract.MeetingDetailsContract;
import com.yway.scomponent.organ.mvp.model.api.service.HomeService;
import com.yway.scomponent.organ.mvp.model.entity.MeetingDetailsBean;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/08 14:49
 * ================================================
 */
@ActivityScope
public class MeetingDetailsModel extends BaseModel implements MeetingDetailsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MeetingDetailsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResponse<MeetingDetailsBean>> queryByMeetingRecordDetails(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .queryByMeetingRecordDetails(params);
    }


    @Override
    public Observable<BaseResponse> doMeetingRecordApproval(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .doMeetingRecordApproval(params);
    }

    @Override
    public Observable<BaseResponse> doMeetingRecordReady(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .doMeetingRecordReady(params);
    }


    @Override
    public Observable<BaseResponse> doCancelMeetingRecord(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .doCancelMeetingRecord(params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}