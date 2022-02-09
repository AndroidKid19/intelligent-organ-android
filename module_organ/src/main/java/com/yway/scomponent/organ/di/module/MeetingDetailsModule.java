package com.yway.scomponent.organ.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;

import com.yway.scomponent.organ.mvp.contract.MeetingDetailsContract;
import com.yway.scomponent.organ.mvp.model.MeetingDetailsModel;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/08 14:49
 * ================================================
 */
@Module
//构建MeetingDetailsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class MeetingDetailsModule {

    @Binds
    abstract MeetingDetailsContract.Model bindMeetingDetailsModel(MeetingDetailsModel model);
}