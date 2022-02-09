package com.yway.scomponent.user.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.yway.scomponent.user.mvp.contract.ModifyPhoneContract;
import com.yway.scomponent.user.mvp.model.ModifyPhoneModel;


/**
 * ================================================
 * Description:
 * ================================================
 */
@Module
public abstract class ModifyPhoneModule {

    @Binds
    abstract ModifyPhoneContract.Model bindModifyPhoneModel(ModifyPhoneModel model);
}