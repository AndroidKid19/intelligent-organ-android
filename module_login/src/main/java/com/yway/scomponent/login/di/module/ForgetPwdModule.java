package com.yway.scomponent.login.di.module;

import com.yway.scomponent.login.mvp.contract.ForgetPwdContract;
import com.yway.scomponent.login.mvp.model.ForgetPwdModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created on 10/24/2020 09:56
 * ================================================
 */
@Module
public abstract class ForgetPwdModule {

    @Binds
    abstract ForgetPwdContract.Model bindForgetPwdModel(ForgetPwdModel model);
}