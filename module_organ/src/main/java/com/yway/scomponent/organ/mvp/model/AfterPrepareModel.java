package com.yway.scomponent.organ.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.organ.mvp.contract.AfterPrepareContract;
import com.yway.scomponent.organ.mvp.model.api.service.HomeService;
import com.yway.scomponent.organ.mvp.model.entity.MeetingRecordBean;

import java.util.Map;

import io.reactivex.Observable;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/02/09 19:58
 * ================================================
 */
@FragmentScope
public class AfterPrepareModel extends BaseModel implements AfterPrepareContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public AfterPrepareModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
    @Override
    public Observable<BaseResponse<MeetingRecordBean>> getMeetingRecordReadyedList(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .getMeetingRecordReadyedList(params);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}