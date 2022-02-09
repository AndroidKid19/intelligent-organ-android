package com.yway.scomponent.organ.mvp.contract;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.commonsdk.core.AddressCompanyBean;

import java.util.Map;

import io.reactivex.Observable;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/11/15 11:13
 * ================================================
 */
public interface AddressBookContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        Fragment getFragment();

        Activity getActivity();

        void queryOrgRspCallBack(AddressCompanyBean data);

        RefreshLayout refreshLayout();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BaseResponse<AddressCompanyBean>> queryAllSysOrgAndSysUserList(Map<String, Object> params);
    }
}