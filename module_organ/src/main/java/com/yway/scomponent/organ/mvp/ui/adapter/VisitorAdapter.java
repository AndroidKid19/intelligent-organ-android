
package com.yway.scomponent.organ.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.mvp.ui.holder.VisitorItemHolder;

import java.util.List;

/**
 * ================================================
 * 智慧访客
 * ================================================
 */
public class VisitorAdapter extends DefaultAdapter<Object> {
    public VisitorAdapter(List<Object> infos) {
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
        return new VisitorItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.organ_item_visitor_record;
    }
}
