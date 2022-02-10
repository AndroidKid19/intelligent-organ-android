package com.yway.scomponent.organ.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.commonsdk.core.UploadFileBean;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/15 14:38
 * ================================================
 */
public interface ApplyRoomContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        Activity getActivity();

        void uploadError(UploadFileBean data);

        void uploadSuccess(UploadFileBean data);

        void createMeetingSuccess();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BaseResponse> createMeetingRecord(Map<String, Object> params);

        Observable<BaseResponse> draftSubmitMeetingRecord(Map<String, Object> params);

        Observable<BaseResponse<UploadFileBean>> uploadFile(List<File> files);
    }
}