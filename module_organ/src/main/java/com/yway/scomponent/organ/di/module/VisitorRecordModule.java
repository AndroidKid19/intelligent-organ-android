package com.yway.scomponent.organ.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;
import com.yway.scomponent.organ.mvp.contract.VisitorRecordContract;
import com.yway.scomponent.organ.mvp.model.VisitorRecordModel;
import com.yway.scomponent.organ.mvp.model.entity.VisitorRecordBean;
import com.yway.scomponent.organ.mvp.ui.adapter.VisitorAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/01/10 10:31
 * ================================================
 */
@Module
//构建VisitorRecordModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class VisitorRecordModule {

    @Binds
    abstract VisitorRecordContract.Model bindVisitorRecordModel(VisitorRecordModel model);

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(VisitorRecordContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }

    @ActivityScope
    @Provides
    static List<VisitorRecordBean> provideList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static VisitorAdapter provideAdapter(List<VisitorRecordBean> list, VisitorRecordContract.View iview) {
        VisitorAdapter adapter = new VisitorAdapter(list);
        return adapter;
    }
}