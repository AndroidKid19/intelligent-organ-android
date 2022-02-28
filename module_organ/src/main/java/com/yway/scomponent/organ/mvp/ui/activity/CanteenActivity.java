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
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yway.scomponent.commonres.dialog.IToast;
import com.yway.scomponent.commonres.dialog.MessageDialog;
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
 * 健康食堂
 */
@Route(path = RouterHub.HOME_CANTEENACTIVITY)
public class CanteenActivity extends BaseActivity<CanteenPresenter> implements CanteenContract.View {
    /**
     * title
     */
    @BindView(R2.id.bar_title)
    TitleBar mBarTitle;
    /**
     * 多状态view
     */
    @BindView(R2.id.multiple_layout)
    MultipleStatusView mMultipleStatusView;
    /**
     * 下拉刷新，上拉加载View
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
     * 查询参数
     **/
    private Map<String, Object> paramMap = new HashMap<>();

    /**
     * 骨架屏
     **/
    private SkeletonScreen mSkeletonScreen;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCanteenComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.organ_activity_canteen; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).titleBar(R.id.bar_title).init();

        initRecyclerView();
        //初始化骨架屏
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
                        .withInt("formPage",1)
                        .navigation(getActivity());
            }
        });
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //设置下拉刷新监听
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mRefreshLayout.setOnLoadMoreListener(mOnLoadMoreListener);

    }

    /**
     * 充值
     * transactionType  交易类型：0消费；1充值；2提现
     * transactionPayType 交易支付类型：0微信；1支付宝；2银行卡
     * transactionAmount 交易金额
     * transactionId 交易id：对应微信/支付宝交易id
     * outTradeNo 商户订单号
     * transactionStatus 交易状态 0成功　1失败
     * */
    @OnClick(R2.id.btn_opt_adopt)
    void onVisitorRecordClick(View view){
        new RechargeDialog.Builder()
                .setOnRechargeListener(amount -> {
                Map<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("transactionType", 1);
                paramsMap.put("transactionPayType", 0);
                paramsMap.put("transactionAmount", ArithUtils.mul(amount,10000));
                paramsMap.put("transactionId", 1);
                paramsMap.put("outTradeNo", 100000);
                paramsMap.put("transactionStatus", 0);
                mPresenter.createAccountTransactionRecord(paramsMap);
                })
                .showPopupWindow();
    }


    /**
     * @description : TODO 下拉刷新监听
     * @author : yuanweiwei
     */
    private OnRefreshListener mOnRefreshListener = refreshLayout -> {
        mPresenter.queryPayRecordPageList(paramMap, true);
        mPresenter.queryByUserAccount();
    };

    /**
     * @description : TODO 上拉加载监听
     * @author : yuanweiwei
     */
    private OnLoadMoreListener mOnLoadMoreListener = refreshLayout -> {
        mPresenter.queryPayRecordPageList(paramMap, false);
    };
    /**
     * @return
     * @description 初始化骨架屏
     * @date: 2020/8/10 16:56
     * @author: YIWUANYUAN
     */
    private void initSkeletonScreen() {
        //初始化数据
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
     * 查看更多
     * */
    @OnClick({R2.id.tv_more})
    void onBtnMore(View view){
        Utils.postcard(RouterHub.HOME_TRANSACTIONRECORDACTIVITY)
                .withInt("formPage",0)
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
        mTvBalance.setText(Utils.appendStr(ArithUtils.div(Double.parseDouble(data.getAccountBalance()),10000)));


    }

    @Override
    public void paymentCallBack() {
        //刷新数据
        mPresenter.queryPayRecordPageList(paramMap, true);
        mPresenter.queryByUserAccount();
        //提示
        IToast.showFinishShort("您已充值成功");
    }
}