package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.yway.scomponent.organ.mvp.model.entity.MessageBean;
import com.yway.scomponent.organ.mvp.model.entity.MessageTitleBean;

/**
 * ================================================
 * 消息
 * ================================================
 */
public class MsgItemHolder extends BaseHolder<Object> {


    public MsgItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(Object data, int position) {
        MessageBean messageBean = (MessageBean) data;
    }

    @Override
    protected void onRelease() {
    }
}
