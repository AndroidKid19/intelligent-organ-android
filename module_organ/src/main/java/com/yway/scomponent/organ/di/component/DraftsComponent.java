package com.yway.scomponent.organ.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.yway.scomponent.organ.di.module.DraftsModule;
import com.yway.scomponent.organ.mvp.contract.DraftsContract;
import com.yway.scomponent.organ.mvp.ui.fragment.DraftsFragment;

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
@Component(modules = DraftsModule.class, dependencies = AppComponent.class)
public interface DraftsComponent {

    void inject(DraftsFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DraftsComponent.Builder view(DraftsContract.View view);

        DraftsComponent.Builder appComponent(AppComponent appComponent);

        DraftsComponent build();
    }
}