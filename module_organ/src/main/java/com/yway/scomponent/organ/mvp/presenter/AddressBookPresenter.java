package com.yway.scomponent.organ.mvp.presenter;

import android.app.Application;

import com.blankj.utilcode.util.CollectionUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
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
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.organ.mvp.contract.AddressBookContract;
import com.yway.scomponent.commonsdk.core.AddressCompanyBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/11/15 11:13
 * ================================================
 */
@FragmentScope
public class AddressBookPresenter extends BasePresenter<AddressBookContract.Model, AddressBookContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    /**
     * 通讯录列表
     * */
    private List<AddressCompanyBean> sysOrgRspBOList;

    /**
     * 人员
     * */
    private List<UserInfoBean> sysUserRspBOList;

    public List<AddressCompanyBean> getSysOrgRspBOList() {
        return sysOrgRspBOList;
    }

    public void setSysOrgRspBOList(List<AddressCompanyBean> sysOrgRspBOList) {
        this.sysOrgRspBOList = sysOrgRspBOList;
    }

    public List<UserInfoBean> getSysUserRspBOList() {
        return sysUserRspBOList;
    }

    public void setSysUserRspBOList(List<UserInfoBean> sysUserRspBOList) {
        this.sysUserRspBOList = sysUserRspBOList;
    }

    @Inject
    public AddressBookPresenter(AddressBookContract.Model model, AddressBookContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 根据 parentId 获取当前下级组织
     * */
    public List<AddressCompanyBean> queryCompany(String parentId){
        List<AddressCompanyBean> addressCompanyBeans = getSysOrgRspBOList();
        List<AddressCompanyBean> list = new ArrayList<>();
        for (int i = 0; i < addressCompanyBeans.size(); i++) {
               if (addressCompanyBeans.get(i).getParentId().equals(parentId)){
                   list.add(addressCompanyBeans.get(i));
               }
        }
        return list;
    }


    /**
     * 根据 orgId 查询当前组织机构人员
     * */
    public List<UserInfoBean> queryCompanyUser(String orgId){
        //当前组织的人员
        List<UserInfoBean> list = new ArrayList<>();
        //如果当前用户为空则返回空集合
        if (CollectionUtils.isEmpty(getSysUserRspBOList())) return list;
        //获取所有人员
        List<UserInfoBean> sysUserRspBOList = getSysUserRspBOList();
        //遍历查找
        for (int i = 0; i < sysUserRspBOList.size(); i++) {
            if (sysUserRspBOList.get(i).getOrgId().equals(orgId)){
                list.add(sysUserRspBOList.get(i));
            }
        }
        return list;
    }

    /**
     * 查询组织机构及人员
     * */
    public void queryAllSysOrgAndSysUserList() {
        mModel.queryAllSysOrgAndSysUserList(new HashMap<>())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.refreshLayout().finishRefresh();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<AddressCompanyBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<AddressCompanyBean> result) {
                        mRootView.refreshLayout().finishRefresh();//隐藏下拉刷新的进度条
                        if (result.isSuccess()){
                            mRootView.queryOrgRspCallBack(result.getData());
                        }else{
                            mRootView.showMessage(result.getMessage());
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