package com.yway.scomponent.organ.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.FragmentScope;
import com.yway.scomponent.organ.mvp.contract.HomeContract;
import com.yway.scomponent.organ.mvp.model.HomeModel;
import com.yway.scomponent.organ.mvp.model.entity.ConferenceBean;
import com.yway.scomponent.organ.mvp.model.entity.MessageBean;
import com.yway.scomponent.organ.mvp.ui.adapter.InformationAdapter;
import com.yway.scomponent.organ.mvp.ui.adapter.MeetingAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVP on 05/09/2020 17:25
 * <a href="mailto:yuanw9@sina.com">Contact me</a>
 * ================================================
 */
@Module
public abstract class HomeModule {

    @Binds
    abstract HomeContract.Model bindHomeModel(HomeModel model);

    @FragmentScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(HomeContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }

    @FragmentScope
    @Provides
    static List<MessageBean> provideList() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    static InformationAdapter provideAdapter(List<MessageBean> list) {
        InformationAdapter adapter = new InformationAdapter(list);
        return adapter;
    }

    @FragmentScope
    @Provides
    static List<ConferenceBean> provideConferenceBeanList() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    static MeetingAdapter provideMeetingAdapter(List<ConferenceBean> list) {
        MeetingAdapter adapter = new MeetingAdapter(list);
        return adapter;
    }
}