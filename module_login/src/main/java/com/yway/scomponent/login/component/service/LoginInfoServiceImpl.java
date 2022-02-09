package com.yway.scomponent.login.component.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonservice.login.bean.LoginInfo;
import com.yway.scomponent.commonservice.login.service.LoginInfoService;

/**
 * ================================================
 * 向外提供服务的接口实现类, 在此类中实现一些具有特定功能的方法提供给外部, 即可让一个组件与其他组件或宿主进行交互
 * ================================================
 */
@Route(path = RouterHub.USER_SERVICE_LOGINSERVICE, name = "登录信息服务")
public class LoginInfoServiceImpl implements LoginInfoService {
    private Context mContext;

    @Override
    public LoginInfo getInfo() {
        return new LoginInfo();
    }

    @Override
    public void init(Context context) {
        mContext = context;
    }
}
