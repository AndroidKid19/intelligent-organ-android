package com.yway.scomponent.user.mvp.presenter;

import android.app.Activity;
import android.app.Application;

import androidx.fragment.app.FragmentActivity;
import com.blankj.utilcode.util.ObjectUtils;
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
import com.jess.arms.utils.RxLifecycleUtils;
import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.user.mvp.contract.SettingContract;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yway.scomponent.user.mvp.model.entity.AppVersion;

import java.util.HashMap;
import java.util.Map;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVP on 10/09/2019 14:44
 * <a href="mailto:yuanw9@sina.com">Contact me</a>
 * ================================================
 */
@ActivityScope
public class SettingPresenter extends BasePresenter<SettingContract.Model, SettingContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public SettingPresenter(SettingContract.Model model, SettingContract.View rootView) {
        super(model, rootView);
    }
    public RxPermissions getRxPermissions(Activity activity){
        return new RxPermissions((FragmentActivity) activity);
    }

    public void upgradeApp() {
        Map<String, Object> params = new HashMap<>();
        /**
         * 类型：0为ios端 1为android端
         */
        params.put("type", 1);
        mModel.queryLatestVersionByEntity(params)
                .subscribeOn(Schedulers.io())
                //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<AppVersion>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<AppVersion> datas) {
                        if (datas.isSuccess() && !ObjectUtils.isEmpty(datas.getData())) {
                            mRootView.upgradeAppBcakCall(datas.getData());
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
