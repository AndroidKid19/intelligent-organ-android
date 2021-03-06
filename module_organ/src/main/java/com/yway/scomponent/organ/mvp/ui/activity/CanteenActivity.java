package com.yway.scomponent.organ.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.LogUtils;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xgr.easypay.EasyPay;
import com.xgr.easypay.callback.IPayCallback;
import com.xgr.wechatpay.wxpay.WXPay;
import com.xgr.wechatpay.wxpay.WXPayInfoImpli;
import com.yway.scomponent.commonres.dialog.IToast;
import com.yway.scomponent.commonres.dialog.ProgresDialog;
import com.yway.scomponent.commonres.view.layout.MultipleStatusView;
import com.yway.scomponent.commonres.view.titlebar.OnTitleBarListener;
import com.yway.scomponent.commonres.view.titlebar.TitleBar;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.utils.ArithUtils;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.di.component.DaggerCanteenComponent;
import com.yway.scomponent.organ.mvp.contract.CanteenContract;
import com.yway.scomponent.organ.mvp.model.entity.PayDetailsBean;
import com.yway.scomponent.organ.mvp.model.entity.RechargeRecordBean;
import com.yway.scomponent.organ.mvp.presenter.CanteenPresenter;
import com.yway.scomponent.organ.mvp.ui.adapter.RechargeRecordAdapter;
import com.yway.scomponent.organ.mvp.ui.dialog.RechargeDialog;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * ????????????
 */
@Route(path = RouterHub.HOME_CANTEENACTIVITY)
public class CanteenActivity extends BaseActivity<CanteenPresenter> implements CanteenContract.View {
    /**
     * title
     */
    @BindView(R2.id.bar_title)
    TitleBar mBarTitle;
    /**
     * ?????????view
     */
    @BindView(R2.id.multiple_layout)
    MultipleStatusView mMultipleStatusView;
    /**
     * ???????????????????????????View
     */
    @BindView(R2.id.refresh_layout)
    RefreshLayout mRefreshLayout;
    /**
     * RecycleView
     **/
    @BindView(R2.id.recycle_view)
    RecyclerView mRecyclerView;

    @BindView(R2.id.tv_card_number)
    AppCompatTextView mTvCardNumber;

    @BindView(R2.id.tv_balance)
    AppCompatTextView mTvBalance;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RechargeRecordAdapter mAdapter;
    /**
     * ????????????
     **/
    private Map<String, Object> paramMap = new HashMap<>();

    /**
     * ?????????
     **/
    private SkeletonScreen mSkeletonScreen;

    /**
     * ???????????????
     */
    private double amount = 0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCanteenComponent //??????????????????,?????????????????????
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.organ_activity_canteen; //???????????????????????????????????? setContentView(id) ??????????????????,????????? 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).titleBar(R.id.bar_title).init();

