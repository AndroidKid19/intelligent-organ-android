package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.jess.arms.base.BaseHolder;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.mvp.model.entity.ConferenceBean;
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
    @BindView(R2.id.tv_conf_count)
    AppCompatTextView mTvMetingCount;

    public CofTitleItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(Object data, int position) {
        if (data instanceof ConferenceTitleBean){
        ConferenceTitleBean conferenceTitleBean = (ConferenceTitleBean) data;
        mTvMetingCount.setText(Utils.appendStr("(",conferenceTitleBean.getMetingCount(),")"));
        mTvQueryAll.setOnClickListener(this);
        }
    }

    @Override
    protected void onRelease() {
    }
}
