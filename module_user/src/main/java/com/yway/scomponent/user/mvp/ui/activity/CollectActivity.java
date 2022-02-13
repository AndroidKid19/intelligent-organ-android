package com.yway.scomponent.user.mvp.ui.activity;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.base.BaseActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;

import com.jess.arms.utils.ArmsUtils;

import android.content.Intent;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yway.scomponent.commonres.view.layout.ClearEditText;
import com.yway.scomponent.commonres.view.layout.MultipleStatusView;
import com.yway.scomponent.commonsdk.BuildConfig;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.user.R2;
import com.yway.scomponent.user.di.component.DaggerCollectComponent;
import com.yway.scomponent.user.mvp.contract.CollectContract;
import com.yway.scomponent.user.mvp.model.entity.ArticleBean;
import com.yway.scomponent.user.mvp.presenter.CollectPresenter;
import com.yway.scomponent.user.R;
import com.yway.scomponent.user.mvp.ui.adapter.CollectAdapter;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnTextChanged;

/**
 * 收藏
 */
@Route(path = RouterHub.USER_COLLECTACTIVITY)
public class CollectActivity extends BaseActivity<CollectPresenter> implements CollectContract.View {
    /**
     * 多状态view
     */
    @BindView(R2.id.multiple_layout)
    MultipleStatusView mMultipleStatusView;
    /**
     * 下拉刷新，上拉加载View
     */
    @BindView(R2.id.refresh_layout)
    RefreshLayout mRefreshLayout;
    /**
     * RecycleView
     **/
    @BindView(R2.id.recycle_view)
    RecyclerView mRecyclerView;

    /**
     * 搜索内容
     **/
    @BindView(R2.id.et_search_text)
    ClearEditText mTvContentCount;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    CollectAdapter mAdapter;
    /**
     * 查询参数
     **/
    private Map<String, Object> paramMap = new HashMap<>();

    /**
     * 骨架屏
     **/
    private SkeletonScreen mSkeletonScreen;

    private int removePosition ;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCollectComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.user_activity_collect; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).titleBar(R.id.bar_title).init();

        initRecyclerView();
        //初始化骨架屏
        initSkeletonScreen();
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //设置下拉刷新监听
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mRefreshLayout.setOnLoadMoreListener(mOnLoadMoreListener);
        mAdapter.setOnItemClickListener((view1, viewType, data, position) -> {
            ArticleBean messageBean = (ArticleBean) data;
           if (view1.getId() == R.id.view_collect){
               removePosition = position;
                //取消收藏
               Map<String,Object> paramsMap = new HashMap<>();
               paramsMap.put("articleId",messageBean.getArticleId());
               mPresenter.cancelArticleFavorites(paramsMap);
           }else{
               Utils.postcard(RouterHub.HOME_WEBVIEWACTIVITY)
                       .withString(RouterHub.PARAM_WEBVIEWXURL,Utils.appendStr(BuildConfig.H5_HOST_ROOT,"articleMobile?id=",messageBean.getArticleId()))
                       .withInt("pageFrom",2)
                       .withString("articleId",messageBean.getArticleId())
                       .navigation(view1.getContext());
           }

        });
    }


    /**
     * @description : TODO 下拉刷新监听
     * @author : yuanweiwei
     */
    private OnRefreshListener mOnRefreshListener = refreshLayout -> {
        mPresenter.queryArticleFavoritesPageList(paramMap, true);
    };

    /**
     * @description : TODO 上拉加载监听
     * @author : yuanweiwei
     */
    private OnLoadMoreListener mOnLoadMoreListener = refreshLayout -> {
        mPresenter.queryArticleFavoritesPageList(paramMap, false);
    };


    @OnTextChanged(R2.id.et_search_text)
    void onTextChanged(CharSequence text){
        //内容改变监听
        if (mTvContentCount.getText().length() > 0) {
            paramMap.put("title",mTvContentCount.getText().toString());
        }else{
            paramMap.remove("title");
        }
        mPresenter.queryArticleFavoritesPageList(paramMap, true);
    }


    /**
     * @return
     * @description 初始化骨架屏
     * @date: 2020/8/10 16:56
     * @author: YIWUANYUAN
     */
    private void initSkeletonScreen() {
        //初始化数据
        mPresenter.queryArticleFavoritesPageList(paramMap, true);
        mSkeletonScreen = Skeleton.bind(mRecyclerView)
                .adapter(mAdapter)
                .shimmer(true)
                .angle(20)
                .frozen(false)
                .duration(1200)
                .count(15)
                .load(R.layout.user_item_skeleton_metting)
                .show(); //default count is 10
    }



    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }


    public Activity getActivity() {
        return this;
    }

    @Override
    public SkeletonScreen skeletonScreen() {
        return mSkeletonScreen;
    }

    @Override
    public MultipleStatusView multipleStatusView() {
        return mMultipleStatusView;
    }

    @Override
    public RefreshLayout refreshLayout() {
        return mRefreshLayout;
    }

    @Override
    public void cancelArticleFavoritesCallBack() {
        mAdapter.removePosition(removePosition);
    }
}