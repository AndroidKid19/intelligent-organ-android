package com.yway.scomponent.organ.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;
import com.yway.scomponent.organ.di.module.BeforePrepareModule;

import com.jess.arms.di.scope.FragmentScope;
import com.yway.scomponent.organ.mvp.contract.BeforePrepareContract;
import com.yway.scomponent.organ.mvp.contract.PrepareMetingContract;
import com.yway.scomponent.organ.mvp.ui.fragment.BeforePrepareFragment;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/02/09 19:58
 * ================================================
 */
@FragmentScope
@Component(modules = BeforePrepareModule.class, dependencies = AppComponent.class)
public interface BeforePrepareComponent {

    void inject(BeforePrepareFragment fragment);
    @Component.Builder
    interface Builder {
        @BindsInstance
        BeforePrepareComponent.Builder view(BeforePrepareContract.View view);

        BeforePrepareComponent.Builder appComponent(AppComponent appComponent);

        BeforePrepareComponent build();
    }
}