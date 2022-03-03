package com.yway.scomponent.organ.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.commonsdk.core.DictClassifyBean;
import com.yway.scomponent.organ.mvp.contract.HomeContract;
import com.yway.scomponent.organ.mvp.model.api.service.HomeService;
import com.yway.scomponent.organ.mvp.model.entity.AppVersion;
import com.yway.scomponent.organ.mvp.model.entity.ConfigureBean;
import com.yway.scomponent.organ.mvp.model.entity.HomeMetingBean;
import com.yway.scomponent.organ.mvp.model.entity.MessageBean;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVP on 05/09/2020 17:25
 * <a href="mailto:yuanw9@sina.com">Contact me</a>
 * ================================================
 */
@FragmentScope
public class HomeModel extends BaseModel implements HomeContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public HomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
    @Override
    public Observable<BaseResponse<AppVersion>> queryLatestVersionByEntity(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .queryLatestVersionByEntity(params);
    }
    /**
     * @description 查询字典数据
     * @author: Yuan
     * @return
     */
    @Override
    public Observable<BaseResponse<DictClassifyBean>> querySysByDictClassify(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .querySysByDictClassify(params);
    }

    /**
     * @description 会议列表
     * @author: Yuan
     * @return
     */
    @Override
    public Observable<BaseResponse<HomeMetingBean>> queryMyMeetingList(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .queryMyMeetingList(params);
    }

    /**
     * @description 首页消息
     * @author: Yuan
     * @return
     */
    @Override
    public Observable<BaseResponse<MessageBean>> queryArticlePublishPageList(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .queryArticlePublishPageList(params);
    }

    @Override
    public Observable<BaseResponse<ConfigureBean>> queryApprovalConfigureList(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .queryApprovalConfigureList(params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}