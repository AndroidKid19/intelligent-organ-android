package com.yway.scomponent.user.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yway.scomponent.user.di.module.ModifyUserInfoModule;
import com.yway.scomponent.user.mvp.contract.ModifyUserInfoContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.user.mvp.ui.activity.ModifyUserInfoActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created on 07/03/2020 11:53
 * ================================================
 */
@ActivityScope
@Component(modules = ModifyUserInfoModule.class, dependencies = AppComponent.class)
public interface ModifyUserInfoComponent {
    void inject(ModifyUserInfoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ModifyUserInfoComponent.Builder view(ModifyUserInfoContract.View view);

        ModifyUserInfoComponent.Builder appComponent(AppComponent appComponent);

        ModifyUserInfoComponent build();
    }
}