package com.yway.scomponent.organ.mvp.presenter;

import android.app.Activity;
import android.app.Application;

import androidx.fragment.app.FragmentActivity;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.organ.mvp.contract.WorkPanelContract;
import com.yway.scomponent.organ.mvp.model.entity.RechargeRecordBean;

import java.util.HashMap;
import java.util.Map;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/11/15 11:13
 * ================================================
 */
@FragmentScope
public class WorkPanelPresenter extends BasePresenter<WorkPanelContract.Model, WorkPanelContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public WorkPanelPresenter(WorkPanelContract.Model model, WorkPanelContract.View rootView) {
        super(model, rootView);
    }

    public RxPermissions getRxPermissions(Activity activity){
        return new RxPermissions((FragmentActivity) activity);
    }
    /**
     * 消费
     */
    public void createAccountTransactionRecord(Map<String,Object> map) {
        mModel.createAccountTransactionRecord(map)
                .subscribeOn(Schedulers.io())
                //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {

                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse datas) {
                        if (datas.isSuccess()) {
                            mRootView.paymentCallBack();
                        } else {
                            ArmsUtils.snackbarText(datas.getMessage());
                        }
                    }
                });
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