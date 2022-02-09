package com.yway.scomponent.organ.mvp.contract;

import android.app.Activity;

import com.ethanhua.skeleton.SkeletonScreen;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.commonsdk.core.DictClassifyBean;

import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVP on 05/09/2020 17:25
 * <a href="mailto:yuanw9@sina.com">Contact me</a>
 * ================================================
 */
public interface HomeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        RefreshLayout refreshLayout();

        SkeletonScreen skeletonScreen();

        Activity getActivity();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {


        Observable<BaseResponse<DictClassifyBean>> querySysByDictClassify(Map<String, Object> params);
    }
}
