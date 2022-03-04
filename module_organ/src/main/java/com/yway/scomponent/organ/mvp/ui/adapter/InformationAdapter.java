
package com.yway.scomponent.organ.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.mvp.model.entity.MeetingRecordBean;
import com.yway.scomponent.organ.mvp.model.entity.MessageBean;
import com.yway.scomponent.organ.mvp.ui.holder.InformationItemHolder;

import java.util.List;

/**
 * ================================================
 * 资讯
 * ================================================
 */
public class InformationAdapter extends DefaultAdapter<MessageBean> {
    public InformationAdapter(List<MessageBean> infos) {
        super(infos);
    }

    /**
     * 刷新
     *
     * @return
     */
    public void refreshData(List<MessageBean> data) {
        //校验是否标签是否存在列表中，如果存在列表中则返回下标位置
        this.mInfos = data;
        notifyDataSetChanged();
    }


    @Override
    public BaseHolder<MessageBean> getHolder(View v, int viewType) {
        return new InformationItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.organ_home_item_meg;
    }
}
