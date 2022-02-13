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

import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.commonsdk.core.Constants;
import com.yway.scomponent.commonsdk.utils.ArithUtils;
import com.yway.scomponent.organ.mvp.contract.VisitorRecordContract;
import com.yway.scomponent.organ.mvp.model.entity.MessageBean;
import com.yway.scomponent.organ.mvp.model.entity.VisitorRecordBean;
import com.yway.scomponent.organ.mvp.ui.adapter.HomeAdapter;
import com.yway.scomponent.organ.mvp.ui.adapter.VisitorAdapter;

import java.util.List;
import java.util.Map;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/01/10 10:31
 * ================================================
 */
@ActivityScope
public class VisitorRecordPresenter extends BasePresenter<VisitorRecordContract.Model, VisitorRecordContract.View> {
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
    private int pageSize = 20;

    @Inject
    List<VisitorRecordBean> mDataLs;

    @Inject
    VisitorAdapter mAdapter;

    @Inject
    public VisitorRecordPresenter(VisitorRecordContract.Model model, VisitorRecordContract.View rootView) {
        super(model, rootView);
    }

    public void queryVisitRegisterRecordPageList(Map<String, Object> params, boolean pullToRefresh){
        if (pullToRefresh){
            pageNo = 1;
        }else{
            pageNo = (int) Math.ceil(ArithUtils.div(mDataLs.size(), Constants.pageSize)) + 1;
        }
        params.put("pageNo",pageNo);
        params.put("pageSize",pageSize);
        mModel.queryVisitRegisterRecordPageList(params)
                .subscribeOn(Schedulers.io())
                //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (pullToRefresh){
                        //首次加载则隐藏骨架屏view
                        mRootView.skeletonScreen().hide();
                        //下拉刷新报错则关闭刷新状态
                        mRootView.refreshLayout().finishRefresh();
                    }else{
                        //如果上拉加载文档不够一页则标识没有更多数据
                        mRootView.refreshLayout().finishLoadMoreWithNoMoreData();
                    }
                })
                //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<VisitorRecordBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<VisitorRecordBean> datas) {
                        //文档列表是否为空
                        if (CollectionUtils.isEmpty(datas.getData().getRows())){
                            if(pullToRefresh){
                                //首次加载则隐藏骨架屏view
                                mRootView.skeletonScreen().hide();
                                //下拉刷新文档没有文档则显示缺省页
                                mRootView.multipleStatusView().showEmpty();
                                //下拉刷新报错则关闭刷新状态
                                mRootView.refreshLayout().finishRefresh();
                            }else{
                                //如果上拉加载文档不够一页则标识没有更多数据
                                mRootView.refreshLayout().finishLoadMoreWithNoMoreData();
                            }
                            return;
                        }
                        //显示文档布局
                        mRootView.multipleStatusView().showContent();
                        if (pullToRefresh) {
                            //如果是下拉刷新则清空列表
                            mDataLs.clear();
                        }
                        //更新之前列表总长度,用于确定加载更多的起始位置
                        preEndIndex = mDataLs.size();
                        mDataLs.addAll(datas.getData().getRows());
                        if (pullToRefresh) {
                            mAdapter.notifyDataSetChanged();
                            if (mDataLs.size() < Constants.pageSize){
                                //如果上拉加载文档不够一页则标识没有更多数据
                                mRootView.refreshLayout().finishLoadMoreWithNoMoreData();
                            }
                        } else {
                            mAdapter.notifyItemRangeInserted(preEndIndex, mDataLs.size());
                            if (datas.getData().getRows().size() < Constants.pageSize){
                                //如果上拉加载文档不够一页则标识没有更多数据
                                mRootView.refreshLayout().finishLoadMoreWithNoMoreData();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (pullToRefresh){
                            //首次加载则隐藏骨架屏view
                            mRootView.skeletonScreen().hide();
                            //下拉刷新报错则关闭刷新状态
                            mRootView.refreshLayout().finishRefresh();
                            //下拉刷新报错则显示缺省页
                            mRootView.multipleStatusView().showEmpty();
                        }else{
                            //上拉加载报错则关闭加载状态
                            mRootView.refreshLayout().finishLoadMore();
                        }
                    }
                });
    }


    /**
     * 统计
     */
    public void queryVisitCountByYearAndMonthAndToday(Map<String, Object> params) {
        mModel.queryVisitCountByYearAndMonthAndToday(params)
                .subscribeOn(Schedulers.io())
                //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<VisitorRecordBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<VisitorRecordBean> datas) {
                        if (datas.isSuccess()) {
                            mRootView.visitCountCallBack(datas.getData());
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