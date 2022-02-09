package com.yway.scomponent.organ.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.organ.di.module.ChooseUserModule;
import com.yway.scomponent.organ.mvp.contract.ChooseUserContract;
import com.yway.scomponent.organ.mvp.ui.activity.ChooseUserActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/22 14:35
 * ================================================
 */
@ActivityScope
@Component(modules = ChooseUserModule.class, dependencies = AppComponent.class)
public interface ChooseUserComponent {

    void inject(ChooseUserActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ChooseUserComponent.Builder view(ChooseUserContract.View view);

        ChooseUserComponent.Builder appComponent(AppComponent appComponent);

        ChooseUserComponent build();
    }
}