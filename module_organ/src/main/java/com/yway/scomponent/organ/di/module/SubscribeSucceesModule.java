package com.yway.scomponent.organ.di.module;

import com.yway.scomponent.organ.mvp.contract.SubscribeSucceesContract;
import com.yway.scomponent.organ.mvp.model.SubscribeSucceesModel;

import dagger.Binds;
import dagger.Module;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/28 15:04
 * ================================================
 */
@Module
//构建SubscribeSucceesModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class SubscribeSucceesModule {

    @Binds
    abstract SubscribeSucceesContract.Model bindSubscribeSucceesModel(SubscribeSucceesModel model);
}