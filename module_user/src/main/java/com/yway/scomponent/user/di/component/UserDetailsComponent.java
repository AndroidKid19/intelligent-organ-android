package com.yway.scomponent.user.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.user.di.module.UserDetailsModule;
import com.yway.scomponent.user.mvp.contract.UserDetailsContract;
import com.yway.scomponent.user.mvp.ui.activity.UserDetailsActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/08 14:49
 * ================================================
 */
@ActivityScope
@Component(modules = UserDetailsModule.class, dependencies = AppComponent.class)
public interface UserDetailsComponent {

    void inject(UserDetailsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        UserDetailsComponent.Builder view(UserDetailsContract.View view);

        UserDetailsComponent.Builder appComponent(AppComponent appComponent);

        UserDetailsComponent build();
    }
}