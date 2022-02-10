package com.yway.scomponent.organ.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.yway.scomponent.organ.di.module.AfterPrepareModule;
import com.yway.scomponent.organ.mvp.contract.AfterPrepareContract;
import com.yway.scomponent.organ.mvp.ui.fragment.AfterPrepareFragment;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/02/09 19:58
 * ================================================
 */
@FragmentScope
@Component(modules = AfterPrepareModule.class, dependencies = AppComponent.class)
public interface AfterPrepareComponent {

    void inject(AfterPrepareFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AfterPrepareComponent.Builder view(AfterPrepareContract.View view);

        AfterPrepareComponent.Builder appComponent(AppComponent appComponent);

        AfterPrepareComponent build();
    }
}