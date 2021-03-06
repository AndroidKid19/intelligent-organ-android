package com.yway.scomponent.organ.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.ObjectUtils;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.commonsdk.core.Constants;
import com.yway.scomponent.commonsdk.core.DictClassifyBean;
import com.yway.scomponent.commonsdk.core.EventBusHub;
import com.yway.scomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import com.yway.scomponent.commonsdk.utils.CacheUtils;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.mvp.contract.HomeContract;
import com.yway.scomponent.organ.mvp.model.entity.AppVersion;
import com.yway.scomponent.organ.mvp.model.entity.ConfigureBean;
import com.yway.scomponent.organ.mvp.model.entity.HomeMetingBean;
import com.yway.scomponent.organ.mvp.model.entity.MessageBean;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVP on 05/09/2020 17:25
 * <a href="mailto:yuanw9@sina.com">Contact me</a>
 * ================================================
 */
@FragmentScope
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
        super(model, rootView);
    }


    public RxPermissions getRxPermissions(Activity activity) {
        return new RxPermissions((FragmentActivity) activity);
    }

    public void queryDict() {
        String dictKey = Utils.appendStr(
                Constants.SYS_DICT_MEETING_DEVICE, ",",
                Constants.SYS_DICT_MEETING_CHECK_IN_TIME, ",",
                Constants.SYS_DICT_POSITION);
        querySysByDictClassify(dictKey);
    }

    public void upgradeApp() {
        Map<String, Object> params = new HashMap<>();
        /**
         * ?????????0???ios??? 1???android???
         */
        params.put("type", 1);
        mModel.queryLatestVersionByEntity(params)
                .subscribeOn(Schedulers.io())
                //?????????????????????,??????????????????????????????,?????????????????????????????????
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                //?????? Rxlifecycle,??? Disposable ??? Activity ????????????
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<AppVersion>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<AppVersion> datas) {
                        if (datas.isSuccess() && !ObjectUtils.isEmpty(datas.getData())) {
                            mRootView.upgradeAppBcakCall(datas.getData());
                        }

                    }
                });
    }

    /**
     * @return
     * @description TODO  ????????????
     */
    public void imageLoader(String url, ImageView imageView) {
        //itemView ??? Context ?????? Activity, Glide ???????????????????????? Activity ?????????????????????
        mImageLoader.loadImage(mApplication,
                CommonImageConfigImpl
                        .builder()
                        .url(url)
                        .isCropCircle(true)
                        .imageView(imageView)
                        .build());
    }

    /**
     * ????????????
     */
    public void queryArticlePublishPageList() {
        Map<String, Object> params = new HashMap<>();
        params.put("pageNo", 1);
        params.put("pageSize", 3);
        mModel.queryArticlePublishPageList(params)
                .subscribeOn(Schedulers.io())
                //?????????????????????,??????????????????????????????,?????????????????????????????????
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {

                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                //?????? Rxlifecycle,??? Disposable ??? Activity ????????????
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<MessageBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<MessageBean> datas) {
                        if (datas.isSuccess()) {
                            if (ObjectUtils.isEmpty(datas.getData())) return;
                            mRootView.queryArticleCallBack(datas.getData().getRows());
                        } else {
                            ArmsUtils.snackbarText(datas.getMessage());
                        }
                    }
                });
    }


    /**
     * ????????????
     */
    public void queryMyMeetingList() {
        mModel.queryMyMeetingList(new HashMap<>())
                .subscribeOn(Schedulers.io())
                //?????????????????????,??????????????????????????????,?????????????????????????????????
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {

                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                //?????? Rxlifecycle,??? Disposable ??? Activity ????????????
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<HomeMetingBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<HomeMetingBean> datas) {
                        if (datas.isSuccess()) {
                            if (ObjectUtils.isEmpty(datas.getData())) return;
                            mRootView.metingListCallBack(datas.getData());
                        } else {
                            ArmsUtils.snackbarText(datas.getMessage());
                        }
                    }
                });
    }

    /**
     * @param ids ??????key
     * @return
     * @description ????????????
     * @date: 2020/12/17 23:16
     * @author: YIWUANYUAN
     */
    public void querySysByDictClassify(String ids) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysDictClassify", ids);
        mModel.querySysByDictClassify(params)
                .subscribeOn(Schedulers.io())
                //?????????????????????,??????????????????????????????,?????????????????????????????????
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.refreshLayout().finishRefresh();
                })
                //?????? Rxlifecycle,??? Disposable ??? Activity ????????????
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<DictClassifyBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<DictClassifyBean> datas) {
                        mRootView.refreshLayout().finishRefresh();
                        if (datas == null || datas.getData() == null) return;
                        List<DictClassifyBean> dictClassifyBeans = datas.getData().getList();
                        cacheDict(dictClassifyBeans);
                    }
                });
    }

    /**
     * @param dictClassifyBeans ????????????
     * @return
     * @description ??????????????????
     * @date: 2020/12/22 15:47
     * @author: Yuan
     */
    private void cacheDict(List<DictClassifyBean> dictClassifyBeans) {
        if (dictClassifyBeans == null || dictClassifyBeans.size() <= 0) return;
        DictClassifyBean dictClassifyBean = new DictClassifyBean();
        //??????????????????
        if (dictClassifyBeans.get(0) != null) {
            List<DictClassifyBean> dictDevice = dictClassifyBeans.get(0).getSysDictRspBOList();
            //????????????
            dictClassifyBean.setDictDevice(dictDevice);
        }
        //????????????????????????
        if (dictClassifyBeans.get(1) != null) {
            List<DictClassifyBean> dictDevice = dictClassifyBeans.get(1).getSysDictRspBOList();
            //????????????????????????
            dictClassifyBean.setDictMeetingCheckInTime(dictDevice);
        }

        //????????????
        if (dictClassifyBeans.get(2) != null) {
            List<DictClassifyBean> dictDevice = dictClassifyBeans.get(2).getSysDictRspBOList();
            //????????????
            dictClassifyBean.setDictJop(dictDevice);
        }

        //??????????????????
        CacheUtils.initMMKV().encode(Constants.APP_COMMON_DICT, dictClassifyBean);

        //??????????????????
        EventBus.getDefault().post(1, EventBusHub.EVENTBUS_TAG_USER_REFRESH);
    }

    /**
     * ????????????
     */
    public void queryApprovalConfigureList() {
        mModel.queryApprovalConfigureList(new HashMap<>())
                .subscribeOn(Schedulers.io())
                //?????????????????????,??????????????????????????????,?????????????????????????????????
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {

                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                //?????? Rxlifecycle,??? Disposable ??? Activity ????????????
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<ConfigureBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<ConfigureBean> datas) {
                        if (datas.isSuccess()) {
                            //??????????????????
                            CacheUtils.initMMKV().encode(Constants.APP_COMMON_config, datas.getData());
                        } else {
                            ArmsUtils.snackbarText(datas.getMessage());
                        }
                    }
                });
    }

    /**
     * ??????
     */
    public void createAccountTransactionRecord(Map<String, Object> map) {
        mModel.createAccountTransactionRecord(map)
                .subscribeOn(Schedulers.io())
                //?????????????????????,??????????????????????????????,?????????????????????????????????
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {

                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                //?????? Rxlifecycle,??? Disposable ??? Activity ????????????
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse datas) {
                        if (datas.isSuccess()) {
                            mRootView.paymentCallBack();
                        } else {
                            ArmsUtils.snackbarText(datas.getMessage());
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
