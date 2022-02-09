package com.yway.scomponent.user.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.yway.scomponent.commonsdk.core.RouterHub;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.user.mvp.contract.MessageContract;
import com.yway.scomponent.user.mvp.model.MessageModel;
import com.yway.scomponent.user.mvp.model.entity.MessageBean;
import com.yway.scomponent.user.mvp.ui.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created on 07/21/2020 13:36
 * ================================================
 */
@Module
public abstract class MessageModule {

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(MessageContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }

    @ActivityScope
    @Provides
    static List<MessageBean> provideList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static MessageAdapter provideAdapter(List<MessageBean> list) {
        MessageAdapter messageAdapter =  new MessageAdapter(list);
        messageAdapter.setOnItemClickListener((view, viewType, data, position) ->
                Utils.postcard(RouterHub.USER_MESSAGEDETAILSACTIVITY)
                .withString(RouterHub.PARAM_MESSAGE_CONTENT,((MessageBean)data).getContent())
                .navigation(view.getContext()));
        return messageAdapter;
    }

    @Binds
    abstract MessageContract.Model bindMessageModel(MessageModel model);
}