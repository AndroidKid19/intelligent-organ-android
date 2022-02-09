package com.yway.scomponent.organ.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;
import com.yway.scomponent.organ.di.module.MeetingDetailsModule;
import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.organ.mvp.contract.MeetingDetailsContract;
import com.yway.scomponent.organ.mvp.ui.activity.MeetingDetailsActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/08 14:49
 * ================================================
 */
@ActivityScope
@Component(modules = MeetingDetailsModule.class, dependencies = AppComponent.class)
public interface MeetingDetailsComponent {

    void inject(MeetingDetailsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MeetingDetailsComponent.Builder view(MeetingDetailsContract.View view);

        MeetingDetailsComponent.Builder appComponent(AppComponent appComponent);

        MeetingDetailsComponent build();
    }
}