package com.yway.scomponent.organ.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;
import com.yway.scomponent.organ.di.module.ApproveModule;

import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.organ.mvp.contract.ApproveContract;
import com.yway.scomponent.organ.mvp.contract.ChooseCompanyContract;
import com.yway.scomponent.organ.mvp.ui.activity.ApproveActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/31 14:59
 * ================================================
 */
@ActivityScope
@Component(modules = ApproveModule.class, dependencies = AppComponent.class)
public interface ApproveComponent {

    void inject(ApproveActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ApproveComponent.Builder view(ApproveContract.View view);

        ApproveComponent.Builder appComponent(AppComponent appComponent);

        ApproveComponent build();
    }
}