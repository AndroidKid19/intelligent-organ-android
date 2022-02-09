package com.yway.scomponent.user.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.yway.scomponent.user.mvp.contract.ModifyUserInfoContract;
import com.yway.scomponent.user.mvp.model.ModifyUserInfoModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created on 07/03/2020 11:53
 * ================================================
 */
@Module
public abstract class ModifyUserInfoModule {

    @Binds
    abstract ModifyUserInfoContract.Model bindModifyUserInfoModel(ModifyUserInfoModel model);
}