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
package com.yway.scomponent.wheel.mvp.model.api;

/**
 * ================================================
 * 存放一些与 API 有关的东西,如请求地址,请求码等
 * ================================================
 */
public interface Api {
    String HOME_DOMAIN_NAME = "wheelapi";

    //草稿创建会议
    String API_CREATEARTICLEFAVORITES = "/organ/articleFavorites/createArticleFavorites";


    //取消收藏
    String API_CANCELARTICLEFAVORITES = "/organ/articleFavorites/cancelArticleFavorites";
}
