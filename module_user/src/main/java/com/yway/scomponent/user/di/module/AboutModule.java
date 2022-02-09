package com.yway.scomponent.user.di.module;

import dagger.Binds;
import dagger.Module;

import com.yway.scomponent.user.mvp.contract.AboutContract;
import com.yway.scomponent.user.mvp.model.AboutModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/09/2019 14:18
 * ================================================
 */
@Module
public abstract class AboutModule {

    @Binds
    abstract AboutContract.Model bindAboutModel(AboutModel model);
}