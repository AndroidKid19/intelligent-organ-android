package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;

import com.jess.arms.base.BaseHolder;

/**
 * ================================================
 * 预约审核
 * ================================================
 */
public class SubscribeApplyItemHolder extends BaseHolder<Object> {

    public SubscribeApplyItemHolder(View itemView) {
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
