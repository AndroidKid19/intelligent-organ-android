package com.yway.scomponent.organ.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.organ.di.module.TransactionRecordModule;
import com.yway.scomponent.organ.mvp.contract.TransactionRecordContract;
import com.yway.scomponent.organ.mvp.ui.activity.TransactionRecordActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/02/25 15:36
 * ================================================
 */
@ActivityScope
@Component(modules = TransactionRecordModule.class, dependencies = AppComponent.class)
public interface TransactionRecordComponent {

    void inject(TransactionRecordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TransactionRecordComponent.Builder view(TransactionRecordContract.View view);

        TransactionRecordComponent.Builder appComponent(AppComponent appComponent);

        TransactionRecordComponent build();
    }
}