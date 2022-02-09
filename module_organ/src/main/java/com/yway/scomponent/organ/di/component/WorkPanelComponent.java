package com.yway.scomponent.organ.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;
import com.yway.scomponent.organ.di.module.WorkPanelModule;

import com.jess.arms.di.scope.FragmentScope;
import com.yway.scomponent.organ.mvp.contract.WorkPanelContract;
import com.yway.scomponent.organ.mvp.ui.fragment.WorkPanelFragment;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/11/15 11:13
 * ================================================
 */
@FragmentScope
@Component(modules = WorkPanelModule.class, dependencies = AppComponent.class)
public interface WorkPanelComponent {

    void inject(WorkPanelFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WorkPanelComponent.Builder view(WorkPanelContract.View view);

        WorkPanelComponent.Builder appComponent(AppComponent appComponent);

        WorkPanelComponent build();
    }
}