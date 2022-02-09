package com.yway.scomponent.organ.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;
import com.yway.scomponent.organ.di.module.MyMeetingModule;

import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.organ.mvp.contract.HomeContract;
import com.yway.scomponent.organ.mvp.contract.MyMeetingContract;
import com.yway.scomponent.organ.mvp.ui.activity.MyMeetingActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/07 16:19
 * ================================================
 */
@ActivityScope
@Component(modules = MyMeetingModule.class, dependencies = AppComponent.class)
public interface MyMeetingComponent {

    void inject(MyMeetingActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyMeetingComponent.Builder view(MyMeetingContract.View view);

        MyMeetingComponent.Builder appComponent(AppComponent appComponent);

        MyMeetingComponent build();
    }
}