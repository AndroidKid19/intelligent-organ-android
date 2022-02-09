package com.yway.scomponent.user.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yway.scomponent.user.di.module.MessageModule;
import com.yway.scomponent.user.mvp.contract.MessageContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.user.mvp.ui.activity.MessageActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created on 07/21/2020 13:36
 * ================================================
 */
@ActivityScope
@Component(modules = MessageModule.class, dependencies = AppComponent.class)
public interface MessageComponent {
    void inject(MessageActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MessageComponent.Builder view(MessageContract.View view);

        MessageComponent.Builder appComponent(AppComponent appComponent);

        MessageComponent build();
    }
}