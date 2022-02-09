package com.yway.scomponent.organ.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.organ.di.module.OnlineSubscribeRoomModule;
import com.yway.scomponent.organ.mvp.contract.OnlineSubscribeRoomContract;
import com.yway.scomponent.organ.mvp.ui.activity.OnlineSubscribeRoomActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/07 16:19
 * ================================================
 */
@ActivityScope
@Component(modules = OnlineSubscribeRoomModule.class, dependencies = AppComponent.class)
public interface OnlineSubscribeRoomComponent {

    void inject(OnlineSubscribeRoomActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        OnlineSubscribeRoomComponent.Builder view(OnlineSubscribeRoomContract.View view);

        OnlineSubscribeRoomComponent.Builder appComponent(AppComponent appComponent);

        OnlineSubscribeRoomComponent build();
    }
}