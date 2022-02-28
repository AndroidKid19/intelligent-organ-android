package com.yway.scomponent.commonsdk.utils;

import com.blankj.utilcode.util.RegexUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ProjectName: miemiemie-android
 * @Package: com.fwzx.scomponent.commonsdk.utils
 * @ClassName: SystemUtils
 * @Description:
 * @Author: Yuan
 * @CreateDate: 2020/12/5 17:45
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/12/5 17:45
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SystemUtils {
    private static final String TAG = "SystemUtils";

    /**
     * 解析出url参数中的键值对（android 获取url中的参数）
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     */
    public static Map URLRequest(String URL) {
        Map mapRequest = new HashMap();
        String[] arrSplit = null;
        String strUrlParam = TruncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        //每个键值为一组 www.2cto.com
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");

            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            } else {
                if (arrSplitEqual[0] != "") {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String TruncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit = null;
        strURL = strURL.trim();
        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }

        return strAllParam;
    }

    /**
     * 验证是否是URL
     * @param url
     * @return
     */
    public static boolean verifyUrl(String url){
        // URL验证规则
        String regEx ="[a-zA-z]+://[^\\s]*";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(url);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.matches();
        return rs;

    }

    /**
     * @description 解析二维码
     * http://8.141.167.159:8000/?transactionType=0
     * @param  * @param result
     * @return java.lang.String
     */
    public static String getTransactionType(String result){
        //校验是否为URL
        if (!RegexUtils.isURL(result))return "";
        //获取二维码参数
        String paramId = (String) SystemUtils.URLRequest(result).get("transactionType");
        return paramId;
    }


}
