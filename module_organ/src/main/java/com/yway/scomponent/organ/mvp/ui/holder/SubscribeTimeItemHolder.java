package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.StringUtils;
import com.jess.arms.base.BaseHolder;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.mvp.model.entity.SubscribeTimeBean;
import com.yway.scomponent.organ.mvp.ui.adapter.SubscribeTimeAdapter;

import butterknife.BindView;

/**
 * ================================================
 * 会议室信息
 * ================================================
 */
public class SubscribeTimeItemHolder extends BaseHolder<SubscribeTimeBean> {
    @BindView(R2.id.tv_am_pm)
    AppCompatTextView mTvAmPm;

    @BindView(R2.id.tv_time)
    AppCompatTextView mTvTime;

    @BindView(R2.id.view_time)
    View mViewTime;

    private SubscribeTimeAdapter mAdapter;

    public SubscribeTimeItemHolder(View itemView, SubscribeTimeAdapter adapter) {
        super(itemView);
        this.mAdapter = adapter;
    }

    @Override
    public void setData(SubscribeTimeBean data, int position) {
        mTvAmPm.setText(data.getKey());
        mTvTime.setText(data.getTime());
        itemView.setOnClickListener(this);

        if (!StringUtils.isEmpty(data.getWhetherAppointment()) && data.getWhetherAppointment().equals("1")){
            mViewTime.setBackground(itemView.getContext().getDrawable(R.drawable.organ_shape_subscribe_time_disable));
            mTvAmPm.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.public_color_text_default));
            mTvTime.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.public_color_text_default));
            return;
        }

        if (position == mAdapter.lastClickPosition) {
            mViewTime.setBackground(itemView.getContext().getDrawable(R.drawable.organ_shape_subscribe_time_checked));
            mTvAmPm.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.public_white));
            mTvTime.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.public_white));
        } else {
            mViewTime.setBackground(itemView.getContext().getDrawable(R.drawable.organ_shape_subscribe_time_normal));
            mTvAmPm.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.public_color_text_default));
            mTvTime.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.public_color_text_default));

        }
    }

    @Override
    protected void onRelease() {
    }
}
