package com.yway.scomponent.organ.mvp.presenter;

import android.app.Application;
import android.widget.ImageView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.commonsdk.core.Constants;
import com.yway.scomponent.commonsdk.core.DictClassifyBean;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;
import com.yway.scomponent.commonsdk.utils.CacheUtils;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.mvp.contract.HomeContract;
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

    public void queryDict() {
        String dictKey = Utils.appendStr(
                Constants.SYS_DICT_MEETING_DEVICE,",",
                        Constants.SYS_DICT_MEETING_CHECK_IN_TIME);
        querySysByDictClassify(dictKey);
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
     * @param ids 字典key
     * @return
     * @description 字典查询
     * @date: 2020/12/17 23:16
     * @author: YIWUANYUAN
     */
    public void querySysByDictClassify(String ids) {
        Map<String, Object> params = new HashMap<>();
        params.put("sysDictClassify", ids);
        mModel.querySysByDictClassify(params)
                .subscribeOn(Schedulers.io())
                //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.refreshLayout().finishRefresh();
                })
                //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
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
     * @param dictClassifyBeans 数据字典
     * @return
     * @description 缓存数据字典
     * @date: 2020/12/22 15:47
     * @author: Yuan
     */
    private void cacheDict(List<DictClassifyBean> dictClassifyBeans) {
        if (dictClassifyBeans == null || dictClassifyBeans.size() <= 0) return;
        DictClassifyBean dictClassifyBean = new DictClassifyBean();
        //获取设备字典
        if (dictClassifyBeans.get(0) != null) {
            List<DictClassifyBean> dictDevice = dictClassifyBeans.get(0).getSysDictRspBOList();
            //缓存设备
            dictClassifyBean.setDictDevice(dictDevice);
        }
        //获取会议报道时间
        if (dictClassifyBeans.get(1) != null) {
            List<DictClassifyBean> dictDevice = dictClassifyBeans.get(1).getSysDictRspBOList();
            //缓存会议报道时间
            dictClassifyBean.setDictMeetingCheckInTime(dictDevice);
        }
        //缓存数据字典
        CacheUtils.initMMKV().encode(Constants.APP_COMMON_DICT, dictClassifyBean);
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
