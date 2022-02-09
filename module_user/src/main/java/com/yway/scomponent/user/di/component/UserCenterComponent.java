package com.yway.scomponent.user.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;
import com.yway.scomponent.user.di.module.UserCenterModule;

import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.user.mvp.contract.UserCenterContract;
import com.yway.scomponent.user.mvp.contract.UserContract;
import com.yway.scomponent.user.mvp.ui.activity.UserCenterActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/02 14:20
 * ================================================
 */
@ActivityScope
@Component(modules = UserCenterModule.class, dependencies = AppComponent.class)
public interface UserCenterComponent {

    void inject(UserCenterActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        UserCenterComponent.Builder view(UserCenterContract.View view);

        UserCenterComponent.Builder appComponent(AppComponent appComponent);

        UserCenterComponent build();
    }
}