package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.yway.scomponent.organ.mvp.model.entity.MessageTitleBean;

/**
 * ================================================
 * 消息 标题
 * ================================================
 */
public class MsgTitleItemHolder extends BaseHolder<Object> {


    public MsgTitleItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(Object data, int position) {
        MessageTitleBean messageTitleBean = (MessageTitleBean) data;
    }

    @Override
    protected void onRelease() {
    }
}
