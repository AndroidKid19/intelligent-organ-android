
package com.yway.scomponent.organ.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.mvp.model.entity.RoomDetailsBean;
import com.yway.scomponent.organ.mvp.ui.holder.ConferenceRoomItemHolder;

import java.util.List;

/**
 * ================================================
 * 会议室列表适配器
 * ================================================
 */
public class ConferenceRoomAdapter extends DefaultAdapter<RoomDetailsBean> {
    public ConferenceRoomAdapter(List<RoomDetailsBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<RoomDetailsBean> getHolder(View v, int viewType) {
        return new ConferenceRoomItemHolder(v);
    }


    @Override
    public int getLayoutId(int viewType) {
        return R.layout.organ_home_item_conference_room;
    }
}
