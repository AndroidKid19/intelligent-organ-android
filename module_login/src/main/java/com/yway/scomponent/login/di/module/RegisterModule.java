package com.yway.scomponent.login.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;

import com.yway.scomponent.login.mvp.contract.RegisterContract;
import com.yway.scomponent.login.mvp.model.RegisterModel;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/01 14:55
 * ================================================
 */
@Module
//构建RegisterModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class RegisterModule {

    @Binds
    abstract RegisterContract.Model bindRegisterModel(RegisterModel model);
}