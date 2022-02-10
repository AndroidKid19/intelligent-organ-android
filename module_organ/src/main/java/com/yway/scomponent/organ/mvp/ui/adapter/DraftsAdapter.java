
package com.yway.scomponent.organ.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.mvp.model.entity.MeetingRecordBean;
import com.yway.scomponent.organ.mvp.ui.holder.DraftsItemHolder;

import java.util.List;

/**
 * ================================================
 * 草稿箱
 * ================================================
 */
public class DraftsAdapter extends DefaultAdapter<MeetingRecordBean> {
    public DraftsAdapter(List<MeetingRecordBean> infos) {
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

    /**
     * 删除
     * @return
     */
    public void remove(int position) {
        //校验是否标签是否存在列表中，如果存在列表中则返回下标位置
        this.mInfos.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public BaseHolder<MeetingRecordBean> getHolder(View v, int viewType) {
        return new DraftsItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.organ_item_drafts;
    }
}
