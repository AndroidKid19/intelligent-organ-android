package com.yway.scomponent.organ.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.jwsd.libzxing.OnQRCodeListener;
import com.jwsd.libzxing.QRCodeManager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yway.scomponent.commonres.dialog.IToast;
import com.yway.scomponent.commonres.dialog.MessageDialog;
import com.yway.scomponent.commonres.dialog.ProgresDialog;
import com.yway.scomponent.commonres.dialog.UpgradeDialog;
import com.yway.scomponent.commonres.view.banner.BGABanner;
import com.yway.scomponent.commonsdk.BuildConfig;
import com.yway.scomponent.commonsdk.core.Constants;
import com.yway.scomponent.commonsdk.core.EventBusHub;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.utils.CacheUtils;
import com.yway.scomponent.commonsdk.utils.SystemUtils;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.di.component.DaggerHomeComponent;
import com.yway.scomponent.organ.mvp.contract.HomeContract;
import com.yway.scomponent.organ.mvp.model.entity.AppVersion;
import com.yway.scomponent.organ.mvp.model.entity.ConferenceBean;
import com.yway.scomponent.organ.mvp.model.entity.ConfigureBean;
import com.yway.scomponent.organ.mvp.model.entity.HomeMetingBean;
import com.yway.scomponent.organ.mvp.model.entity.MessageBean;
import com.yway.scomponent.organ.mvp.presenter.HomePresenter;
import com.yway.scomponent.organ.mvp.ui.adapter.InformationAdapter;
import com.yway.scomponent.organ.mvp.ui.adapter.MeetingAdapter;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * ================================================
 * Description:??????
 * ================================================
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View, BGABanner.Delegate<ImageView, String>, BGABanner.Adapter<ImageView, String> {
    @BindView(R2.id.banner_view)
    BGABanner mContentBanner;
    /**
     * ????????????
     */
    @BindView(R2.id.recycle_view)
    RecyclerView mRecyclerView;
    /**
     * ???????????????????????????View
     */
    @BindView(R2.id.refresh_layout)
    RefreshLayout mRefreshLayout;
    /**
     * ??????
     */
    @BindView(R2.id.recycle_view_msg)
    RecyclerView mRecyclerViewMsg;

    @BindView(R2.id.tv_queryall)
    AppCompatTextView mTvQueryAll;
    @BindView(R2.id.tv_conf_count)
    AppCompatTextView mTvMetingCount;
    /**
     * ?????????????????????
     */
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    InformationAdapter mAdapter;

    @Inject
    MeetingAdapter mMeetingAdapter;
    /**
     * ?????????????????????
     */
    @Inject
    List<MessageBean> mDataLs;
    /**
     * ?????????????????????
     */
    @Inject
    List<ConferenceBean> mConferenceBeanDataLs;
    /**
     * ?????????
     **/
    private SkeletonScreen mSkeletonScreen;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent //??????????????????,?????????????????????
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.organ_fragment_home, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        BarUtils.setStatusBarLightMode(getActivity(), false);
        //???????????????
        ImmersionBar.with(this).titleBar(R.id.view_search).statusBarDarkFont(true).init();
        //?????????recylerview
        initRecyclerView();
        //?????????banner
        initBanner();
        //??????????????????
//        initSkeletonScreen();
        initQueryData();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.upgradeApp();
    }


    private void initQueryData() {
        //????????????
        mPresenter.queryApprovalConfigureList();
        //?????????????????????
        mPresenter.queryDict();
        //??????????????????
        mPresenter.queryMyMeetingList();
        //????????????
        mPresenter.queryArticlePublishPageList();
    }

    /**
     * ????????????????????????
     */
    @OnClick(R2.id.tv_search)
    void onSearchClick(View view) {
        Utils.postcard(RouterHub.HOME_INFORMATIONACTIVITY)
                .navigation(getActivity());
    }


    @OnClick({R2.id.ibtn_scan_pay})
    void onBtnCard(View view){
        PermissionUtil.launchCamera(mRequestPermission, mPresenter.getRxPermissions(getActivity()), ArmsUtils.obtainAppComponentFromContext(getActivity()).rxErrorHandler());
    }


    /**
     * ?????????RecyclerView
     */
    private void initRecyclerView() {
        ArmsUtils.configRecyclerView(mRecyclerViewMsg, mLayoutManager);
        mRecyclerViewMsg.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mMeetingAdapter);

        //????????????????????????
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        //??????????????????
//        mRefreshLayout.setOnLoadMoreListener(mOnLoadMoreListener);
        mRefreshLayout.setEnableLoadMore(false);

        //??????????????????
        mAdapter.setOnItemClickListener(mOnRecyclerViewItemClickListener);

        //??????????????????
        mMeetingAdapter.setOnItemClickListener(mOnRecyclerViewItemClickListener);
    }

    private DefaultAdapter.OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener = (view, viewType, data, position) -> {
        if (view.getId() == R.id.view_metting) {
            ConferenceBean conferenceBean = (ConferenceBean) data;
            Utils.postcard(RouterHub.HOME_MEETINGDETAILSACTIVITY)
                    .withString("mettingId", conferenceBean.getMeetingRecordId())
                    .withInt("pageFrom", 6)
                    .navigation(getActivity());
        } else if (view.getId() == R.id.view_information) {
            MessageBean messageBean = (MessageBean) data;
            Timber.i(Utils.appendStr(BuildConfig.H5_HOST_ROOT, "articleMobile?id=", messageBean.getId()));
            Utils.postcard(RouterHub.HOME_WEBVIEWACTIVITY)
                    .withString(RouterHub.PARAM_WEBVIEWXURL, Utils.appendStr(BuildConfig.H5_HOST_ROOT, "articleMobile?id=", messageBean.getId()))
                    .withInt("pageFrom", 2)
                    .withString("articleId", messageBean.getId())
                    .withString("pushTime",messageBean.getCreateTime())
                    .navigation(getActivity());
        }
    };

    /**
     * @return
     * @description ??????????????????
     * @date: 2020/8/10 16:56
     * @author: YIWUANYUAN
     */
    private void initSkeletonScreen() {
        mSkeletonScreen = Skeleton.bind(mRecyclerView)
                .adapter(mAdapter)
                .shimmer(true)
                .angle(20)
                .frozen(false)
                .duration(1200)
                .count(15)
                .load(R.layout.organ_home_item_skeleton)
                .show(); //default count is 10
    }


    /**
     * init banner data
     */
    private void initBanner() {
        // ???????????????

        mContentBanner.setDelegate(this);
        mContentBanner.setAdapter(this);
        List<String> bannerLs = new ArrayList<>();
        bannerLs.add("https://bdp-dev-bucket.oss-cn-beijing.aliyuncs.com/app/20220214140555.png");
        bannerLs.add("https://bdp-dev-bucket.oss-cn-beijing.aliyuncs.com/app/20220214140548.png");
        mContentBanner.setAutoPlayAble(bannerLs.size() > 1);
        mContentBanner.setData(bannerLs, null);
    }

    /**
     * @description : TODO ????????????
     * @author : yuanweiwei
     */
    private OnRefreshListener mOnRefreshListener = refreshLayout -> {
        //?????????????????????
        initQueryData();
    };

    /**
     * @description : TODO ????????????
     * @author : yuanweiwei
     */
    private OnLoadMoreListener mOnLoadMoreListener = refreshLayout -> {
    };

    /**
     * ?????????
     */
    @OnClick(R2.id.tv_menu_address)
    void onAddressClick(View view) {
        EventBus.getDefault().post(1, EventBusHub.EVENTBUS_TAG_HOME_CURRENTITEM_REFRESH);
    }

    /**
     * ?????????
     */
    @OnClick(R2.id.tv_menu_room)
    void onRoomClick(View view) {
        //????????????
        ConfigureBean configureBean = CacheUtils.initMMKV().decodeParcelable(Constants.APP_COMMON_config, ConfigureBean.class);
        boolean isAuth = false;
        for (ConfigureBean config : configureBean.getList()) {
            if (config.getType() == 3 && config.getUserId().equals(CacheUtils.queryUserId())) {
                //????????????
                isAuth = true;
            }
        }
        if (isAuth) {//???????????????
            Utils.navigation(getActivity(), RouterHub.HOME_CONFERENCEROOMACTIVITY);
        } else {
            IToast.showFinishShort("??????????????????");
        }
    }

    /**
     * ????????????
     */
    @OnClick(R2.id.tv_menu_canteen)
    void onCanteenClick(View view) {
        Utils.navigation(getActivity(), RouterHub.HOME_CANTEENACTIVITY);
    }

    /**
     * ??????
     */
    @OnClick(R2.id.tv_menu_card)
    void onCardClick(View view) {
        IToast.showWarnShort("?????????????????????");
    }

    /**
     * ????????????
     */
    @OnClick(R2.id.tv_queryall)
    void onMoreMettingClick(View view) {
        //?????????????????? - ????????????
        Utils.navigation(getActivity(), RouterHub.HOME_MYMEETINGACTIVITY);
    }


    /**
     * ????????????
     */
    @OnClick(R2.id.tv_more)
    void onMoreMsgClick(View view) {
        //??????????????????
        Utils.postcard(RouterHub.HOME_INFORMATIONACTIVITY)
                .navigation(getActivity());
    }

    /**
     * ????????????
     */
    @Override
    public void metingListCallBack(HomeMetingBean data) {
        //???????????????
        List<ConferenceBean> inMeetingPersonnelRspBOList = data.getInMeetingPersonnelRspBOList();
        //????????????
        List<ConferenceBean> todoMeetingPersonnelRspBOList = data.getTodoMeetingPersonnelRspBOList();
        mConferenceBeanDataLs.clear();
        //????????????????????????
        if (CollectionUtils.isNotEmpty(inMeetingPersonnelRspBOList)) {
            for (ConferenceBean conferenceBean : inMeetingPersonnelRspBOList) {
                conferenceBean.setConfIng(true);
                mConferenceBeanDataLs.add(conferenceBean);
            }
        }
        //??????????????????
        if (CollectionUtils.isNotEmpty(todoMeetingPersonnelRspBOList)) {
            for (ConferenceBean conferenceBean : todoMeetingPersonnelRspBOList) {
                mConferenceBeanDataLs.add(conferenceBean);
            }
        }
        mTvMetingCount.setText(Utils.appendStr("(",inMeetingPersonnelRspBOList.size()+todoMeetingPersonnelRspBOList.size(),")"));
        mMeetingAdapter.notifyDataSetChanged();
    }

    /**
     * ??????
     */
    @Override
    public void queryArticleCallBack(List<MessageBean> rows) {
        mDataLs.clear();
        mDataLs.addAll(rows);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * @return
     * @description ??????????????????
     * @date: 2021/3/5 17:49
     * @author: Yuan
     */
    @Override
    public void upgradeAppBcakCall(AppVersion data) {
        upgradeApp(data);
    }

    /**
     * @description ??????
     * @date: 2021/3/5 17:48
     * @author: Yuan
     * @return
     */
    private UpgradeDialog.Builder mUpdateDialog;

    private void upgradeApp(AppVersion data) {
        if (Long.parseLong(data.getVersion()) > AppUtils.getAppVersionCode()) {
            RxPermissions mRxPermissions = new RxPermissions(this);
            if (!ObjectUtils.isEmpty(mUpdateDialog) && mUpdateDialog.isShowing()) return;
            mUpdateDialog = new UpgradeDialog.Builder(getActivity(), mRxPermissions)
                    // ?????????
                    .setVersionName(Utils.appendStr("?????????V", data.getUserVersion(), "?????????"))
                    // ??????????????????
                    .setForceUpdate(data.getUpgradeMode() == 1 ? true : false)
                    // ????????????
                    .setUpdateLog(data.getRemark().replace("##", "\n"))
                    // ?????? url
                    .setDownloadUrl(data.getPackageDownloadLink())
                    // ????????????
                    .setFileSize(Utils.appendStr("?????????", data.getPackageSize(), "M"))
                    .showPopupWindow();
        }
    }



    private PermissionUtil.RequestPermission mRequestPermission = new PermissionUtil.RequestPermission() {
        @Override
        public void onRequestPermissionSuccess() {
            QRCodeManager.getInstance().with(getActivity()).setReqeustType(1).scanningQRCode(mOnQRCodeListener);
        }

        @Override
        public void onRequestPermissionFailure(List<String> permissions) {
            PermissionUtil.launchCamera(mRequestPermission, mPresenter.getRxPermissions(getActivity()), ArmsUtils.obtainAppComponentFromContext(getActivity()).rxErrorHandler());
        }

        @Override
        public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
            ArmsUtils.snackbarText(getString(R.string.public_common_permission_fail));
        }
    };

    /**
     * @description ?????????????????????
     * @date: 2020/12/7 18:57
     * @author: Yuan
     * @return
     */
    private OnQRCodeListener mOnQRCodeListener = new OnQRCodeListener() {
        @Override
        public void onCompleted(String result) {
            //???????????????????????????
            String transactionType = SystemUtils.getTransactionType(result);
            //??????
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("transactionType", transactionType);
            paramsMap.put("transactionPayType", transactionType);
            paramsMap.put("transactionStatus", 0);
            mPresenter.createAccountTransactionRecord(paramsMap);
        }

        @Override
        public void onError(Throwable errorMsg) {
            ArmsUtils.snackbarText(errorMsg.getMessage());
        }

        @Override
        public void onCancel() {

        }
    };



    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public RefreshLayout refreshLayout() {
        return mRefreshLayout;
    }


    @Override
    public SkeletonScreen skeletonScreen() {
        return mSkeletonScreen;
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

    /**
     * banner click callback
     */
    @Override
    public void onBannerItemClick(BGABanner banner, ImageView itemView, @Nullable @org.jetbrains.annotations.Nullable String model, int position) {
//        String newUrl = "https://www.baidu.com";
//        if (!StringUtils.isEmpty(newUrl)) {
//            Utils.postcard(RouterHub.APP_AGENTWEBACTIVITY)
//                    .withString(RouterHub.PARAM_WEBVIEWXURL, newUrl)
//                    .navigation(getContext());
//        }
    }

    /**
     * banner ????????????
     */
    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable @org.jetbrains.annotations.Nullable String model, int position) {
        mPresenter.imageLoader(model, itemView);
    }

    @Override
    public void paymentCallBack() {
        new MessageDialog.Builder()
                .setTitle("????????????")
                .setMessage("??????????????????,?????????")
                .setOnViewItemClickListener(v -> {

                })
                .showPopupWindow();
    }
}
