package com.yway.scomponent.user.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yway.scomponent.user.di.module.MessageDetailsModule;
import com.yway.scomponent.user.mvp.contract.MessageDetailsContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.user.mvp.ui.activity.MessageDetailsActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created on 07/21/2020 14:09
 * ================================================
 */
@ActivityScope
@Component(modules = MessageDetailsModule.class, dependencies = AppComponent.class)
public interface MessageDetailsComponent {
    void inject(MessageDetailsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MessageDetailsComponent.Builder view(MessageDetailsContract.View view);

        MessageDetailsComponent.Builder appComponent(AppComponent appComponent);

        MessageDetailsComponent build();
    }
}