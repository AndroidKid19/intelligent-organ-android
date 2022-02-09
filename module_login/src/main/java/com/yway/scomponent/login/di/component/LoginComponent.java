
package com.yway.scomponent.login.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.login.di.module.LoginModule;

import dagger.BindsInstance;
import dagger.Component;

import com.yway.scomponent.login.mvp.contract.LoginHomeContract;
import com.yway.scomponent.login.mvp.ui.activity.LoginActivity;

/**
 * ================================================
 * 展示 Component 的用法
 * ================================================
 */
@ActivityScope
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent {
    void inject(LoginActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        LoginComponent.Builder view(LoginHomeContract.View view);
        LoginComponent.Builder appComponent(AppComponent appComponent);
        LoginComponent build();
    }
}
