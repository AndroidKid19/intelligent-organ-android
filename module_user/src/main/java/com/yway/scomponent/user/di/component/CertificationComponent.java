package com.yway.scomponent.user.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.user.di.module.CertificationModule;
import com.yway.scomponent.user.mvp.contract.CertificationContract;
import com.yway.scomponent.user.mvp.ui.activity.CertificationActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/06 16:57
 * ================================================
 */
@ActivityScope
@Component(modules = CertificationModule.class, dependencies = AppComponent.class)
public interface CertificationComponent {

    void inject(CertificationActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CertificationComponent.Builder view(CertificationContract.View view);

        CertificationComponent.Builder appComponent(AppComponent appComponent);

        CertificationComponent build();
    }
}