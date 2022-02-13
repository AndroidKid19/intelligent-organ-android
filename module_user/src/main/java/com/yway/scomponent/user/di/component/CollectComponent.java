package com.yway.scomponent.user.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.user.di.module.CollectModule;
import com.yway.scomponent.user.mvp.contract.CollectContract;
import com.yway.scomponent.user.mvp.ui.activity.CollectActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/02/11 14:22
 * ================================================
 */
@ActivityScope
@Component(modules = CollectModule.class, dependencies = AppComponent.class)
public interface CollectComponent {

    void inject(CollectActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CollectComponent.Builder view(CollectContract.View view);

        CollectComponent.Builder appComponent(AppComponent appComponent);

        CollectComponent build();
    }
}