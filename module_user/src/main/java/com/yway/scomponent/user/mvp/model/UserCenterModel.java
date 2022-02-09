package com.yway.scomponent.user.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.commonsdk.core.ParamsHelper;
import com.yway.scomponent.commonsdk.core.UploadFileBean;
import com.yway.scomponent.user.mvp.contract.UserCenterContract;
import com.yway.scomponent.user.mvp.model.api.service.UserService;

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
 * Created by MVPArmsTemplate on 2021/12/02 14:20
 * ================================================
 */
@ActivityScope
public class UserCenterModel extends BaseModel implements UserCenterContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public UserCenterModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
    /**
     * @return : io.reactivex.Observable
     * @description :上传图片
     * @author : yuanweiwei
     * @date : 2018/12/24 12:01
     */
    @Override
    public Observable<BaseResponse<UploadFileBean>> uploadPicture(File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("files", file.getName(), requestFile);
        Observable<BaseResponse<UploadFileBean>> data = mRepositoryManager.obtainRetrofitService(UserService.class).uploadPicture(body);
        return data;
    }

    @Override
    public Observable<BaseResponse<UploadFileBean>> uploadPicture(List<File> files) {
        Observable<BaseResponse<UploadFileBean>> data = mRepositoryManager.obtainRetrofitService(UserService.class).uploadPictureMore(filesToMultipartBody(files));
        return data;
    }


    public static MultipartBody filesToMultipartBody(List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (int i = 0; i < files.size(); i++) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), files.get(i));
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
    public Observable<BaseResponse> modifyAppUserInfoById(Map<String, Object> params) {
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .modifyAppUserInfoById(params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}