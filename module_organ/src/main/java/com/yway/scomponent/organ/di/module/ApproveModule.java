package com.yway.scomponent.organ.di.module;

import dagger.Binds;
import dagger.Module;

import com.yway.scomponent.organ.mvp.contract.ApproveContract;
import com.yway.scomponent.organ.mvp.model.ApproveModel;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/31 14:59
 * ================================================
 */
@Module
//构建ApproveModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class ApproveModule {

    @Binds
    abstract ApproveContract.Model bindApproveModel(ApproveModel model);
}