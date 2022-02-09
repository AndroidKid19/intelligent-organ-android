
package com.yway.scomponent.login.mvp.model.api.service;

import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.login.mvp.model.api.Api;
import com.yway.scomponent.login.mvp.model.entity.LoginBaseResponse;
import com.yway.scomponent.login.mvp.model.entity.LoginItemBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
 * ================================================
 * 展示 {@link Retrofit#create(Class)} 中需要传入的 ApiService 的使用方式
 * 存放关于 gank 的一些 API
 * <p>
 * Created by AndroidKid19 on 08/05/2016 12:05
 * <a href="mailto:yuanw9@sina.com">Contact me</a>
 * <a href="https://github.com/AndroidKid19Coding">Follow me</a>
 * ================================================
 */
public interface LoginService {
    /**
     * 列表
     */
    @Headers({DOMAIN_NAME_HEADER + Api.LOGIN_DOMAIN_NAME})
    @GET("/api/data/福利/{num}/{page}")
    Observable<LoginBaseResponse<List<LoginItemBean>>> getGirlList(@Path("num") int num, @Path("page") int page);


    /**
     * 登录
     */
    @Headers({DOMAIN_NAME_HEADER + Api.LOGIN_DOMAIN_NAME})
    @POST(Api.API_LOGIN)
    Observable<BaseResponse<UserInfoBean>> login(@Body() Map<String, String> params);


    /**
     * 查询用户信息
     */
    @Headers({DOMAIN_NAME_HEADER + Api.LOGIN_DOMAIN_NAME})
    @POST(Api.API_LOGIN)
    Observable<BaseResponse<UserInfoBean>> queryDoctorInfo(@Body() Map<String, Object> params);

    /**
     * 发送验证码
     */
    @Headers({DOMAIN_NAME_HEADER + Api.LOGIN_DOMAIN_NAME})
    @GET(Api.API_SEND_CODE)
    Observable<BaseResponse> sendSms(@QueryMap() Map<String, Object> params);

    /**
     * 注册
     */
    @Headers({DOMAIN_NAME_HEADER + Api.LOGIN_DOMAIN_NAME})
    @POST(Api.API_REGISTER)
    Observable<BaseResponse> register(@Body() Map<String, Object> params);

    /**
     * 忘记密码
     */
    @Headers({DOMAIN_NAME_HEADER + Api.LOGIN_DOMAIN_NAME})
    @POST(Api.API_MODIFYFORGETPASSWORDBYPHONE)
    Observable<BaseResponse> modifyForgetPasswordByPhone(@Body() Map<String, Object> params);

}
