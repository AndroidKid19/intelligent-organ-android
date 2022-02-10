package com.yway.scomponent.organ.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.organ.mvp.contract.DraftsContract;
import com.yway.scomponent.organ.mvp.model.api.service.HomeService;
import com.yway.scomponent.organ.mvp.model.entity.MeetingDetailsBean;
import com.yway.scomponent.organ.mvp.model.entity.MeetingRecordBean;

import java.util.Map;

import io.reactivex.Observable;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/01/01 11:01
 * ================================================
 */
@FragmentScope
public class DraftsModel extends BaseModel implements DraftsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public DraftsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResponse<MeetingRecordBean>> queryMeetingRecordPageList(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .queryMeetingRecordPageList(params);
    }


    @Override
    public Observable<BaseResponse> deleteMeetingRecord(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .deleteMeetingRecord(params);
    }

    @Override
    public Observable<BaseResponse<MeetingDetailsBean>> queryByMeetingRecordDetails(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .queryByMeetingRecordDetails(params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}