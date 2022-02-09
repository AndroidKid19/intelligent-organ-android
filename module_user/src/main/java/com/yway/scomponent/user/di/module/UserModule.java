package com.yway.scomponent.user.di.module;

import dagger.Binds;
import dagger.Module;

import com.yway.scomponent.user.mvp.contract.UserContract;
import com.yway.scomponent.user.mvp.model.UserModel;


/**
 * ================================================
 * Description: TODO 我的
 * <p>
 * Created by yuanweiwei on 09/19/2019 16:42
 * <a href="mailto:yuanw9@sina.com">Contact me</a>
 * <a href="https://github.com/AndroidKid19">Follow me</a>
 * ================================================
 */
@Module
public abstract class UserModule {

    @Binds
    abstract UserContract.Model bindUserModel(UserModel model);
}