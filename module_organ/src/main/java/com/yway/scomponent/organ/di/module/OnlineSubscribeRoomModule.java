package com.yway.scomponent.organ.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.organ.mvp.contract.OnlineSubscribeRoomContract;
import com.yway.scomponent.organ.mvp.model.OnlineSubscribeRoomModel;
import com.yway.scomponent.organ.mvp.model.entity.RoomDetailsBean;
import com.yway.scomponent.organ.mvp.ui.adapter.ConferenceRoomAdapter;

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
//构建OnlineSubscribeRoomModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class OnlineSubscribeRoomModule {

    @Binds
    abstract OnlineSubscribeRoomContract.Model bindOnlineSubscribeRoomModel(OnlineSubscribeRoomModel model);

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(OnlineSubscribeRoomContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }

    @ActivityScope
    @Provides
    static List<RoomDetailsBean> provideList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static ConferenceRoomAdapter provideAdapter(List<RoomDetailsBean> list) {
        ConferenceRoomAdapter adapter = new ConferenceRoomAdapter(list);
        return adapter;
    }
}