
package com.yway.scomponent.user.mvp.model.api;

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
    String USER_DOMAIN_NAME = "wheelapi";
    String  API_USER = "/";

    //发送验证码
    String  API_SEND_CODE = "organ/sendSms";

    //修改当前手机号
    String  API_MODIFYCELLPHONEBYUSERID = "organ/modifyCellPhoneByUserId";

    //修改密码
    String  API_MODIFYPASSWORD = "organ/modifyPassword";

    //上传图片
    String API_HOME_UPLOAD_FILE = "/organ/fileUpload/upload";

    //用户认证
    String API_MODIFYAPPUSERINFOBYID = "/organ/modifyAppUserInfoById";

    //个人信息查询
    String API_QUERYLOGINUSERINFOBYID = "/organ/queryloginUserInfoById";

    //查询收藏
    String API_QUERYARTICLEFAVORITESPAGELIST = "/organ/articleFavorites/queryArticleFavoritesPageList";



    //取消收藏
    String API_CANCELARTICLEFAVORITES = "/organ/articleFavorites/cancelArticleFavorites";



    //通讯录查询
    String API_QUERYALLSYSORGANDSYSUSERLIST = "/organ/org/queryAllSysOrgAndSysUserList";
}
