
package com.yway.scomponent.organ.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.mvp.ui.holder.ApprovedItemHolder;
import com.yway.scomponent.organ.mvp.ui.holder.SubscribeApplyItemHolder;

import java.util.List;

/**
 * ================================================
 * 已审核
 * ================================================
 */
public class ApprovedAdapter extends DefaultAdapter<Object> {
    public ApprovedAdapter(List<Object> infos) {
        super(infos);
    }

    /**
     * 刷新
     * @return
     */
    public void refreshData(List<Object> data) {
        //校验是否标签是否存在列表中，如果存在列表中则返回下标位置
        this.mInfos = data;
        notifyDataSetChanged();
    }

    @Override
    public BaseHolder<Object> getHolder(View v, int viewType) {
        return new ApprovedItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.organ_item_admin_approved;
    }
}
