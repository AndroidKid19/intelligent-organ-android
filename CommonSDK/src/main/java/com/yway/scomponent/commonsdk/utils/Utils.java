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
package com.yway.scomponent.commonsdk.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * ================================================
 * Created by Yuanweiwei on 30/03/2018 17:16
 * <p>
 * ================================================
 */
public class Utils {
    private Utils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    /**
     * 使用 {@link ARouter} 根据 {@code path} 跳转到对应的页面, 这个方法因为没有使用 {@link Activity}跳转
     * 所以 {@link ARouter} 会自动给 {@link android.content.Intent} 加上 Intent.FLAG_ACTIVITY_NEW_TASK
     * 如果不想自动加上这个 Flag 请使用 {@link ARouter#getInstance()#navigation(Context, String)} 并传入 {@link Activity}
     *
     * @param path {@code path}
     */
    public static void navigation(String path) {
        ARouter.getInstance().build(path).navigation();
    }

    /**
     * 使用 {@link ARouter} 根据 {@code path} 跳转到对应的页面, 如果参数 {@code context} 传入的不是 {@link Activity}
     * {@link ARouter} 就会自动给 {@link android.content.Intent} 加上 Intent.FLAG_ACTIVITY_NEW_TASK
     * 如果不想自动加上这个 Flag 请使用 {@link Activity} 作为 {@code context} 传入
     *
     * @param context
     * @param path
     */
    public static void navigation(Context context, String path) {
        ARouter.getInstance().build(path).navigation(context);
    }

    /**
     * 使用 {@link ARouter} 根据 {@code path} 跳转到对应的页面, 如果参数 {@code context} 传入的不是 {@link Activity}
     * {@link ARouter} 就会自动给 {@link android.content.Intent} 加上 Intent.FLAG_ACTIVITY_NEW_TASK
     * 如果不想自动加上这个 Flag 请使用 {@link Activity} 作为 {@code context} 传入
     *
     * @param path
     */
    public static Postcard postcard(String path) {
        return ARouter.getInstance().build(path);
    }


    /**
     * 获取缓存token 校验是否登陆
     */
    public static boolean isLogin() {
        String token = CacheUtils.queryToken();
        return token == null || "".equals(token) ? false : true;
    }

    /**
     * 字符串拼接
     */
    public static String appendStr(Object... param) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < param.length; i++) {
            stringBuilder.append(param[i]);
        }
        return stringBuilder.toString();
    }


    /**
     * 跳转浏览器
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static void goToBrowser(String url, Activity activity) {
        if (!url.equals("")) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            activity.startActivity(i);
        }

    }

    public static String getNonceStr() {
//      Random random = new Random();
//      return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
//              .getBytes());
        final String[] Strarray = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"};
        StringBuffer nonceStr = new StringBuffer();
        for (int i = 0; i < 15; i++) {
            nonceStr.append(Strarray[(int) (Math.random() * Strarray.length)]);
        }
        return nonceStr.toString();
    }

}
