package com.yway.scomponent.organ.mvp.ui.activity;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.base.BaseActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;

import com.jess.arms.utils.ArmsUtils;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.yway.scomponent.commonres.dialog.ProgresDialog;
import com.yway.scomponent.commonres.view.titlebar.OnTitleBarListener;
import com.yway.scomponent.commonres.view.titlebar.TitleBar;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.di.component.DaggerWebViewComponent;
import com.yway.scomponent.organ.mvp.contract.WebViewContract;
import com.yway.scomponent.organ.mvp.presenter.WebViewPresenter;
import com.yway.scomponent.organ.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * webview
 */
@Route(path = RouterHub.HOME_WEBVIEWACTIVITY)
public class WebViewActivity extends BaseActivity<WebViewPresenter> implements WebViewContract.View {
    @BindView(R2.id.layout_webview)
    FrameLayout mLayoutWebView;
    @BindView(R2.id.bar_title)
    TitleBar mTitleBar;
    @BindView(R2.id.tv_push_time)
    AppCompatTextView mPushTime;

    private AgentWeb mAgentWeb;
    private BridgeWebView mBridgeWebView;

    @Autowired(name = RouterHub.PARAM_WEBVIEWXURL)
    String webviewxUrl;

    @Autowired
    int pageFrom;

    @Autowired
    String articleId;

    @Autowired
    String pushTime;

    private String favoritesStatus;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWebViewComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.organ_activity_web_view; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).titleBar(R.id.bar_title).statusBarDarkFont(true).init();
        initWebView();
        if (pageFrom == 2){

            Map<String,Object> paramsMap = new HashMap<>();
            paramsMap.put("articleId",articleId);
            mPresenter.queryUserIsArticleFavorites(paramsMap);

            mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
                @Override
                public void onLeftClick(View v) {

                }

                @Override
                public void onTitleClick(View v) {

                }

                @Override
                public void onRightClick(View v) {
                    Map<String,Object> paramsMap = new HashMap<>();
                    paramsMap.put("articleId",articleId);
                    if (favoritesStatus.equals("1")){
                        mPresenter.cancelArticleFavorites(paramsMap);
                    }else{
                        mPresenter.createArticleFavorites(paramsMap);
                    }
                }
            });
        }

        mPushTime.setText(pushTime);
    }

    /**
     * @description 初始化webview
     * @date: 2020/4/23 23:26
     * @author: YIWUANYUAN
     * @return
     */
    private void initWebView() {
        mBridgeWebView = new BridgeWebView(this);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLayoutWebView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator(-1,2)
                .setWebViewClient(getWebViewClient())
                .setWebChromeClient(mWebChromeClient)
                .setWebView(mBridgeWebView)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
//                .setDownloadListener(mDownloadListener) 4.0.0 删除该API
                .createAgentWeb()//
                .ready()//
                .go(webviewxUrl);
//                .go(new StringBuilder().append(BuildConfig.H5_HOST_ROOT).append(webviewxUrl).toString());
        mAgentWeb.getWebCreator().getWebView().setBackgroundColor(ContextCompat.getColor(this,R.color.white));
//        mBridgeWebView.registerHandler("formatpParameter", new BridgeHandler() {
//            @Override
//            public void handler(String data, CallBackFunction function) {
//                Timber.i(data);
//                function.onCallBack("submitFromWeb exe, response data 中文 from Java");
//            }
//        });
//        Map<String,String> map = new HashMap<>();
//        map.put("dddd","dddddddd");
//        map.put("aaaa","aaaaaaaa");
//        mBridgeWebView.callHandler("functionInJs", new Gson().toJson(map), new CallBackFunction() {
//            @Override
//            public void onCallBack(String data) {
//                Timber.i("data:" + data);
//            }
//        });
//
//        mBridgeWebView.send("发送一个消息");
    }

    private WebViewClient getWebViewClient(){
        return new WebViewClient() {
            BridgeWebViewClient mBridgeWebViewClient = new BridgeWebViewClient(mBridgeWebView);

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return mBridgeWebViewClient.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mBridgeWebViewClient.onPageFinished(view, url);
            }

        };
    }

    protected WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //解决进度条不消失问题，不要去掉super.onProgressChanged(view, newProgress);
              super.onProgressChanged(view, newProgress);
            Log.i(TAG, "onProgressChanged:" + newProgress + "  view:" + view);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
//            mTitleBar.setTitle(title);
        }
    };
    /**
     * 收藏
     * */
    @Override
    public void createArticleFavoritesCallBack() {
        mTitleBar.setRightIcon(getDrawable(R.mipmap.public_ic_collect_ed));
    }
    /**
     * 文章收藏状态；
     * 1代表文章已收藏
     * 0代表文章未收藏
     */
    @Override
    public void isFavoritesCallBack(String favoritesStatus) {
        this.favoritesStatus = favoritesStatus;
        if (favoritesStatus.equals("1")){
            mTitleBar.setRightIcon(getDrawable(R.mipmap.public_ic_collect_ed));
        }else{
            mTitleBar.setRightIcon(getDrawable(R.mipmap.public_ic_collect));
        }
    }

    /**
     * 取消收藏
     * */
    @Override
    public void cancelArticleFavoritesCallBack() {
        mTitleBar.setRightIcon(getDrawable(R.mipmap.public_ic_collect));
    }

    @Override
    public void showLoading() {
        ProgresDialog.getInstance(this).show();
    }

    @Override
    public void hideLoading() {
        ProgresDialog.getInstance(this).dismissDialog();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();

    }
    public Activity getActivity() {
        return this;
    }
}