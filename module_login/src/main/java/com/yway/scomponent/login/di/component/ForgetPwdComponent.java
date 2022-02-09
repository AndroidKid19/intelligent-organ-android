package com.yway.scomponent.login.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;
import com.yway.scomponent.login.di.module.ForgetPwdModule;
import com.yway.scomponent.login.mvp.contract.ForgetPwdContract;
import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.login.mvp.ui.activity.ForgetPwdActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created on 10/24/2020 09:56
 * ================================================
 */
@ActivityScope
@Component(modules = ForgetPwdModule.class, dependencies = AppComponent.class)
public interface ForgetPwdComponent {
    void inject(ForgetPwdActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ForgetPwdComponent.Builder view(ForgetPwdContract.View view);

        ForgetPwdComponent.Builder appComponent(AppComponent appComponent);

        ForgetPwdComponent build();
    }
}