package com.yway.scomponent.user.mvp.presenter;

import android.app.Activity;
import android.app.Application;

import androidx.fragment.app.FragmentActivity;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yway.scomponent.user.mvp.contract.FeedbackContract;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/02 14:20
 * ================================================
 */
@ActivityScope
public class FeedbackPresenter extends BasePresenter<FeedbackContract.Model, FeedbackContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public FeedbackPresenter(FeedbackContract.Model model, FeedbackContract.View rootView) {
        super(model, rootView);
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