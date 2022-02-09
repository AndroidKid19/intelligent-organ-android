package com.yway.scomponent.user.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;

import com.yway.scomponent.user.mvp.contract.UserDetailsContract;
import com.yway.scomponent.user.mvp.model.UserDetailsModel;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/08 14:49
 * ================================================
 */
@Module
//构建UserDetailsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class UserDetailsModule {

    @Binds
    abstract UserDetailsContract.Model bindUserDetailsModel(UserDetailsModel model);
}