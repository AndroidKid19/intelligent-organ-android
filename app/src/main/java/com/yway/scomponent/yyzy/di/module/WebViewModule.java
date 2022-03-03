package com.yway.scomponent.yyzy.di.module;

import dagger.Binds;
import dagger.Module;

import com.yway.scomponent.yyzy.mvp.contract.WebViewContract;
import com.yway.scomponent.yyzy.mvp.model.WebViewModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created on 07/29/2020 12:07
 * ================================================
 */
@Module
public abstract class WebViewModule {

    @Binds
    abstract WebViewContract.Model bindWebViewModel(WebViewModel model);
}