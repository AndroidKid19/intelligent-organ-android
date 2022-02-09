package com.yway.scomponent.organ.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;

import com.yway.scomponent.organ.mvp.contract.ConferenceRoomContract;
import com.yway.scomponent.organ.mvp.model.ConferenceRoomModel;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/06 16:57
 * ================================================
 */
@Module
//构建ConferenceRoomModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class ConferenceRoomModule {

    @Binds
    abstract ConferenceRoomContract.Model bindConferenceRoomModel(ConferenceRoomModel model);
}