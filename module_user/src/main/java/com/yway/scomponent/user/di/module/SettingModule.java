package com.yway.scomponent.user.di.module;

import dagger.Binds;
import dagger.Module;

import com.yway.scomponent.user.mvp.contract.SettingContract;
import com.yway.scomponent.user.mvp.model.SettingModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVP on 10/09/2019 14:44
 * <a href="mailto:yuanw9@sina.com">Contact me</a>
 * ================================================
 */
@Module
public abstract class SettingModule {

    @Binds
    abstract SettingContract.Model bindSettingModel(SettingModel model);
}