package com.yway.scomponent.organ.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;

import com.yway.scomponent.organ.mvp.contract.WebViewContract;
import com.yway.scomponent.organ.mvp.model.WebViewModel;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/02/11 14:22
 * ================================================
 */
@Module
//构建WebViewModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class WebViewModule {

    @Binds
    abstract WebViewContract.Model bindWebViewModel(WebViewModel model);
}