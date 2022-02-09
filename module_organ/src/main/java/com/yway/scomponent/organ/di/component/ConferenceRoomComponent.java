package com.yway.scomponent.organ.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.organ.di.module.ConferenceRoomModule;
import com.yway.scomponent.organ.mvp.contract.ConferenceRoomContract;
import com.yway.scomponent.organ.mvp.ui.activity.ConferenceRoomActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/06 16:57
 * ================================================
 */
@ActivityScope
@Component(modules = ConferenceRoomModule.class, dependencies = AppComponent.class)
public interface ConferenceRoomComponent {

    void inject(ConferenceRoomActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ConferenceRoomComponent.Builder view(ConferenceRoomContract.View view);

        ConferenceRoomComponent.Builder appComponent(AppComponent appComponent);

        ConferenceRoomComponent build();
    }
}