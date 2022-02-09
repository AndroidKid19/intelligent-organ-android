package com.yway.scomponent.organ.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.organ.di.module.VisitorRecordModule;
import com.yway.scomponent.organ.mvp.contract.VisitorRecordContract;
import com.yway.scomponent.organ.mvp.ui.activity.VisitorRecordActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/01/10 10:31
 * ================================================
 */
@ActivityScope
@Component(modules = VisitorRecordModule.class, dependencies = AppComponent.class)
public interface VisitorRecordComponent {

    void inject(VisitorRecordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        VisitorRecordComponent.Builder view(VisitorRecordContract.View view);

        VisitorRecordComponent.Builder appComponent(AppComponent appComponent);

        VisitorRecordComponent build();
    }
}