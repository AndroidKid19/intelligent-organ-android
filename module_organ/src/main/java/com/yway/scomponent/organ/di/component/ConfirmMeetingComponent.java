package com.yway.scomponent.organ.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.yway.scomponent.organ.di.module.ConfirmMeetingModule;
import com.yway.scomponent.organ.mvp.contract.ConfirmMeetingContract;
import com.yway.scomponent.organ.mvp.ui.fragment.ConfirmMeetingFragment;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/08 14:49
 * ================================================
 */
@FragmentScope
@Component(modules = ConfirmMeetingModule.class, dependencies = AppComponent.class)
public interface ConfirmMeetingComponent {

    void inject(ConfirmMeetingFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ConfirmMeetingComponent.Builder view(ConfirmMeetingContract.View view);

        ConfirmMeetingComponent.Builder appComponent(AppComponent appComponent);

        ConfirmMeetingComponent build();
    }
}