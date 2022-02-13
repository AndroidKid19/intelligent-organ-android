package com.yway.scomponent.user.mvp.presenter;

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
import com.yway.scomponent.user.mvp.contract.CollectContract;
import com.yway.scomponent.user.mvp.model.entity.ArticleBean;
import com.yway.scomponent.user.mvp.ui.adapter.CollectAdapter;

import java.util.List;
import java.util.Map;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/02/11 14:22
 * ================================================
 */
@ActivityScope
public class CollectPresenter extends BasePresenter<CollectContract.Model, CollectContract.View> {
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
    List<Object> mDataLs;

    @Inject
    CollectAdapter mAdapter;

    @Inject
    public CollectPresenter(CollectContract.Model model, CollectContract.View rootView) {
        super(model, rootView);
    }


    public void queryArticleFavoritesPageList(Map<String, Object> params, boolean pullToRefresh){
        if (pullToRefresh){
            pageNo = 1;
        }else{
            pageNo = (int) Math.ceil(ArithUtils.div(mDataLs.size(), Constants.pageSize)) + 1;
        }
        params.put("pageNo",pageNo);
        params.put("pageSize",pageSize);
        mModel.queryArticleFavoritesPageList(params)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<ArticleBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<ArticleBean> datas) {
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
     * 取消收藏
     */
    public void cancelArticleFavorites(Map<String, Object> params) {
        mModel.cancelArticleFavorites(params)
                .subscribeOn(Schedulers.io())
                //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse datas) {
                        mRootView.hideLoading();
                        if (datas.isSuccess()) {
                            mRootView.cancelArticleFavoritesCallBack();
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