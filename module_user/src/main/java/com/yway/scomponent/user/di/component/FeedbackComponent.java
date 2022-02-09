package com.yway.scomponent.user.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.user.di.module.FeedbackModule;
import com.yway.scomponent.user.mvp.contract.FeedbackContract;
import com.yway.scomponent.user.mvp.ui.activity.FeedbackActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/02 14:20
 * ================================================
 */
@ActivityScope
@Component(modules = FeedbackModule.class, dependencies = AppComponent.class)
public interface FeedbackComponent {

    void inject(FeedbackActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        FeedbackComponent.Builder view(FeedbackContract.View view);

        FeedbackComponent.Builder appComponent(AppComponent appComponent);

        FeedbackComponent build();
    }
}