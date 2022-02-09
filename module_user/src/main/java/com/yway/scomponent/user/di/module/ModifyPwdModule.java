package com.yway.scomponent.user.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.yway.scomponent.user.mvp.contract.ModifyPwdContract;
import com.yway.scomponent.user.mvp.model.ModifyPwdModel;


/**
 * ================================================
 * Description:
 * ================================================
 */
@Module
public abstract class ModifyPwdModule {

    @Binds
    abstract ModifyPwdContract.Model bindModifyPwdModel(ModifyPwdModel model);
}