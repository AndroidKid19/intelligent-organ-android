package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;

import com.jess.arms.base.BaseHolder;

/**
 * ================================================
 * 智慧访客
 * ================================================
 */
public class VisitorItemHolder extends BaseHolder<Object> {

    public VisitorItemHolder(View itemView) {
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
