package com.yway.scomponent.organ.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.mvp.contract.DraftsContract;
import com.yway.scomponent.organ.mvp.model.DraftsModel;
import com.yway.scomponent.organ.mvp.model.entity.MeetingRecordBean;
import com.yway.scomponent.organ.mvp.ui.adapter.DraftsAdapter;

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
//构建DraftsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class DraftsModule {

    @Binds
    abstract DraftsContract.Model bindDraftsModel(DraftsModel model);

    @FragmentScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(DraftsContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }

    @FragmentScope
    @Provides
    static List<MeetingRecordBean> provideList() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    static DraftsAdapter provideAdapter(List<MeetingRecordBean> list, DraftsContract.View iview) {
        DraftsAdapter adapter = new DraftsAdapter(list);
        return adapter;
    }
}