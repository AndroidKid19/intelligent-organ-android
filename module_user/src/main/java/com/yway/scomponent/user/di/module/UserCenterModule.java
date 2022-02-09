package com.yway.scomponent.user.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;

import com.yway.scomponent.user.mvp.contract.UserCenterContract;
import com.yway.scomponent.user.mvp.model.UserCenterModel;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/02 14:20
 * ================================================
 */
@Module
//构建UserCenterModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class UserCenterModule {

    @Binds
    abstract UserCenterContract.Model bindUserCenterModel(UserCenterModel model);
}