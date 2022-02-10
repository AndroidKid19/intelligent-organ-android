package com.yway.scomponent.organ.mvp.presenter;

import android.app.Application;
import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
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
import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.mvp.contract.MeetingDetailsContract;
import com.yway.scomponent.organ.mvp.model.entity.MeetingDetailsBean;

import java.util.Map;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/08 14:49
 * ================================================
 */
@ActivityScope
public class MeetingDetailsPresenter extends BasePresenter<MeetingDetailsContract.Model, MeetingDetailsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MeetingDetailsPresenter(MeetingDetailsContract.Model model, MeetingDetailsContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 会议详情
     */
    public void queryByMeetingRecordDetails(Map<String, Object> params) {
        mModel.queryByMeetingRecordDetails(params)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<MeetingDetailsBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<MeetingDetailsBean> result) {
                        mRootView.hideLoading();//隐藏进度条
                        if (result.isSuccess()) {
                            mRootView.queryMessingDetailsSuccess(result.getData());

                        } else {
                            mRootView.showMessage(result.getMessage());
                        }
                    }
                });
    }

    /**
     * 审批
     * “approvalResult”: 2,            //审批状态     2审批已通过，3审批被驳回
     * approvalOpinion 意见
     */
    public void doMeetingRecordApproval(Map<String, Object> params) {
        mModel.doMeetingRecordApproval(params)
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
                            mRootView.approvalResultsCallBack((Integer) params.get("approvalResult"));
                        } else {
                            ArmsUtils.snackbarText(datas.getMessage());
                        }
                    }
                });
    }

    /**
     * 准备会议
     * “approvalResult”: 2,            //审批状态     2审批已通过，3审批被驳回
     * approvalOpinion 意见
     */
    public void doMeetingRecordReady(Map<String, Object> params) {
        mModel.doMeetingRecordReady(params)
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
                            mRootView.doMeetingRecordReadyCallBack();
                        } else {
                            ArmsUtils.snackbarText(datas.getMessage());
                        }
                    }
                });
    }


    /**
     * @description TODO  加载图片
     * @return
     */
    public void imageLoader(String url, ImageView imageView){
        //itemView 的 Context 就是 Activity, Glide 会自动处理并和该 Activity 的生命周期绑定
        if (StringUtils.isEmpty(url))return;
        mImageLoader.loadImage(mApplication,
                CommonImageConfigImpl
                        .builder()
                        .url(url)
                        .isCropCircle(true)
                        .imageView(imageView)
                        .placeholder(R.mipmap.public_ic_default_head)
                        .build());
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