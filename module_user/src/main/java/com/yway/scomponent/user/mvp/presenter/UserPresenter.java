package com.yway.scomponent.user.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.StringUtils;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.commonsdk.http.Api;
import com.yway.scomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import com.yway.scomponent.user.mvp.contract.UserContract;
import com.yway.scomponent.commonsdk.core.UserInfoBean;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * Description: TODO 我的
 * <p>
 * Created by yuanweiwei on 09/19/2019 16:42
 * <a href="mailto:yuanw9@sina.com">Contact me</a>
 * <a href="https://github.com/AndroidKid19">Follow me</a>
 * ================================================
 */
@FragmentScope
public class UserPresenter extends BasePresenter<UserContract.Model, UserContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


    @Inject
    public UserPresenter(UserContract.Model model, UserContract.View rootView) {
        super(model, rootView);
    }

    public void queryloginUserInfoById(Map<String,Object> params) {
        mModel.queryloginUserInfoById(params)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UserInfoBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<UserInfoBean> result) {
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                        if (result.isSuccess()){
                            mRootView.queryUserInfoSuccess(result.getData());
                        }else{
                            mRootView.showMessage(result.getMessage());
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

    public RxPermissions getRxPermissions(Activity activity){
        return new RxPermissions((FragmentActivity) activity);
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
