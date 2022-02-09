package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;

import com.jess.arms.base.BaseHolder;

/**
 * ================================================
 * 预约审核
 * ================================================
 */
public class DraftsItemHolder extends BaseHolder<Object> {

    public DraftsItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(Object data, int position) {

        itemView.setOnClickListener(this);
    }

    @Override
    protected void onRelease() {
    }
}
