package com.yway.scomponent.organ.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;

import com.yway.scomponent.organ.mvp.contract.MySubscribeContract;
import com.yway.scomponent.organ.mvp.model.MySubscribeModel;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/01/01 11:01
 * ================================================
 */
@Module
//构建MySubscribeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class MySubscribeModule {

    @Binds
    abstract MySubscribeContract.Model bindMySubscribeModel(MySubscribeModel model);
}