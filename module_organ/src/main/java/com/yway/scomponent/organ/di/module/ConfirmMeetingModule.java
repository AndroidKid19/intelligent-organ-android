package com.yway.scomponent.organ.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.FragmentScope;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.mvp.contract.ConfirmMeetingContract;
import com.yway.scomponent.organ.mvp.model.ConfirmMeetingModel;
import com.yway.scomponent.organ.mvp.ui.adapter.ConfirmMeetingAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/08 14:49
 * ================================================
 */
@Module
//构建ConfirmMeetingModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class ConfirmMeetingModule {

    @Binds
    abstract ConfirmMeetingContract.Model bindConfirmMeetingModel(ConfirmMeetingModel model);

    @FragmentScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(ConfirmMeetingContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }

    @FragmentScope
    @Provides
    static List<Object> provideList() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    static ConfirmMeetingAdapter provideAdapter(List<Object> list, ConfirmMeetingContract.View iview) {
        ConfirmMeetingAdapter adapter = new ConfirmMeetingAdapter(list);
        adapter.setOnItemClickListener((view, viewType, data, position) -> {
            Utils.navigation(iview.getActivity(), RouterHub.HOME_MEETINGDETAILSACTIVITY);
        });
        return adapter;
    }
}