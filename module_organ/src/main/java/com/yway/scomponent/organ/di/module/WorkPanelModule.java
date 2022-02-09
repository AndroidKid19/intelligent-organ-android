package com.yway.scomponent.organ.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;

import com.yway.scomponent.organ.mvp.contract.WorkPanelContract;
import com.yway.scomponent.organ.mvp.model.WorkPanelModel;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/11/15 11:13
 * ================================================
 */
@Module
//构建WorkPanelModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class WorkPanelModule {

    @Binds
    abstract WorkPanelContract.Model bindWorkPanelModel(WorkPanelModel model);
}