package com.yway.scomponent.organ.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.FragmentScope;
import com.yway.scomponent.organ.mvp.contract.AfterPrepareContract;
import com.yway.scomponent.organ.mvp.model.AfterPrepareModel;
import com.yway.scomponent.organ.mvp.model.entity.MeetingRecordBean;
import com.yway.scomponent.organ.mvp.ui.adapter.AfterPrepareAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/02/09 19:58
 * ================================================
 */
@Module
//构建AfterPrepareModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class AfterPrepareModule {

    @Binds
    abstract AfterPrepareContract.Model bindAfterPrepareModel(AfterPrepareModel model);

    @FragmentScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(AfterPrepareContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }

    @FragmentScope
    @Provides
    static List<MeetingRecordBean> provideList() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    static AfterPrepareAdapter provideAdapter(List<MeetingRecordBean> list, AfterPrepareContract.View iview) {
        AfterPrepareAdapter adapter = new AfterPrepareAdapter(list);

        return adapter;
    }
}