        initRecyclerView();
        //??????????????????
        initSkeletonScreen();
        mBarTitle.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {

            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                Utils.postcard(RouterHub.HOME_TRANSACTIONRECORDACTIVITY)
                        .withInt("formPage", 1)
                        .navigation(getActivity());
            }
        });
    }

    /**
     * ?????????RecyclerView
     */
    private void initRecyclerView() {
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //????????????????????????
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mRefreshLayout.setOnLoadMoreListener(mOnLoadMoreListener);

    }

    /**
     * ??????
     * transactionType  ???????????????0?????????1?????????2??????
     * transactionPayType ?????????????????????0?????????1????????????2?????????
     * transactionAmount ????????????
     * transactionId ??????id???????????????/???????????????id
     * outTradeNo ???????????????
     * transactionStatus ???????????? 0?????????1??????
     */
    @OnClick(R2.id.btn_opt_adopt)
    void onVisitorRecordClick(View view) {
        new RechargeDialog.Builder()
                .setOnRechargeListener(amount -> {
                    this.amount = amount;
                    Map<String, Object> paramsMap = new HashMap<>();
                    paramsMap.put("id", 1);
                    paramsMap.put("type", 1);
                    paramsMap.put("money", amount);
                    mPresenter.wxPay(paramsMap);
                })
                .showPopupWindow();

    }

    /***
     * ????????????
     * */
    private void wxpay(String sign, String prepayId, String partnerid, String appid, String timestamp, String noncestr, String outTradeNo) {
        //???????????????????????????
        WXPay wxPay = WXPay.getInstance();
        //??????????????????????????????????????????????????????????????????
        WXPayInfoImpli wxPayInfoImpli = new WXPayInfoImpli();
        wxPayInfoImpli.setTimestamp(timestamp);
        //?????? ????????????
        wxPayInfoImpli.setSign(sign);
        //?????????????????????ID
        wxPayInfoImpli.setPrepayId(prepayId);
        //?????????
        wxPayInfoImpli.setPartnerid(partnerid);
        //APPID
        wxPayInfoImpli.setAppid(appid);
        //???????????????
        wxPayInfoImpli.setNonceStr(noncestr);
        //????????????
        wxPayInfoImpli.setPackageValue("Sign=WXPay");
        LogUtils.json(wxPayInfoImpli);
        //?????????????????????????????????????????????????????????????????????
        EasyPay.pay(wxPay, this, wxPayInfoImpli, new IPayCallback() {
            @Override
            public void success() {
                createAccountTransactionRecord(outTradeNo, partnerid, 0);
                IToast.showFinishShort("????????????");
            }

            @Override
            public void failed(int code, String message) {
                createAccountTransactionRecord(outTradeNo, partnerid, 1);
                IToast.showFinishShort(Utils.appendStr(code, "-", message));
            }

            @Override
            public void cancel() {
                createAccountTransactionRecord(outTradeNo, partnerid, 1);
                IToast.showFinishShort("????????????");
            }
        });
    }

    /**
     * transactionType ???????????????0?????????1?????????2??????
     * transactionPayType ?????????????????????0?????????1????????????2?????????
     * transactionAmount ????????????
     * transactionId ??????id???????????????/???????????????id
     * outTradeNo ???????????????
     * transactionStatus ???????????? 0?????????1??????
     */
    private void createAccountTransactionRecord(String outTradeNo, String transactionId, int transactionStatus) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("transactionType", 1);
        paramsMap.put("transactionPayType", 0);
        paramsMap.put("transactionAmount", ArithUtils.mul(this.amount, 10000));
        paramsMap.put("transactionId", transactionId);
        paramsMap.put("outTradeNo", outTradeNo);
        paramsMap.put("transactionStatus", transactionStatus);
        mPresenter.createAccountTransactionRecord(paramsMap);
    }

    /**
     * @description : TODO ??????????????????
     * @author : yuanweiwei
     */
    private OnRefreshListener mOnRefreshListener = refreshLayout -> {
        mPresenter.queryPayRecordPageList(paramMap, true);
        mPresenter.queryByUserAccount();
    };

    /**
     * @description : TODO ??????????????????
     * @author : yuanweiwei
     */
    private OnLoadMoreListener mOnLoadMoreListener = refreshLayout -> {
        mPresenter.queryPayRecordPageList(paramMap, false);
    };

    /**
     * @return
     * @description ??????????????????
     * @date: 2020/8/10 16:56
     * @author: YIWUANYUAN
     */
    private void initSkeletonScreen() {
        //???????????????
        mPresenter.queryPayRecordPageList(paramMap, true);
        mPresenter.queryByUserAccount();
        mSkeletonScreen = Skeleton.bind(mRecyclerView)
                .adapter(mAdapter)
                .shimmer(true)
                .angle(20)
                .frozen(false)
                .duration(1200)
                .count(15)
                .load(R.layout.organ_item_skeleton_metting)
                .show(); //default count is 10
    }

    /**
     * ????????????
     */
    @OnClick({R2.id.tv_more})
    void onBtnMore(View view) {
        Utils.postcard(RouterHub.HOME_TRANSACTIONRECORDACTIVITY)
                .withInt("formPage", 0)
                .navigation(this);
    }


    @Override
    public void showLoading() {
        ProgresDialog.getInstance(getActivity()).show();
    }

    @Override
    public void hideLoading() {
        ProgresDialog.getInstance(getActivity()).dismissDialog();
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

    public Activity getActivity() {
        return this;
    }


    @Override
    public SkeletonScreen skeletonScreen() {
        return mSkeletonScreen;
    }

    @Override
    public MultipleStatusView multipleStatusView() {
        return mMultipleStatusView;
    }

    @Override
    public RefreshLayout refreshLayout() {
        return mRefreshLayout;
    }

    @Override
    public void queryAccountCallBack(RechargeRecordBean data) {
        mTvCardNumber.setText(data.getAccount());
        mTvBalance.setText(Utils.appendStr(ArithUtils.div(Double.parseDouble(data.getAccountBalance()), 10000)));


    }

    @Override
    public void paymentCallBack() {
        //????????????
        mPresenter.queryPayRecordPageList(paramMap, true);
        mPresenter.queryByUserAccount();
        //??????
        IToast.showFinishShort("??????????????????");
    }

    /***
     * ??????????????????
     * */
    @Override
    public void unifiedorderCallBack(PayDetailsBean data) {
        //??????????????????
        wxpay(data.getSign(), data.getPrepayid(), data.getPartnerid(), data.getAppid(), data.getTimestamp(), data.getNoncestr(), data.getOutTradeNo());
    }
}