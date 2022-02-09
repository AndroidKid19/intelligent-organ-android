package com.yway.scomponent.user.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yway.scomponent.user.di.module.ModifyPhoneModule;
import com.yway.scomponent.user.mvp.contract.ModifyPhoneContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.user.mvp.ui.activity.ModifyPhoneActivity;


/**
 * ================================================
 * Description:
 * ================================================
 */
@ActivityScope
@Component(modules = ModifyPhoneModule.class, dependencies = AppComponent.class)
public interface ModifyPhoneComponent {
    void inject(ModifyPhoneActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ModifyPhoneComponent.Builder view(ModifyPhoneContract.View view);

        ModifyPhoneComponent.Builder appComponent(AppComponent appComponent);

        ModifyPhoneComponent build();
    }
}