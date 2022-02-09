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

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.module.ClientModule;
import com.jess.arms.di.module.GlobalConfigModule;
import com.jess.arms.http.log.FormatPrinter;
import com.jess.arms.http.log.RequestInterceptor;
import com.jess.arms.integration.ConfigModule;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mmkv.MMKV;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.yway.scomponent.commonsdk.BuildConfig;
import com.yway.scomponent.commonsdk.http.Api;
import com.yway.scomponent.commonsdk.http.SSLSocketClient;
import com.yway.scomponent.commonsdk.imgaEngine.Strategy.CommonGlideImageLoaderStrategy;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import timber.log.Timber;


/**
 * ================================================
 * CommonSDK 的 GlobalConfiguration 含有有每个组件都可公用的配置信息, 每个组件的 AndroidManifest 都应该声明此 ConfigModule
 * ================================================
 */
public class GlobalConfiguration implements ConfigModule {
    private static final int TIME_OUT = 60;

    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        if (!BuildConfig.LOG_DEBUG) //Release 时,让框架不再打印 Http 请求和响应的信息
            builder.printHttpLogLevel(RequestInterceptor.Level.NONE);
        builder.baseurl(Api.Domain.APP_DOMAIN)
//                .formatPrinter(new FormatPrinter() {
//                    @Override
//                    public void printJsonRequest(@NonNull @NotNull Request request, @NonNull @NotNull String bodyString) {
//                        Timber.i("┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
//                        Timber.i("│printJsonRequest:" + bodyString);
//                        Timber.i("└────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
//                    }
//
//                    @Override
//                    public void printFileRequest(@NonNull @NotNull Request request) {
//                        Timber.i("printFileRequest:" + request.url().toString());
//                    }
//
//                    @Override
//                    public void printJsonResponse(long chainMs, boolean isSuccessful, int code, @NonNull @NotNull String headers, @Nullable @org.jetbrains.annotations.Nullable MediaType contentType, @Nullable @org.jetbrains.annotations.Nullable String bodyString, @NonNull @NotNull List<String> segments, @NonNull @NotNull String message, @NonNull @NotNull String responseUrl) {
////                        LogUtils.json(bodyString);
//                        Timber.i("printJsonResponse:" + bodyString);
//                    }
//
//                    @Override
//                    public void printFileResponse(long chainMs, boolean isSuccessful, int code, @NonNull @NotNull String headers, @NonNull @NotNull List<String> segments, @NonNull @NotNull String message, @NonNull @NotNull String responseUrl) {
//                        Timber.i("printFileResponse:" + responseUrl);
//                    }
//                })
                .imageLoaderStrategy(new CommonGlideImageLoaderStrategy())
                .globalHttpHandler(new GlobalHttpHandlerImpl(context))
                .responseErrorListener(new ResponseErrorListenerImpl())
                .gsonConfiguration((context1, gsonBuilder) -> {//这里可以自己自定义配置Gson的参数
                    gsonBuilder
                            .serializeNulls()//支持序列化null的参数
                            .enableComplexMapKeySerialization();//支持将序列化key为object的map,默认只能序列化key为string的map
                })
                .okhttpConfiguration(new ClientModule.OkhttpConfiguration() {
                    @Override
                    public void configOkhttp(Context context, OkHttpClient.Builder builder) {
                        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                                .writeTimeout(TIME_OUT, TimeUnit.SECONDS);
                        builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getTrustManager());
                        builder.hostnameVerifier(SSLSocketClient.getHostnameVerifier());
                        //让 Retrofit 同时支持多个 BaseUrl 以及动态改变 BaseUrl. 详细使用请方法查看 https://github.com/JessYanCoding/RetrofitUrlManager
                        RetrofitUrlManager.getInstance().with(builder);
                    }
                })
                .rxCacheConfiguration((context1, rxCacheBuilder) -> {//这里可以自己自定义配置RxCache的参数
                    rxCacheBuilder.useExpiredDataIfLoaderNotAvailable(true);
                    return null;
                });
    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        // AppDelegate.Lifecycle 的所有方法都会在基类Application对应的生命周期中被调用,所以在对应的方法中可以扩展一些自己需要的逻辑
        lifecycles.add(new AppLifecycles() {

            @Override
            public void attachBaseContext(@NonNull Context base) {
            }

            @Override
            public void onCreate(@NonNull Application application) {
                if (BuildConfig.LOG_DEBUG) {//Timber日志打印
                    Timber.plant(new Timber.DebugTree());
                    ButterKnife.setDebug(true);
                    ARouter.openLog();     // 打印日志
                    ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
                    RetrofitUrlManager.getInstance().setDebug(true);
                }
                ARouter.init(application); // 尽可能早,推荐在Application中初始化

                //设置全局的Header构建器
                SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
                    //开始设置全局的基本参数（这里设置的属性只跟下面的MaterialHeader绑定，其他Header不会生效，能覆盖DefaultRefreshInitializer的属性和Xml设置的属性）
                    layout.setEnableHeaderTranslationContent(false);
                    layout.setEnableAutoLoadMore(true);
                    layout.setEnablePureScrollMode(false);
                    layout.setEnableOverScrollBounce(true);//是否启用越界回弹
                    return new MaterialHeader(context);
                });
                SmartRefreshLayout.setDefaultRefreshFooterCreator((context1, layout) -> {
                    layout.setEnableOverScrollBounce(true);//是否启用越界回弹
                    layout.setEnableAutoLoadMore(true);
                    layout.setEnableScrollContentWhenLoaded(true);
                    layout.setEnableLoadMoreWhenContentNotFull(true);
                    layout.setEnableFooterTranslationContent(true);//是否上拉Footer的时候向上平移列表或者内容
                    ClassicsFooter classicsFooter = new ClassicsFooter(context1).setSpinnerStyle(SpinnerStyle.Translate);
                    return classicsFooter;
                });

                //------------------增加上报进程控制
                // 获取当前包名
                String packageName = application.getPackageName();
                // 获取当前进程名
                String processName = getProcessName(android.os.Process.myPid());
                // 设置是否为上报进程
                CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
                strategy.setUploadProcess(processName == null || processName.equals(packageName));
                // 初始化Bugly
                //第三个参数为SDK调试模式开关，测试阶段建议设置成true，发布时设置为false。
                CrashReport.initCrashReport(application, "beff630402", BuildConfig.LOG_DEBUG);
                //初始化MMKV
                String rootDir = MMKV.initialize(application);
                Timber.i("mmkv root: " + rootDir);
                /**
                 * 注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调
                 * 用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，
                 * UMConfigure.init调用中appkey和channel参数请置为null）。
                 */
                UMConfigure.init(application, "5ed636b2978eea08e1ba90da", "miemiemei", UMConfigure.DEVICE_TYPE_PHONE, null);
                PlatformConfig.setWeixin("wxf016b2637aff1bf3", "1d0bd457a39e7d0b38d2bb03d582f8ae");
                PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");

            }

            @Override
            public void onTerminate(@NonNull Application application) {

            }
        });
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
        lifecycles.add(new ActivityLifecycleCallbacksImpl());
    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        lifecycles.add(new FragmentLifecycleCallbacksImpl());
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
