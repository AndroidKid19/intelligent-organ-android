package com.yway.scomponent.user.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;

import com.yway.scomponent.user.mvp.contract.FeedbackContract;
import com.yway.scomponent.user.mvp.model.FeedbackModel;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/02 14:20
 * ================================================
 */
@Module
//构建FeedbackModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class FeedbackModule {

    @Binds
    abstract FeedbackContract.Model bindFeedbackModel(FeedbackModel model);
}