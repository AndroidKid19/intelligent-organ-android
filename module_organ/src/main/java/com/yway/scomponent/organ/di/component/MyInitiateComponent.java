package com.yway.scomponent.organ.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.yway.scomponent.organ.di.module.MyInitiateModule;
import com.yway.scomponent.organ.mvp.contract.MyInitiateContract;
import com.yway.scomponent.organ.mvp.ui.fragment.MyInitiateFragment;

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
@Component(modules = MyInitiateModule.class, dependencies = AppComponent.class)
public interface MyInitiateComponent {

    void inject(MyInitiateFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyInitiateComponent.Builder view(MyInitiateContract.View view);

        MyInitiateComponent.Builder appComponent(AppComponent appComponent);

        MyInitiateComponent build();
    }
}