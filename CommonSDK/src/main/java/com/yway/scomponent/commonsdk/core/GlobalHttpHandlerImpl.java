/*
 * Copyright 2018 JessYan
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
package com.yway.scomponent.commonsdk.core;
import android.content.Context;
import com.blankj.utilcode.util.StringUtils;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.http.GlobalHttpHandler;
import com.jess.arms.utils.ArmsUtils;
import com.tencent.mmkv.MMKV;
import com.yway.scomponent.commonsdk.http.Api;
import com.yway.scomponent.commonsdk.utils.CacheUtils;
import com.yway.scomponent.commonsdk.utils.Utils;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ================================================
 * 展示 {@link GlobalHttpHandler} 的用法
 * ================================================
 */
public class GlobalHttpHandlerImpl implements GlobalHttpHandler {
    private Context context;

    public GlobalHttpHandlerImpl(Context context) {
        this.context = context;
    }

    /**
     * 这里可以先客户端一步拿到每一次 Http 请求的结果, 可以先解析成 Json, 再做一些操作, 如检测到 token 过期后
     * 重新请求 token, 并重新执行请求
     *
     * @param httpResult 服务器返回的结果 (已被框架自动转换为字符串)
     * @param chain {@link okhttp3.Interceptor.Chain}
     * @param response {@link Response}
     * @return
     */
    @Override
    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
        /* 这里如果发现 token 过期, 可以先请求最新的 token, 然后在拿新的 token 放入 Request 里去重新请求
        注意在这个回调之前已经调用过 proceed(), 所以这里必须自己去建立网络请求, 如使用 Okhttp 使用新的 Request 去请求
        create a new request and modify it accordingly using the new token
        Request newRequest = chain.request().newBuilder().header("token", newToken)
                             .build();
        retry the request
        response.body().close();
        如果使用 Okhttp 将新的请求, 请求成功后, 再将 Okhttp 返回的 Response return 出去即可
        如果不需要返回新的结果, 则直接把参数 response 返回出去即可 */

        if (!StringUtils.isEmpty(httpResult)) {
            try {
                BaseResponse baseResponse = ArmsUtils.obtainAppComponentFromContext(context).gson().fromJson(httpResult, new TypeToken<BaseResponse>() {
                }.getType());
                if (Api.ERROR_LOGIN_OUT == baseResponse.getCode()) {
                    MMKV.defaultMMKV().clearAll();
                    Utils.navigation(context, RouterHub.LOGGIN_LOGINACTIVITY);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return response;
            }
        }
        return response;
    }

    /**
     * 这里可以在请求服务器之前拿到 {@link Request}, 做一些操作比如给 {@link Request} 统一添加 token 或者 header 以及参数加密等操作
     *
     * @param chain {@link okhttp3.Interceptor.Chain}
     * @param request {@link Request}
     * @return
     */
    @Override
    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
        //请求定制：添加请求头
        Request.Builder requestBuilder = request.newBuilder();
        if (!StringUtils.isEmpty(CacheUtils.queryToken())){
            requestBuilder.header("token", CacheUtils.queryToken());
        }
        //请求体定制：统一添加token参数
        if (request.body() instanceof FormBody) {
            FormBody oidFormBody = (FormBody) request.body();
            // 01 获取所有参数
            HashMap<String, String> hashMapParam = new HashMap<>();
            for (int i = 0; i < oidFormBody.size(); i++) {
                if (!StringUtils.isEmpty(oidFormBody.encodedValue(i))) {
                    try {
                        hashMapParam.put(oidFormBody.encodedName(i), URLDecoder.decode(oidFormBody.encodedValue(i),"Utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
            Map treeMap = ParamsHelper.getCommonParams();
            treeMap.putAll(hashMapParam);
            HashMap<String,String> paramHashMap = new HashMap<>(ParamsHelper.encryptSign(((TreeMap) treeMap).descendingMap()));

            //03 重组参数
            FormBody.Builder newFormBody = new FormBody.Builder();
            Iterator iter = paramHashMap.entrySet().iterator();
            while (iter.hasNext()){
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                newFormBody.add(key.toString(), val.toString());
            }

            requestBuilder.method(request.method(), newFormBody.build());
        }
        /* 如果需要再请求服务器之前做一些操作, 则重新返回一个做过操作的的 Request 如增加 Header, 不做操作则直接返回参数 request
        return chain.request().newBuilder().header("token", tokenId)
                              .build(); */
        return requestBuilder.build();
    }
}
