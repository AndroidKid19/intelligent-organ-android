package com.yway.scomponent.organ.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.organ.di.module.CanteenModule;
import com.yway.scomponent.organ.mvp.contract.CanteenContract;
import com.yway.scomponent.organ.mvp.ui.activity.CanteenActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/02/25 15:36
 * ================================================
 */
@ActivityScope
@Component(modules = CanteenModule.class, dependencies = AppComponent.class)
public interface CanteenComponent {

    void inject(CanteenActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CanteenComponent.Builder view(CanteenContract.View view);

        CanteenComponent.Builder appComponent(AppComponent appComponent);

        CanteenComponent build();
    }
}