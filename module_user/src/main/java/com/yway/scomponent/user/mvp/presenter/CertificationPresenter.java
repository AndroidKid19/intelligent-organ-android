package com.yway.scomponent.user.mvp.presenter;

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
import com.yway.scomponent.commonsdk.core.UploadFileBean;
import com.yway.scomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import com.yway.scomponent.user.mvp.contract.CertificationContract;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/06 16:57
 * ================================================
 */
@ActivityScope
public class CertificationPresenter extends BasePresenter<CertificationContract.Model, CertificationContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public CertificationPresenter(CertificationContract.Model model, CertificationContract.View rootView) {
        super(model, rootView);
    }

    /**
     * @method  上传图片
     * @return
     */
    public void uploadPicture(List<File> files){
        mModel.uploadPicture(files)
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
                        if (datas.isSuccess()){
                            mRootView.uploadSuccess(datas.getData());
                        }else{
                            mRootView.uploadError(datas.getData());
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
                        .build());
    }


    /**
     * 个人认证
     * @param  params
     * "certifiedStatus"：认证状态：0待提交认证，1已提交认证（认证中），2认证已通过，3认证被驳回
     * "frontIdCardUrl":"身份证正面url",
     * "afterIdCardUrl":"身份证反面url",
     * "certificateNumber":"证件号码"
     * "name":"姓名"
     * @return
     */
    public void modifyAppUserInfoById(Map<String,Object> params) {
        mModel.modifyAppUserInfoById(params)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse result) {
                        if (result.isSuccess()){
                            mRootView.modifySuccess();
                        }else{
                            ArmsUtils.snackbarText(result.getMessage());
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