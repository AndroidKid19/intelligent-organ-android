package com.yway.scomponent.organ.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.mvp.contract.MyMeetingContract;
import com.yway.scomponent.organ.mvp.model.MyMeetingModel;
import com.yway.scomponent.organ.mvp.model.entity.ConferenceBean;
import com.yway.scomponent.organ.mvp.model.entity.MeetingRecordBean;
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
 * Created by MVPArmsTemplate on 2021/12/07 16:19
 * ================================================
 */
@Module
//构建MyMeetingModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class MyMeetingModule {

    @Binds
    abstract MyMeetingContract.Model bindMyMeetingModel(MyMeetingModel model);

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(MyMeetingContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }

    @ActivityScope
    @Provides
    static List<Object> provideList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static ConfirmMeetingAdapter provideAdapter(List<Object> list, MyMeetingContract.View iview) {
        ConfirmMeetingAdapter adapter = new ConfirmMeetingAdapter(list);
        adapter.setOnItemClickListener((view, viewType, data, position) -> {
            ConferenceBean conferenceBean = (ConferenceBean) data;
            Utils.postcard(RouterHub.HOME_MEETINGDETAILSACTIVITY)
                    .withString("mettingId",conferenceBean.getMeetingRecordId())
                    .withInt("pageFrom",6)
                    .navigation(iview.getActivity());
        });
        return adapter;
    }
}