
package com.yway.scomponent.organ.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.mvp.model.entity.MeetingRecordBean;
import com.yway.scomponent.organ.mvp.ui.holder.MyInitiateItemHolder;

import java.util.List;

/**
 * ================================================
 * 我的预约
 * 预约列表
 * ================================================
 */
public class MyInitiateAdapter extends DefaultAdapter<MeetingRecordBean> {
    public MyInitiateAdapter(List<MeetingRecordBean> infos) {
        super(infos);
    }

    /**
     * 刷新
     * @return
     */
    public void refreshData(List<MeetingRecordBean> data) {
        //校验是否标签是否存在列表中，如果存在列表中则返回下标位置
        this.mInfos = data;
        notifyDataSetChanged();
    }

    @Override
    public BaseHolder<MeetingRecordBean> getHolder(View v, int viewType) {
        return new MyInitiateItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.organ_item_admin_approved;
    }
}
