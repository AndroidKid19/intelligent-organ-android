package com.yway.scomponent.organ.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;

import com.yway.scomponent.organ.mvp.contract.PrepareMetingContract;
import com.yway.scomponent.organ.mvp.model.PrepareMetingModel;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/02/09 19:58
 * ================================================
 */
@Module
//构建PrepareMetingModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class PrepareMetingModule {

    @Binds
    abstract PrepareMetingContract.Model bindPrepareMetingModel(PrepareMetingModel model);
}