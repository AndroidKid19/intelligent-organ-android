package com.yway.scomponent.user.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yway.scomponent.user.di.module.AboutModule;
import com.yway.scomponent.user.mvp.contract.AboutContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.user.mvp.ui.activity.AboutActivity;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
@ActivityScope
@Component(modules = AboutModule.class, dependencies = AppComponent.class)
public interface AboutComponent {
    void inject(AboutActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AboutComponent.Builder view(AboutContract.View view);

        AboutComponent.Builder appComponent(AppComponent appComponent);

        AboutComponent build();
    }
}