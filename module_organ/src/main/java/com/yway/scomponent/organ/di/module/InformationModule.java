package com.yway.scomponent.organ.di.module;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.jess.arms.di.scope.FragmentScope;
import com.yway.scomponent.commonsdk.BuildConfig;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.mvp.contract.HomeContract;
import com.yway.scomponent.organ.mvp.contract.InformationContract;
import com.yway.scomponent.organ.mvp.model.InformationModel;
import com.yway.scomponent.organ.mvp.model.entity.MessageBean;
import com.yway.scomponent.organ.mvp.ui.adapter.HomeAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/02/10 14:33
 * ================================================
 */
@Module
//构建InformationModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class InformationModule {

    @Binds
    abstract InformationContract.Model bindInformationModel(InformationModel model);

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(InformationContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }

    @ActivityScope
    @Provides
    static List<Object> provideList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static HomeAdapter provideAdapter(List<Object> list,InformationContract.View view) {
        HomeAdapter adapter = new HomeAdapter(list);
        adapter.setOnItemClickListener((view1, viewType, data, position) -> {
            MessageBean messageBean = (MessageBean) data;
            Utils.postcard(RouterHub.HOME_WEBVIEWACTIVITY)
                    .withString(RouterHub.PARAM_WEBVIEWXURL,Utils.appendStr(BuildConfig.H5_HOST_ROOT,"articleMobile?id=",messageBean.getId()))
                    .withInt("pageFrom",2)
                    .withString("articleId",messageBean.getId())
                    .navigation(view1.getContext());

        });
        return adapter;
    }
}