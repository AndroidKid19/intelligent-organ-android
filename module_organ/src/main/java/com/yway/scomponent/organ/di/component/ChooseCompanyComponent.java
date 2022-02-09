package com.yway.scomponent.organ.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;
import com.yway.scomponent.organ.di.module.ChooseCompanyModule;

import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.organ.mvp.contract.ChooseCompanyContract;
import com.yway.scomponent.organ.mvp.ui.activity.ChooseCompanyActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/23 11:05
 * ================================================
 */
@ActivityScope
@Component(modules = ChooseCompanyModule.class, dependencies = AppComponent.class)
public interface ChooseCompanyComponent {

    void inject(ChooseCompanyActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ChooseCompanyComponent.Builder view(ChooseCompanyContract.View view);

        ChooseCompanyComponent.Builder appComponent(AppComponent appComponent);

        ChooseCompanyComponent build();
    }
}