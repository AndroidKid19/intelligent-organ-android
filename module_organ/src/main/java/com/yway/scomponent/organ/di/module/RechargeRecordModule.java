package com.yway.scomponent.organ.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.yway.scomponent.organ.mvp.contract.CanteenContract;
import com.yway.scomponent.organ.mvp.contract.RechargeRecordContract;
import com.yway.scomponent.organ.mvp.model.RechargeRecordModel;
import com.yway.scomponent.organ.mvp.model.entity.RechargeRecordBean;
import com.yway.scomponent.organ.mvp.ui.adapter.RechargeRecordAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/02/25 15:36
 * ================================================
 */
@Module
//构建RechargeRecordModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class RechargeRecordModule {

    @Binds
    abstract RechargeRecordContract.Model bindRechargeRecordModel(RechargeRecordModel model);

    @FragmentScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(RechargeRecordContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }

    @FragmentScope
    @Provides
    static List<RechargeRecordBean> provideList() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    static RechargeRecordAdapter provideAdapter(List<RechargeRecordBean> list, RechargeRecordContract.View iview) {
        RechargeRecordAdapter adapter = new RechargeRecordAdapter(list);
        return adapter;
    }
}