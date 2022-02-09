package com.yway.scomponent.organ.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.organ.di.module.SubscribeSucceesModule;
import com.yway.scomponent.organ.mvp.contract.SubscribeSucceesContract;
import com.yway.scomponent.organ.mvp.ui.activity.SubscribeSucceesActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/28 15:04
 * ================================================
 */
@ActivityScope
@Component(modules = SubscribeSucceesModule.class, dependencies = AppComponent.class)
public interface SubscribeSucceesComponent {

    void inject(SubscribeSucceesActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SubscribeSucceesComponent.Builder view(SubscribeSucceesContract.View view);

        SubscribeSucceesComponent.Builder appComponent(AppComponent appComponent);

        SubscribeSucceesComponent build();
    }
}