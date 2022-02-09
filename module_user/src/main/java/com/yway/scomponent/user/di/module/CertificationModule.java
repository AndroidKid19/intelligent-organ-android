package com.yway.scomponent.user.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;

import com.yway.scomponent.user.mvp.contract.CertificationContract;
import com.yway.scomponent.user.mvp.model.CertificationModel;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/06 16:57
 * ================================================
 */
@Module
//构建CertificationModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class CertificationModule {

    @Binds
    abstract CertificationContract.Model bindCertificationModel(CertificationModel model);
}