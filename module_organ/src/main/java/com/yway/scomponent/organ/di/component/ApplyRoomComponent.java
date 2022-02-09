package com.yway.scomponent.organ.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.organ.di.module.ApplyRoomModule;
import com.yway.scomponent.organ.mvp.contract.ApplyRoomContract;
import com.yway.scomponent.organ.mvp.ui.activity.ApplyRoomActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/15 14:38
 * ================================================
 */
@ActivityScope
@Component(modules = ApplyRoomModule.class, dependencies = AppComponent.class)
public interface ApplyRoomComponent {

    void inject(ApplyRoomActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ApplyRoomComponent.Builder view(ApplyRoomContract.View view);

        ApplyRoomComponent.Builder appComponent(AppComponent appComponent);

        ApplyRoomComponent build();
    }
}