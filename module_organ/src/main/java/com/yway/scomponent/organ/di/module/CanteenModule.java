package com.yway.scomponent.organ.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.organ.mvp.contract.CanteenContract;
import com.yway.scomponent.organ.mvp.model.CanteenModel;
import com.yway.scomponent.organ.mvp.model.entity.RechargeRecordBean;
import com.yway.scomponent.organ.mvp.ui.adapter.RechargeRecordAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/02/25 15:36
 * ================================================
 */
@Module
//构建CanteenModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class CanteenModule {

    @Binds
    abstract CanteenContract.Model bindCanteenModel(CanteenModel model);

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(CanteenContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }

    @ActivityScope
    @Provides
    static List<RechargeRecordBean> provideList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static RechargeRecordAdapter provideAdapter(List<RechargeRecordBean> list, CanteenContract.View iview) {
        RechargeRecordAdapter adapter = new RechargeRecordAdapter(list);
        return adapter;
    }
}