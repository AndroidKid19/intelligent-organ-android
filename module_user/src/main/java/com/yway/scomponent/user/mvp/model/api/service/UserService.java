
package com.yway.scomponent.user.mvp.model.api.service;

import com.yway.scomponent.commonsdk.core.AddressCompanyBean;
import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.commonsdk.core.UploadFileBean;
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.user.mvp.model.api.Api;
import com.yway.scomponent.user.mvp.model.entity.ArticleBean;
import com.yway.scomponent.user.mvp.model.entity.MessageBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;


/**
 * ================================================
 * 展示 {@link Retrofit#create(Class)} 中需要传入的 ApiService 的使用方式
 * 存放关于 zhihu 的一些 API
 * <p>
 * Created by AndroidKid19 on 08/05/2016 12:05
 * <a href="mailto:yuanw9@sina.com">Contact me</a>
 * <a href="https://github.com/AndroidKid19Coding">Follow me</a>
 * ================================================
 */
public interface UserService {


    /**
     * 查询用户信息
     */
    @Headers({DOMAIN_NAME_HEADER + Api.USER_DOMAIN_NAME})
    @POST(Api.API_USER)
    Observable<BaseResponse<UserInfoBean>> queryDoctorInfo(@Body() Map<String,Object> params);

    /**
     * 修改密码
     */
    @Headers({DOMAIN_NAME_HEADER + Api.USER_DOMAIN_NAME})
    @POST(Api.API_MODIFYPASSWORD)
    Observable<BaseResponse> modifyPwd(@Body() Map<String,Object> params);

    /**
     * 发送验证码
     */
    @Headers({DOMAIN_NAME_HEADER + Api.USER_DOMAIN_NAME})
    @GET(Api.API_SEND_CODE)
    Observable<BaseResponse> sendSms(@QueryMap() Map<String,Object> params);

    /**
     * 修改手机号
     */
    @Headers({DOMAIN_NAME_HEADER + Api.USER_DOMAIN_NAME})
    @POST(Api.API_MODIFYCELLPHONEBYUSERID)
    Observable<BaseResponse> modifyCellPhoneByUserId(@Body() Map<String,Object> params);

    /**
     * 获取系统消息接口
     */
    @Headers({DOMAIN_NAME_HEADER + Api.USER_DOMAIN_NAME})
    @POST(Api.API_USER)
    Observable<BaseResponse<List<MessageBean>>> queryMessage(@Body() Map<String,Object> params);

    /**
     * 上传图片
     */
    @Headers({DOMAIN_NAME_HEADER + Api.USER_DOMAIN_NAME})
    @POST(Api.API_USER)
    @Multipart
    Observable<BaseResponse<UploadFileBean>> uploadPicture(@Part MultipartBody.Part file);

    /**
     * 多图上传
     */
    @Headers({DOMAIN_NAME_HEADER +  Api.USER_DOMAIN_NAME})
    @POST(Api.API_HOME_UPLOAD_FILE)
    Observable<BaseResponse<UploadFileBean>> uploadPictureMore(@Body MultipartBody files);


    /**
     * 认证
     */
    @Headers({DOMAIN_NAME_HEADER + Api.USER_DOMAIN_NAME})
    @POST(Api.API_MODIFYAPPUSERINFOBYID)
    Observable<BaseResponse> modifyAppUserInfoById(@Body() Map<String,Object> params);

    /**
     * 个人信息查询
     */
    @Headers({DOMAIN_NAME_HEADER +  Api.USER_DOMAIN_NAME})
    @POST(Api.API_QUERYLOGINUSERINFOBYID)
    Observable<BaseResponse<UserInfoBean>> queryloginUserInfoById(@Body() Map<String,Object> params);

    /**
     * 查询收藏
     */
    @Headers({DOMAIN_NAME_HEADER +  Api.USER_DOMAIN_NAME})
    @POST(Api.API_QUERYARTICLEFAVORITESPAGELIST)
    Observable<BaseResponse<ArticleBean>> queryArticleFavoritesPageList(@Body() Map<String,Object> params);

    /**
     * 取消收藏
     */
    @Headers({DOMAIN_NAME_HEADER + Api.USER_DOMAIN_NAME})
    @POST(Api.API_CANCELARTICLEFAVORITES)
    Observable<BaseResponse> cancelArticleFavorites(@Body() Map<String, Object> params);

    /**
     * 通讯录查询
     */
    @Headers({DOMAIN_NAME_HEADER + Api.USER_DOMAIN_NAME})
    @POST(Api.API_QUERYALLSYSORGANDSYSUSERLIST)
    Observable<BaseResponse<AddressCompanyBean>> queryAllSysOrgAndSysUserList(@Body() Map<String, Object> params);
}
