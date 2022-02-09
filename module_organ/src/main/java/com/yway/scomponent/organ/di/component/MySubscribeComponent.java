package com.yway.scomponent.organ.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.organ.di.module.MySubscribeModule;
import com.yway.scomponent.organ.mvp.contract.MySubscribeContract;
import com.yway.scomponent.organ.mvp.ui.activity.MySubscribeActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/01/01 11:01
 * ================================================
 */
@ActivityScope
@Component(modules = MySubscribeModule.class, dependencies = AppComponent.class)
public interface MySubscribeComponent {

    void inject(MySubscribeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MySubscribeComponent.Builder view(MySubscribeContract.View view);

        MySubscribeComponent.Builder appComponent(AppComponent appComponent);

        MySubscribeComponent build();
    }
}