package com.yway.scomponent.organ.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.yway.scomponent.organ.di.module.ApprovedModule;
import com.yway.scomponent.organ.mvp.contract.ApprovedContract;
import com.yway.scomponent.organ.mvp.ui.fragment.ApprovedFragment;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/01/01 11:01
 * ================================================
 */
@FragmentScope
@Component(modules = ApprovedModule.class, dependencies = AppComponent.class)
public interface ApprovedComponent {

    void inject(ApprovedFragment fragment);
    @Component.Builder
    interface Builder {
        @BindsInstance
        ApprovedComponent.Builder view(ApprovedContract.View view);

        ApprovedComponent.Builder appComponent(AppComponent appComponent);

        ApprovedComponent build();
    }
}