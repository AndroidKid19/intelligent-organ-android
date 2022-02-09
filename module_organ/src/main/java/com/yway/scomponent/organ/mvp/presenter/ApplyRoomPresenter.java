package com.yway.scomponent.organ.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.commonsdk.core.UploadFileBean;
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.mvp.contract.ApplyRoomContract;
import com.yway.scomponent.commonsdk.core.AddressCompanyBean;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/15 14:38
 * ================================================
 */
@ActivityScope
public class ApplyRoomPresenter extends BasePresenter<ApplyRoomContract.Model, ApplyRoomContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ApplyRoomPresenter(ApplyRoomContract.Model model, ApplyRoomContract.View rootView) {
        super(model, rootView);
    }

    /**
     * @return
     * @method 会议预约接口 及 存入草稿箱接口
     */
    public void createMeetingRecord(Map<String, Object> params) {
        mModel.createMeetingRecord(params)
                .subscribeOn(Schedulers.io())
                //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse datas) {
                        mRootView.hideLoading();
                        if (datas.isSuccess()) {
                            mRootView.createMeetingSuccess();
                        } else {
                            ArmsUtils.snackbarText(datas.getMessage());
                        }
                    }
                });
    }

    /**
     * @return
     * @method 上传文件
     */
    public void uploadFile(List<File> files) {
        mModel.uploadFile(files)
                .subscribeOn(Schedulers.io())
                //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UploadFileBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<UploadFileBean> datas) {
                        mRootView.hideLoading();
                        if (datas.isSuccess()) {
                            mRootView.uploadSuccess(datas.getData());
                        } else {
                            mRootView.uploadError(datas.getData());
                        }
                    }
                });
    }

    /**
     * 生成用户字符串名称
     */
    public String generateStrUserNames(List<UserInfoBean> data) {
        String userNames = "";
        for (int i = 0; i < (data.size() > 4 ? 3 : data.size()); i++) {
            userNames = Utils.appendStr(data.get(i).getName(), "、", userNames);
        }
        userNames = userNames.substring(0, userNames.length() - 1);
        //超过四人则展示等多少人
        if (data.size() > 4) {
            userNames = Utils.appendStr(userNames, "等", (data.size() - 3), "人");
        }
        return userNames;
    }

    /**
     * 校验当前单参会单位是否已选择
     */
    public boolean checkedCompany(AddressCompanyBean companyBean, List<AddressCompanyBean> list) {
        boolean isChecked = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getOrgId().equals(companyBean.getOrgId())) {
                isChecked = true;
            }
        }
        return isChecked;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}