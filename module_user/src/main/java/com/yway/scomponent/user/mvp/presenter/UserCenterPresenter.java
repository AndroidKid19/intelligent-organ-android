package com.yway.scomponent.user.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.StringUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;



import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yway.scomponent.commonres.dialog.IToast;
import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.commonsdk.core.UploadFileBean;
import com.yway.scomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import com.yway.scomponent.user.mvp.contract.UserCenterContract;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/02 14:20
 * ================================================
 */
@ActivityScope
public class UserCenterPresenter extends BasePresenter<UserCenterContract.Model, UserCenterContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    /**
     * 记录头像
     * */
    public String sysUserFilePath = "";

    public String getSysUserFilePath() {
        return sysUserFilePath;
    }

    @Inject
    public UserCenterPresenter(UserCenterContract.Model model, UserCenterContract.View rootView) {
        super(model, rootView);
    }

    /**
     * @method  上传图片
     * @description
     * @date: 2019/9/18 15:12
     * @author: Yuanweiwei
     * @return
     */
    public void uploadPicture(File file){
        mModel.uploadPicture(file)
                .subscribeOn(Schedulers.io())
                //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .retryWhen(new RetryWithDelay(0, 2))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UploadFileBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<UploadFileBean> datas) {
                        mRootView.hideLoading();
                        if (datas.isSuccess() && !CollectionUtils.isEmpty(datas.getData().getFileList())){
                            mRootView.uploadSuccess(datas.getData().getFileList().get(0).getUrl());
                        }else{
                            IToast.showErrorShort(datas.getMessage());
                        }
                    }
                });
    }


    public void modifyHeadPic(List<File> files){
        mModel.uploadPicture(files)
                .subscribeOn(Schedulers.io())
                //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<BaseResponse<UploadFileBean>, ObservableSource<BaseResponse>>) uploadFileBeanBaseResponse -> {
                    Map<String, Object> paramsMap = new HashMap<>();
                    //获取上传的头像
                    List<UploadFileBean> uploadFileBean  = uploadFileBeanBaseResponse.getData().getFileList();
                    if (CollectionUtils.isEmpty(uploadFileBean) || StringUtils.isEmpty(uploadFileBean.get(0).getUrl())){
                        paramsMap.put("sysUserFilePath", "");
                    }else{
                        paramsMap.put("sysUserFilePath", uploadFileBean.get(0).getUrl());
                        this.sysUserFilePath = uploadFileBean.get(0).getUrl();
                    }
                    //修改头像
                    return mModel.modifyAppUserInfoById(paramsMap);
                })
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
                            mRootView.modifyHeadPicSuccess();
                        } else {
                            ArmsUtils.snackbarText(datas.getMessage());
                        }
                    }
                });
    }


    public RxPermissions getRxPermissions(Activity activity){
        return new RxPermissions((FragmentActivity) activity);
    }
    /**
     * @description TODO  加载图片
     * @return
     */
    public void imageLoader(String url, ImageView imageView){
        //itemView 的 Context 就是 Activity, Glide 会自动处理并和该 Activity 的生命周期绑定
        mImageLoader.loadImage(mApplication,
                CommonImageConfigImpl
                        .builder()
                        .url(url)
                        .isCropCircle(true)
                        .imageView(imageView)
                        .build());
    }

    /**
     * 修改性别
     * @param  params
     * sex 性别  0女 1男
     * @return
     */
    public void modifyAppUserInfoById(Map<String,Object> params) {
        mModel.modifyAppUserInfoById(params)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse result) {
                        if (result.isSuccess()){
                            mRootView.modifySuccess();
                        }else{
                            ArmsUtils.snackbarText(result.getMessage());
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