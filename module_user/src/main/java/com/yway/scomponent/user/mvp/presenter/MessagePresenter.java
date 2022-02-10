package com.yway.scomponent.user.mvp.presenter;

import android.app.Application;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.blankj.utilcode.util.CollectionUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.commonsdk.core.ParamsHelper;
import com.yway.scomponent.commonsdk.utils.CacheUtils;
import com.yway.scomponent.user.mvp.contract.MessageContract;
import com.yway.scomponent.user.mvp.model.entity.MessageBean;
import com.yway.scomponent.user.mvp.ui.adapter.MessageAdapter;

import java.util.HashMap;
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
 * Created on 07/21/2020 13:36
 * ================================================
 */
@ActivityScope
public class MessagePresenter extends BasePresenter<MessageContract.Model, MessageContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    MessageAdapter mAdapter;
    @Inject
    List<MessageBean> mDataLs;

    @Inject
    public MessagePresenter(MessageContract.Model model, MessageContract.View rootView) {
        super(model, rootView);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        requestData();
    }

    public void requestData() {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("doctor_id", CacheUtils.queryUserId());
        paramMap.putAll(ParamsHelper.getCommonParams());
        queryMessage(paramMap);
    }

    public void queryMessage(Map<String,Object> params) {
        mModel.queryMessage(params)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<MessageBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<MessageBean>> result) {
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                        if (result.isSuccess()){
                            if (CollectionUtils.isEmpty(result.getData())){
                                mRootView.multipleStatusView().showEmpty();
                                return;
                            }
                            mRootView.multipleStatusView().showContent();
                            mDataLs.clear();
                            mDataLs.addAll(result.getData());
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.multipleStatusView().showEmpty();
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
