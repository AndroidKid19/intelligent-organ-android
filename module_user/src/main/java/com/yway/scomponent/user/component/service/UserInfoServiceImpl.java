/*
 * Copyright 2018 AndroidKid19
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yway.scomponent.user.component.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yway.scomponent.user.mvp.ui.fragment.UserFragment;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonservice.user.bean.UserInfo;
import com.yway.scomponent.commonservice.user.service.UserInfoService;

/**
 * ================================================
 * 向外提供服务的接口实现类, 在此类中实现一些具有特定功能的方法提供给外部, 即可让一个组件与其他组件或宿主进行交互
 *
 * @see <a href="https://github.com/AndroidKid19/ArmsComponent/wiki#2.2.3.2">CommonService wiki 官方文档</a>
 * Created by AndroidKid19 on 2018/4/27 14:27
 * <a href="mailto:yuanw9@sina.com">Contact me</a>
 * <a href="https://github.com/AndroidKid19Coding">Follow me</a>
 * ================================================
 */
@Route(path = RouterHub.USER_SERVICE_USERINFOSERVICE, name = "我的信息服务")
public class UserInfoServiceImpl implements UserInfoService {
    private Context mContext;

    @Override
    public UserInfo getInfo() {
        return new UserInfo(UserFragment.newInstance());
    }

    @Override
    public void init(Context context) {
        mContext = context;
    }
}
