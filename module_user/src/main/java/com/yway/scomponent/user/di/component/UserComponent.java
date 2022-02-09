package com.yway.scomponent.user.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yway.scomponent.user.di.module.UserModule;
import com.yway.scomponent.user.mvp.contract.UserContract;

import com.jess.arms.di.scope.FragmentScope;
import com.yway.scomponent.user.mvp.ui.fragment.UserFragment;


/**
 * ================================================
 * Description: TODO 我的
 * <p>
 * Created by yuanweiwei on 09/19/2019 16:42
 * <a href="mailto:yuanw9@sina.com">Contact me</a>
 * <a href="https://github.com/AndroidKid19">Follow me</a>
 * ================================================
 */
@FragmentScope
@Component(modules = UserModule.class, dependencies = AppComponent.class)
public interface UserComponent {
    void inject(UserFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        UserComponent.Builder view(UserContract.View view);

        UserComponent.Builder appComponent(AppComponent appComponent);

        UserComponent build();
    }
}