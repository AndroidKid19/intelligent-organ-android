package com.yway.scomponent.commonsdk.core;

import com.blankj.utilcode.util.AppUtils;
import com.yway.scomponent.commonsdk.utils.CacheUtils;
import com.yway.scomponent.commonsdk.utils.MD5Util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @ProjectName: ITOC-Android
 * @Package: com.fatalsignal.outsourcing.commonsdk.core
 * @ClassName: ParamsHelper
 * @Description:
 * @Author: Yuanweiwei
 * @CreateDate: 2019/9/23 17:32
 * @UpdateUser:
 * @UpdateDate: 2019/9/23 17:32
 * @UpdateRemark:
 * @Version: 1.0
 */
public class ParamsHelper {
    private static String SIGN_TOKEN = "drZPEVDLg1QVdlv2";

    private static Map<String,String> mCommonParams(){
        Map params =new HashMap<String, String>();
        params.put("token", CacheUtils.queryToken());
//        params.put("version", String.valueOf(AppUtils.getAppVersionCode()));
//        params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000L));
//        params.put("deviceType","android");
        return params;
    }

    public static TreeMap<String,String> getCommonParams(){
        TreeMap<String,String> params = new TreeMap<String, String>();
        params.putAll(mCommonParams());
        return params;
    }


    public static Map<String,String> encryptSign(Map<String,String> treeMap){
        //04 拼接字符串
        StringBuilder signsParams = new StringBuilder();
        signsParams.append(SIGN_TOKEN);
        Iterator<Map.Entry<String,String>> iterator = treeMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> param = iterator.next();
            signsParams.append(param.getValue());
            if (iterator.hasNext()){
                signsParams.append("|");
            }
        }
        signsParams.append(SIGN_TOKEN);
        String sign = MD5Util.MD5Encode(signsParams.toString().getBytes());
        //05添加signs 参数
        treeMap.put("sign",sign);
        return  treeMap;
    }

}
