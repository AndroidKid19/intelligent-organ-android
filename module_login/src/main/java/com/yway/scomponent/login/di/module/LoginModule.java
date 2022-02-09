
package com.yway.scomponent.login.di.module;

import com.yway.scomponent.login.mvp.contract.LoginHomeContract;
import com.yway.scomponent.login.mvp.model.LoginModel;

import dagger.Binds;
import dagger.Module;

/**
 * ================================================
 * 展示 Module 的用法
 * ================================================
 */
@Module
public abstract class LoginModule {
    @Binds
    abstract LoginHomeContract.Model bindGankModel(LoginModel model);

}
