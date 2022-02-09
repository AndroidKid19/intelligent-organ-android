package com.yway.scomponent.user.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.yway.scomponent.user.mvp.contract.MessageDetailsContract;
import com.yway.scomponent.user.mvp.model.MessageDetailsModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created on 07/21/2020 14:09
 * ================================================
 */
@Module
public abstract class MessageDetailsModule {

    @Binds
    abstract MessageDetailsContract.Model bindMessageDetailsModel(MessageDetailsModel model);
}