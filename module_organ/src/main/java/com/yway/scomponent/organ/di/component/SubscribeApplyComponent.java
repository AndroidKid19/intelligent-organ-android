package com.yway.scomponent.organ.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.yway.scomponent.organ.di.module.SubscribeApplyModule;
import com.yway.scomponent.organ.mvp.contract.SubscribeApplyContract;
import com.yway.scomponent.organ.mvp.ui.fragment.SubscribeApplyFragment;

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
@Component(modules = SubscribeApplyModule.class, dependencies = AppComponent.class)
public interface SubscribeApplyComponent {

    void inject(SubscribeApplyFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SubscribeApplyComponent.Builder view(SubscribeApplyContract.View view);

        SubscribeApplyComponent.Builder appComponent(AppComponent appComponent);

        SubscribeApplyComponent build();
    }
}