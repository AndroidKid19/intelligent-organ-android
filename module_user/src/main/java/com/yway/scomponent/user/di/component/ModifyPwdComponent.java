package com.yway.scomponent.user.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yway.scomponent.user.di.module.ModifyPwdModule;
import com.yway.scomponent.user.mvp.contract.ModifyPwdContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.user.mvp.ui.activity.ModifyPwdActivity;


/**
 * ================================================
 * Description:
 * ================================================
 */
@ActivityScope
@Component(modules = ModifyPwdModule.class, dependencies = AppComponent.class)
public interface ModifyPwdComponent {
    void inject(ModifyPwdActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ModifyPwdComponent.Builder view(ModifyPwdContract.View view);

        ModifyPwdComponent.Builder appComponent(AppComponent appComponent);

        ModifyPwdComponent build();
    }
}