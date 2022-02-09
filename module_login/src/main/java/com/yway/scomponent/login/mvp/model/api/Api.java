
package com.yway.scomponent.login.mvp.model.api;

/**
 * ================================================
 * 存放一些与 API 有关的东西,如请求地址,请求码等
 * <p>
 * Created by AndroidKid19 on 08/05/2016 11:25
 * <a href="mailto:yuanw9@sina.com">Contact me</a>
 * <a href="https://github.com/AndroidKid19Coding">Follow me</a>
 * ================================================
 */
public interface Api {
    String LOGIN_DOMAIN_NAME = "wheelapi";
    String GANK_DOMAIN = "http://gank.io";

    //登录
    String API_LOGIN = "organ/mobileLogin";


    String  API_PFX = "util/pfx";

    //发送验证码
    String  API_SEND_CODE = "organ/sendSms";

    //注册
    String  API_REGISTER = "organ/register";

    //忘记密码
    String  API_MODIFYFORGETPASSWORDBYPHONE = "organ/modifyForgetPasswordByPhone";


}
