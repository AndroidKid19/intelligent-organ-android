package com.yway.scomponent.organ.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.commonsdk.core.ParamsHelper;
import com.yway.scomponent.commonsdk.core.UploadFileBean;
import com.yway.scomponent.organ.mvp.contract.ApplyRoomContract;
import com.yway.scomponent.organ.mvp.model.api.service.HomeService;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/15 14:38
 * ================================================
 */
@ActivityScope
public class ApplyRoomModel extends BaseModel implements ApplyRoomContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ApplyRoomModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Observable<BaseResponse> createMeetingRecord(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .createMeetingRecord(params);
    }

    @Override
    public Observable<BaseResponse> draftSubmitMeetingRecord(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .draftSubmitMeetingRecord(params);
    }

    /**
     * @description :上传文件
     */
    @Override
    public Observable<BaseResponse<UploadFileBean>> uploadFile(List<File> files) {
        Observable<BaseResponse<UploadFileBean>> data = mRepositoryManager.obtainRetrofitService(HomeService.class).uploadPictureMore(filesToMultipartBody(files));
        return data;
    }


    public static MultipartBody filesToMultipartBody(List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (int i = 0; i < files.size(); i++) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), files.get(i));
            builder.addFormDataPart("files", files.get(i).getName(), requestBody);
        }

        Map treeMap = ParamsHelper.getCommonParams();
        HashMap<String,String> paramHashMap = new HashMap<>(ParamsHelper.encryptSign(((TreeMap) treeMap).descendingMap()));
        //03 重组参数
        Iterator iter = paramHashMap.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            builder.addFormDataPart(key.toString(), val.toString());
        }
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}