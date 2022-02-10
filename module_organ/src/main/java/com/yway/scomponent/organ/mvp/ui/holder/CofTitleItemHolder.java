package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.jess.arms.base.BaseHolder;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.mvp.model.entity.ConferenceTitleBean;

import butterknife.BindView;

/**
 * ================================================
 * 会议 标题
 * ================================================
 */
public class CofTitleItemHolder extends BaseHolder<Object> {
    @BindView(R2.id.tv_queryall)
    AppCompatTextView mTvQueryAll;

    public CofTitleItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(Object data, int position) {
        ConferenceTitleBean conferenceTitleBean = (ConferenceTitleBean) data;
        mTvQueryAll.setOnClickListener(this);
    }

    @Override
    protected void onRelease() {
    }
}
