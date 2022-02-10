package com.yway.scomponent.organ.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.organ.di.module.PrepareMetingModule;
import com.yway.scomponent.organ.mvp.contract.PrepareMetingContract;
import com.yway.scomponent.organ.mvp.ui.activity.PrepareMetingActivity;
import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/02/09 19:58
 * ================================================
 */
@ActivityScope
@Component(modules = PrepareMetingModule.class, dependencies = AppComponent.class)
public interface PrepareMetingComponent {

    void inject(PrepareMetingActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PrepareMetingComponent.Builder view(PrepareMetingContract.View view);

        PrepareMetingComponent.Builder appComponent(AppComponent appComponent);

        PrepareMetingComponent build();
    }
}