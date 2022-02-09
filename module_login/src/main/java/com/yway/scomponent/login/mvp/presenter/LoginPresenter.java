
package com.yway.scomponent.login.mvp.presenter;

import android.app.Application;
import androidx.core.app.ComponentActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.commonsdk.core.ParamsHelper;
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.commonsdk.http.Api;
import com.yway.scomponent.commonsdk.utils.CacheUtils;
import com.yway.scomponent.login.mvp.contract.LoginHomeContract;
import com.yway.scomponent.login.mvp.model.entity.LoginBaseResponse;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * ================================================
 * 展示 Presenter 的用法
 * ================================================
 */
@ActivityScope
public class LoginPresenter extends BasePresenter<LoginHomeContract.Model, LoginHomeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;


    @Inject
    public LoginPresenter(LoginHomeContract.Model model, LoginHomeContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 使用 2017 Google IO 发布的 Architecture Components 中的 Lifecycles 的新特性 (此特性已被加入 Support library)
     * 使 {@code Presenter} 可以与 {@link ComponentActivity} 和 {@link Fragment} 的部分生命周期绑定
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        //打开 App 时自动加载列表
    }

    public void login(Map<String, String> params) {
        mModel.login(params)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UserInfoBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<UserInfoBean> data) {
                        if (data.isSuccess()) {
                            mRootView.loginSuccess(data.getData());
                        } else {
                            mRootView.loginError(data.getMessage());
                        }
                    }
                });
    }

    public void queryDoctorInfo() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("biz", "doctor_prove_app");
        paramMap.put("doctor_id", CacheUtils.queryDoctorId());
        paramMap.putAll(ParamsHelper.getCommonParams());
        mModel.queryDoctorInfo(paramMap)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UserInfoBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<UserInfoBean> result) {
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                        if (result.isSuccess()) {
                            mRootView.queryDoctorInfoSuccess(result.getData());
                        } else {
                            mRootView.loginError(result.getMessage());
                        }
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }
}
