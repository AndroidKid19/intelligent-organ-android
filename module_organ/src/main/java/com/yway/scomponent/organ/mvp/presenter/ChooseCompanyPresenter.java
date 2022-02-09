package com.yway.scomponent.organ.mvp.presenter;

import android.app.Application;

import com.blankj.utilcode.util.CollectionUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.organ.mvp.contract.ChooseCompanyContract;
import com.yway.scomponent.commonsdk.core.AddressCompanyBean;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/23 11:05
 * ================================================
 */
@ActivityScope
public class ChooseCompanyPresenter extends BasePresenter<ChooseCompanyContract.Model, ChooseCompanyContract.View> {
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
    public ChooseCompanyPresenter(ChooseCompanyContract.Model model, ChooseCompanyContract.View rootView) {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}