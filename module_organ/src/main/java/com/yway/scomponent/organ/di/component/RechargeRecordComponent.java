package com.yway.scomponent.organ.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.yway.scomponent.organ.di.module.RechargeRecordModule;
import com.yway.scomponent.organ.mvp.contract.RechargeRecordContract;
import com.yway.scomponent.organ.mvp.ui.fragment.RechargeRecordFragment;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/02/25 15:36
 * ================================================
 */
@FragmentScope
@Component(modules = RechargeRecordModule.class, dependencies = AppComponent.class)
public interface RechargeRecordComponent {

    void inject(RechargeRecordFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        RechargeRecordComponent.Builder view(RechargeRecordContract.View view);

        RechargeRecordComponent.Builder appComponent(AppComponent appComponent);

        RechargeRecordComponent build();
    }
}