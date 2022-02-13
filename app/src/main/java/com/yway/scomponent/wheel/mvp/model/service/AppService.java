/*
 * Copyright 2017 JessYan
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
package com.yway.scomponent.wheel.mvp.model.service;

import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.wheel.mvp.model.api.Api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
 * ================================================
 * 展示 {@link Retrofit#create(Class)} 中需要传入的 ApiService 的使用方式
 * 存放关于 zhihu 的一些 API
 * <p>
 * Created by JessYan on 08/05/2016 12:05
 * <p>
 * ================================================
 */
public interface AppService {

    /**
     * 收藏
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_CREATEARTICLEFAVORITES)
    Observable<BaseResponse> createArticleFavorites(@Body() Map<String, Object> params);


    /**
     * 取消收藏
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_CANCELARTICLEFAVORITES)
    Observable<BaseResponse> cancelArticleFavorites(@Body() Map<String, Object> params);
}
