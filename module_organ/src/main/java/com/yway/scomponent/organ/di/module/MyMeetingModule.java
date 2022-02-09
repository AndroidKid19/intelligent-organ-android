package com.yway.scomponent.organ.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;

import com.yway.scomponent.organ.mvp.contract.MyMeetingContract;
import com.yway.scomponent.organ.mvp.model.MyMeetingModel;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/07 16:19
 * ================================================
 */
@Module
//构建MyMeetingModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class MyMeetingModule {

    @Binds
    abstract MyMeetingContract.Model bindMyMeetingModel(MyMeetingModel model);
}