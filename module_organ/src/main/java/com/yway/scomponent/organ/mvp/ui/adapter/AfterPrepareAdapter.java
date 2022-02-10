
package com.yway.scomponent.organ.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.mvp.model.entity.MeetingRecordBean;
import com.yway.scomponent.organ.mvp.ui.holder.AfterPrepareItemHolder;
import com.yway.scomponent.organ.mvp.ui.holder.BeforePrepareItemHolder;

import java.util.List;

/**
 * ================================================
 * 已准备
 * ================================================
 */
public class AfterPrepareAdapter extends DefaultAdapter<MeetingRecordBean> {
    public AfterPrepareAdapter(List<MeetingRecordBean> infos) {
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
        return new AfterPrepareItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.organ_item_after_prepare;
    }
}
