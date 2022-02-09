package com.yway.scomponent.wheel.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.yway.scomponent.wheel.mvp.contract.WebViewContract;
import com.yway.scomponent.wheel.mvp.model.WebViewModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created on 07/29/2020 12:07
 * ================================================
 */
@Module
public abstract class WebViewModule {

    @Binds
    abstract WebViewContract.Model bindWebViewModel(WebViewModel model);
}