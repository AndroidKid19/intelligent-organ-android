
package com.yway.scomponent.organ.mvp.ui.adapter;

import android.view.View;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.mvp.model.entity.SubscribeTimeBean;
import com.yway.scomponent.organ.mvp.ui.holder.SubscribeTimeItemHolder;
import java.util.List;

/**
 * ================================================
 * 会议室列表适配器
 * ================================================
 */
public class SubscribeTimeAdapter extends DefaultAdapter<SubscribeTimeBean> {
    public int lastClickPosition=-1;
    public SubscribeTimeAdapter(List<SubscribeTimeBean> infos) {
        super(infos);
    }

    public void singleChoose(int position){
        lastClickPosition = position;
        notifyDataSetChanged();
    }

    @Override
    public BaseHolder<SubscribeTimeBean> getHolder(View v, int viewType) {
        return new SubscribeTimeItemHolder(v,this);
    }


    @Override
    public int getLayoutId(int viewType) {
        return R.layout.organ_home_item_conference_room_time;
    }
}
