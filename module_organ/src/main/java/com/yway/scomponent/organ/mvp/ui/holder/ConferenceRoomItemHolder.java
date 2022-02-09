
package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.utils.ArmsUtils;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.mvp.model.entity.RoomDetailsBean;
import com.yway.scomponent.organ.mvp.model.entity.SubscribeTimeBean;
import com.yway.scomponent.organ.mvp.ui.adapter.SubscribeTimeAdapter;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

/**
 * ================================================
 * 会议室信息
 * ================================================
 */
public class ConferenceRoomItemHolder extends BaseHolder<RoomDetailsBean> {
    @BindView(R2.id.tv_room_name)
    AppCompatTextView mTvRoomName;

    @BindView(R2.id.tv_room_desc)
    AppCompatTextView mTvRoomDesc;

    @BindView(R2.id.recycle_view_time)
    RecyclerView mRecyclerViewTime;

    @BindView(R2.id.btn_subscribe)
    AppCompatButton mBtnSubscribe;


    public ConferenceRoomItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(RoomDetailsBean data, int position) {
        mTvRoomName.setText(data.getName());
        mTvRoomDesc.setText(Utils.appendStr(data.getLocation(), " · 可容纳", data.getSeatsNumber(), "人"));
        //初始化时间点
        SubscribeTimeAdapter subscribeTimeAdapter = new SubscribeTimeAdapter(data.getMeetingTimeBatRspBOList());
        ArmsUtils.configRecyclerView(mRecyclerViewTime, new GridLayoutManager(itemView.getContext(), 4));
        mRecyclerViewTime.setAdapter(subscribeTimeAdapter);
        subscribeTimeAdapter.setOnItemClickListener((view, viewType, data1, position1) -> {
            subscribeTimeAdapter.singleChoose(position1);
            data.setSubscribeTimeBean((SubscribeTimeBean) data1);
        });
        mBtnSubscribe.setOnClickListener(this);

    }

    @Override
    protected void onRelease() {
    }
}
