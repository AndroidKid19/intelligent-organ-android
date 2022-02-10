package com.yway.scomponent.organ.mvp.contract;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.ethanhua.skeleton.SkeletonScreen;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.yway.scomponent.commonres.view.layout.MultipleStatusView;
import com.yway.scomponent.commonsdk.core.BaseResponse;
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
public interface BeforePrepareContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        Activity getActivity();

        Fragment getFragment();

        SkeletonScreen skeletonScreen();

        MultipleStatusView multipleStatusView();

        RefreshLayout refreshLayout();

        void approvalResultsCallBack(int approvalResult);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<MeetingRecordBean>> getMeetingRecordReadyingList(Map<String, Object> params);

        Observable<BaseResponse> doMeetingRecordReady(Map<String, Object> params);
    }
}