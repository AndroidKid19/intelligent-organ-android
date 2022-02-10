package com.yway.scomponent.organ.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;
import com.yway.scomponent.organ.di.module.UserSearchModule;

import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.organ.mvp.contract.UserSearchContract;
import com.yway.scomponent.organ.mvp.contract.VisitorRecordContract;
import com.yway.scomponent.organ.mvp.ui.activity.UserSearchActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/02/09 19:58
 * ================================================
 */
@ActivityScope
@Component(modules = UserSearchModule.class, dependencies = AppComponent.class)
public interface UserSearchComponent {

    void inject(UserSearchActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        UserSearchComponent.Builder view(UserSearchContract.View view);

        UserSearchComponent.Builder appComponent(AppComponent appComponent);

        UserSearchComponent build();
    }
}