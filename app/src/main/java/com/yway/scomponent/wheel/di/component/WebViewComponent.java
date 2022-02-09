package com.yway.scomponent.wheel.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yway.scomponent.wheel.di.module.WebViewModule;
import com.yway.scomponent.wheel.mvp.contract.WebViewContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.wheel.mvp.ui.activity.WebViewActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created on 07/29/2020 12:07
 * ================================================
 */
@ActivityScope
@Component(modules = WebViewModule.class, dependencies = AppComponent.class)
public interface WebViewComponent {
    void inject(WebViewActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WebViewComponent.Builder view(WebViewContract.View view);

        WebViewComponent.Builder appComponent(AppComponent appComponent);

        WebViewComponent build();
    }
}