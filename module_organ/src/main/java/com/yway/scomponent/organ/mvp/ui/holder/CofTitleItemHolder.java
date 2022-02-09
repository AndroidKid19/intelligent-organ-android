package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.yway.scomponent.organ.mvp.model.entity.ConferenceTitleBean;

/**
 * ================================================
 * 会议 标题
 * ================================================
 */
public class CofTitleItemHolder extends BaseHolder<Object> {


    public CofTitleItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(Object data, int position) {
        ConferenceTitleBean conferenceTitleBean = (ConferenceTitleBean) data;
    }

    @Override
    protected void onRelease() {
    }
}
