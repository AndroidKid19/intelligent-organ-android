package com.yway.scomponent.yyzy.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.yway.scomponent.commonres.dialog.ProgresDialog;
import com.yway.scomponent.commonres.view.titlebar.OnTitleBarListener;
import com.yway.scomponent.commonres.view.titlebar.TitleBar;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.yyzy.R;
import com.yway.scomponent.yyzy.di.component.DaggerWebViewComponent;
import com.yway.scomponent.yyzy.mvp.contract.WebViewContract;
import com.yway.scomponent.yyzy.mvp.presenter.WebViewPresenter;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created on 07/29/2020 12:07
 * ================================================
 */
@Route(path = RouterHub.APP_AGENTWEBACTIVITY)
public class WebViewActivity extends BaseActivity<WebViewPresenter> implements WebViewContract.View {
//    @BindView(R.id.web_view)
//    WebView webView;
    @BindView(R.id.layout_webview)
    FrameLayout mLayoutWebView;
    @BindView(R.id.bar_title)
    TitleBar mTitleBar;
    private AgentWeb mAgentWeb;
    private BridgeWebView mBridgeWebView;

    @Autowired(name = RouterHub.PARAM_WEBVIEWXURL)
    String webviewxUrl;

    @Autowired
    int pageFrom;

    @Autowired
    String articleId;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWebViewComponent //??????????????????,?????????????????????
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        return R.layout.activity_web_view; //???????????????????????????????????? setContentView(id) ??????????????????,????????? 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).titleBar(R.id.bar_title).statusBarDarkFont(true).init();
        initWebView();
        if (pageFrom == 2){
            mTitleBar.setRightIcon(getDrawable(R.mipmap.public_ic_collect));
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
                    mPresenter.createArticleFavorites(paramsMap);
                }
            });
        }

    }

    /**
     * @description ?????????webview
     * @date: 2020/4/23 23:26
     * @author: YIWUANYUAN
     * @return
     */
    private void initWebView() {
        mBridgeWebView = new BridgeWebView(this);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLayoutWebView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator(-1, 2)
                .setWebViewClient(getWebViewClient())
                .setWebChromeClient(mWebChromeClient)
                .setWebView(mBridgeWebView)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
//                .setDownloadListener(mDownloadListener) 4.0.0 ?????????API
                .createAgentWeb()//
                .ready()//
                .go(webviewxUrl);
//                .go(new StringBuilder().append(BuildConfig.H5_HOST_ROOT).append(webviewxUrl).toString());
        mAgentWeb.getWebCreator().getWebView().setBackgroundColor(ContextCompat.getColor(this,R.color.white));
//        mBridgeWebView.registerHandler("formatpParameter", new BridgeHandler() {
//            @Override
//            public void handler(String data, CallBackFunction function) {
//                Timber.i(data);
//                function.onCallBack("submitFromWeb exe, response data ?????? from Java");
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
//        mBridgeWebView.send("??????????????????");
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
            //  super.onProgressChanged(view, newProgress);
            Log.i(TAG, "onProgressChanged:" + newProgress + "  view:" + view);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            mTitleBar.setTitle(title);
        }
    };
    /**
     * ??????
     * */
    @Override
    public void createArticleFavoritesCallBack() {

    }

    /**
     * ????????????
     * */
    @Override
    public void cancelArticleFavoritesCallBack() {

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
        finish();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();

    }


}
