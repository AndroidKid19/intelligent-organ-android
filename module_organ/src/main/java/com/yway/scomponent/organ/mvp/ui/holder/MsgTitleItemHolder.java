package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.jess.arms.base.BaseHolder;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.mvp.model.entity.MessageTitleBean;

import butterknife.BindView;

/**
 * ================================================
 * 消息 标题
 * ================================================
 */
public class MsgTitleItemHolder extends BaseHolder<Object> {

    @BindView(R2.id.tv_more)
    AppCompatTextView mTvMore;

    public MsgTitleItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(Object data, int position) {
        MessageTitleBean messageTitleBean = (MessageTitleBean) data;
        mTvMore.setOnClickListener(this);
    }

    @Override
    protected void onRelease() {
    }
}
