package com.yway.scomponent.user.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.yway.scomponent.commonsdk.BuildConfig;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.user.mvp.contract.CollectContract;
import com.yway.scomponent.user.mvp.model.CollectModel;
import com.yway.scomponent.user.mvp.model.entity.ArticleBean;
import com.yway.scomponent.user.mvp.ui.adapter.CollectAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/02/11 14:22
 * ================================================
 */
@Module
//构建CollectModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class CollectModule {

    @Binds
    abstract CollectContract.Model bindCollectModel(CollectModel model);

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(CollectContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }

    @ActivityScope
    @Provides
    static List<Object> provideList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static CollectAdapter provideAdapter(List<Object> list, CollectContract.View view) {
        CollectAdapter adapter = new CollectAdapter(list);

        return adapter;
    }
}