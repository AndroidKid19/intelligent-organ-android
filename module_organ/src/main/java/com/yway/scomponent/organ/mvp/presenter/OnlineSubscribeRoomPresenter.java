package com.yway.scomponent.organ.mvp.presenter;

import android.app.Application;

import com.blankj.utilcode.util.CollectionUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.jess.arms.utils.RxLifecycleUtils;
import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.commonsdk.core.Constants;
import com.yway.scomponent.commonsdk.utils.ArithUtils;
import com.yway.scomponent.organ.mvp.contract.OnlineSubscribeRoomContract;
import com.yway.scomponent.organ.mvp.model.entity.RoomDetailsBean;
import com.yway.scomponent.organ.mvp.ui.adapter.ConferenceRoomAdapter;

import java.util.List;
import java.util.Map;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/07 16:19
 * ================================================
 */
@ActivityScope
public class OnlineSubscribeRoomPresenter extends BasePresenter<OnlineSubscribeRoomContract.Model, OnlineSubscribeRoomContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private int pageNo = 1;
    private int preEndIndex;
    private int pageSize = 100000;

    @Inject
    List<RoomDetailsBean> mDataLs;

    @Inject
    ConferenceRoomAdapter mAdapter;

    @Inject
    public OnlineSubscribeRoomPresenter(OnlineSubscribeRoomContract.Model model, OnlineSubscribeRoomContract.View rootView) {
        super(model, rootView);
    }


    public void queryMeetingRoomPageList(Map<String, Object> params, boolean pullToRefresh){
        if (pullToRefresh){
            pageNo = 1;
        }else{
            pageNo = (int) Math.ceil(ArithUtils.div(mDataLs.size(), Constants.pageSize)) + 1;
        }
        params.put("pageNo",pageNo);
        params.put("pageSize",pageSize);
        mModel.queryMeetingRoomPageList(params)
                .subscribeOn(Schedulers.io())
                //?????????????????????,??????????????????????????????,?????????????????????????????????
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (pullToRefresh){
                        //??????????????????????????????view
//                        mRootView.skeletonScreen().hide();
                    }else{
                    }
                })
                //?????? Rxlifecycle,??? Disposable ??? Activity ????????????
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<RoomDetailsBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<RoomDetailsBean> datas) {
                        //????????????????????????
                        if (CollectionUtils.isEmpty(datas.getData().getRows())){
                            if(pullToRefresh){
                                //??????????????????????????????view
//                                mRootView.skeletonScreen().hide();
                                //????????????????????????????????????????????????
//                                mRootView.multipleStatusView().showEmpty();
                            }else{
                            }
                            return;
                        }
                        //??????????????????
//                        mRootView.multipleStatusView().showContent();
                        if (pullToRefresh) {
                            //????????????????????????????????????
                            mDataLs.clear();
                        }
                        //???????????????????????????,???????????????????????????????????????
                        preEndIndex = mDataLs.size();
                        mDataLs.addAll(datas.getData().getRows());
                        if (pullToRefresh) {
                            mAdapter.notifyDataSetChanged();
                            if (mDataLs.size() < Constants.pageSize){
                                //???????????????????????????????????????????????????????????????
//                                mRootView.refreshLayout().finishLoadMoreWithNoMoreData();
                            }
                        } else {
                            mAdapter.notifyItemRangeInserted(preEndIndex, mDataLs.size());
                            if (datas.getData().getRows().size() < Constants.pageSize){
                                //???????????????????????????????????????????????????????????????
//                                mRootView.refreshLayout().finishLoadMoreWithNoMoreData();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (pullToRefresh){
                            //??????????????????????????????view
//                            mRootView.skeletonScreen().hide();
                            //???????????????????????????????????????
//                            mRootView.refreshLayout().finishRefresh();
                            //????????????????????????????????????
//                            mRootView.multipleStatusView().showEmpty();
                        }else{
                            //???????????????????????????????????????
//                            mRootView.refreshLayout().finishLoadMore();
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