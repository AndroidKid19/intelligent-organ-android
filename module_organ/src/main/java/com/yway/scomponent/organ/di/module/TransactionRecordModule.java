package com.yway.scomponent.organ.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;

import com.yway.scomponent.organ.mvp.contract.TransactionRecordContract;
import com.yway.scomponent.organ.mvp.model.TransactionRecordModel;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/02/25 15:36
 * ================================================
 */
@Module
//构建TransactionRecordModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class TransactionRecordModule {

    @Binds
    abstract TransactionRecordContract.Model bindTransactionRecordModel(TransactionRecordModel model);
}