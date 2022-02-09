package com.yway.scomponent.organ.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.di.component.AppComponent;
import com.yway.scomponent.organ.di.module.HomeModule;
import com.yway.scomponent.organ.mvp.contract.HomeContract;

import com.yway.scomponent.organ.mvp.ui.fragment.HomeFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVP on 05/09/2020 17:25
 * <a href="mailto:yuanw9@sina.com">Contact me</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HomeModule.class, dependencies = AppComponent.class)
public interface HomeComponent {
    void inject(HomeFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HomeComponent.Builder view(HomeContract.View view);

        HomeComponent.Builder appComponent(AppComponent appComponent);

        HomeComponent build();
    }
}