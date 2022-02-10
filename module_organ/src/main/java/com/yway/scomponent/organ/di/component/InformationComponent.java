package com.yway.scomponent.organ.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.organ.di.module.InformationModule;
import com.yway.scomponent.organ.mvp.contract.InformationContract;
import com.yway.scomponent.organ.mvp.ui.activity.InformationActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/02/10 14:33
 * ================================================
 */
@ActivityScope
@Component(modules = InformationModule.class, dependencies = AppComponent.class)
public interface InformationComponent {

    void inject(InformationActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        InformationComponent.Builder view(InformationContract.View view);

        InformationComponent.Builder appComponent(AppComponent appComponent);

        InformationComponent build();
    }
}