package com.yway.scomponent.commonsdk.utils;

import android.content.Context;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.tencent.mmkv.MMKV;
import com.yway.scomponent.commonsdk.core.Constants;
import com.yway.scomponent.commonsdk.core.DictClassifyBean;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.core.UserInfoBean;

import static com.yway.scomponent.commonsdk.core.Constants.APP_COMMON_DICT;
import static com.yway.scomponent.commonsdk.core.Constants.APP_USER_ID;
import static com.yway.scomponent.commonsdk.core.Constants.APP_USER_INFO;
import static com.yway.scomponent.commonsdk.core.Constants.USER_TOKEN;

/**
 * @ProjectName: Android-Component-master
 * @Package: com.yway.scomponent.commonsdk.utils
 * @ClassName: CacheUtils
 * @Description: 缓存
 * @Author: YIWUANYUAN
 * @Version: 1.0
 */
public class CacheUtils {

    private CacheUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }


    /**
     * 获取MMKV
     */
    public static MMKV initMMKVOther() {
        return MMKV.mmkvWithID("other");
    }

    public static MMKV initMMKV() {
        return MMKV.mmkvWithID("organ_user");
    }

    /**
     * 获取个人信息
     */
    public static UserInfoBean queryUserInfo() {
        return initMMKV().decodeParcelable(APP_USER_INFO, UserInfoBean.class);
    }

    /**
     * 获取个人用户昵称+手机号后4位
     */
    public static String queryUserNameAddPhone() {
        UserInfoBean userInfoBean = queryUserInfo();
        if (StringUtils.isEmpty(userInfoBean.getName())) return "榆阳智云 0001";
        return Utils.appendStr(userInfoBean.getName(), "  ", userInfoBean.getCellPhone().substring(7, 11));
    }

    /**
     * 获取个人手机号
     */
    public static String queryCellPhone() {
        UserInfoBean userInfoBean = queryUserInfo();
        return userInfoBean.getCellPhone();
    }

    /**
     * 缓存个人信息
     */
    public static void cacheIsUserInfo(UserInfoBean userInfoBean) {
        initMMKV().encode(APP_USER_INFO, userInfoBean);
    }

    /**
     * 获取个人id
     */
    public static int queryDoctorId() {
        return initMMKV().decodeInt(APP_USER_ID);
    }



    /**
     * 获取字典
     */
    public static DictClassifyBean queryDictData() {
        return initMMKV().decodeParcelable(APP_COMMON_DICT,DictClassifyBean.class);
    }

    /**
     * 获取个人名称
     */
    public static String queryName() {
        //获取个人信息
        UserInfoBean userInfoBean = initMMKV().decodeParcelable(APP_USER_INFO, UserInfoBean.class);
        if (ObjectUtils.isEmpty(userInfoBean)) return "";
        //获取当前名称
        String name = userInfoBean.getName();
        //获取当前昵称
        String nickName = "";
        if (!StringUtils.isEmpty(userInfoBean.getNickName())) {
            nickName = userInfoBean.getCellPhone().substring(userInfoBean.getCellPhone().length() - 4);
        }
        return StringUtils.isEmpty(name) ? nickName : name;
    }

    /**
     * 获取个人昵称
     */
    public static String queryNickName() {
        //获取个人信息
        UserInfoBean userInfoBean = initMMKV().decodeParcelable(APP_USER_INFO, UserInfoBean.class);
        if (ObjectUtils.isEmpty(userInfoBean)) return "";
        //获取当前昵称
        String nickName = userInfoBean.getNickName();
        //获取当前手机号码后四位
        String phone4 = "";
        if (!StringUtils.isEmpty(userInfoBean.getCellPhone())) {
            phone4 = userInfoBean.getCellPhone().substring(userInfoBean.getCellPhone().length() - 4);
        }
        return StringUtils.isEmpty(nickName) ? phone4 : nickName;
    }

    /**
     * 获取实名认证状态
     */
    public static int queryCertifiedStatus() {
        //获取个人信息
        UserInfoBean userInfoBean = initMMKV().decodeParcelable(APP_USER_INFO, UserInfoBean.class);
        if (ObjectUtils.isEmpty(userInfoBean)) return 0;
        //认证状态
        //certifiedStatus：认证状态：0待提交认证，1已提交认证（认证中），2认证已通过，3认证被驳回
        int certifiedStatus = userInfoBean.getCertifiedStatus();
        return certifiedStatus;
    }

    /**
     * 是否实名认证
     */
    public static boolean isAuthUser() {
        //获取个人信息
        UserInfoBean userInfoBean = initMMKV().decodeParcelable(APP_USER_INFO, UserInfoBean.class);
        if (ObjectUtils.isEmpty(userInfoBean)) return false;
        //认证状态
        //certifiedStatus：认证状态：0待提交认证，1已提交认证（认证中），2认证已通过，3认证被驳回
        int certifiedStatus = userInfoBean.getCertifiedStatus();
        if (certifiedStatus == 2)
            return true;
        else
            return false;
    }

    /**
     * 缓存Token
     */
    public static void cacheToken(String token) {
        initMMKV().encode(USER_TOKEN, token);
    }

    /**
     * 获取token
     */
    public static String queryToken() {
        return initMMKV().decodeString(USER_TOKEN);
    }


    /**
     * 清空缓存
     */
    public static void clearCache(Context context) {
        initMMKV().clearAll();
        CacheDataManager.clearAllCache(context);
    }
}
