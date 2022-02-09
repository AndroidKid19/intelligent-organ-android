package com.yway.scomponent.organ.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.mvp.contract.ApprovedContract;
import com.yway.scomponent.organ.mvp.contract.MyInitiateContract;
import com.yway.scomponent.organ.mvp.model.MyInitiateModel;
import com.yway.scomponent.organ.mvp.ui.adapter.ApprovedAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/01/01 11:01
 * ================================================
 */
@Module
//构建MyInitiateModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class MyInitiateModule {

    @Binds
    abstract MyInitiateContract.Model bindMyInitiateModel(MyInitiateModel model);

    @FragmentScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(MyInitiateContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }

    @FragmentScope
    @Provides
    static List<Object> provideList() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    static ApprovedAdapter provideAdapter(List<Object> list, MyInitiateContract.View iview) {
        ApprovedAdapter adapter = new ApprovedAdapter(list);
        adapter.setOnItemClickListener((view, viewType, data, position) -> {
            Utils.navigation(iview.getActivity(), RouterHub.HOME_MEETINGDETAILSACTIVITY);
        });
        return adapter;
    }
}