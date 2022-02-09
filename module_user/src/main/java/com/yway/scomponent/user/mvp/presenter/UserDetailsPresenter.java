package com.yway.scomponent.user.mvp.presenter;

import android.app.Application;
import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.yway.scomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import com.yway.scomponent.user.R;
import com.yway.scomponent.user.mvp.contract.UserDetailsContract;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/08 14:49
 * ================================================
 */
@ActivityScope
public class UserDetailsPresenter extends BasePresenter<UserDetailsContract.Model, UserDetailsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public UserDetailsPresenter(UserDetailsContract.Model model, UserDetailsContract.View rootView) {
        super(model, rootView);
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