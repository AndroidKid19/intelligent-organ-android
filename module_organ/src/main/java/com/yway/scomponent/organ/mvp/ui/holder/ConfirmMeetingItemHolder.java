
package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.jess.arms.base.BaseHolder;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.mvp.model.entity.ConferenceBean;
import com.yway.scomponent.organ.mvp.model.entity.MeetingRecordBean;

import butterknife.BindView;

/**
 * ================================================
 * 已同意会议
 * ================================================
 */
public class ConfirmMeetingItemHolder extends BaseHolder<Object> {
    @BindView(R2.id.tv_cof_date)
    AppCompatTextView mTvConfDate;
    @BindView(R2.id.tv_cof_time)
    AppCompatTextView mTvConfTime;
    @BindView(R2.id.tv_cof_title)
    AppCompatTextView mTvConfTitle;
    @BindView(R2.id.tv_cof_location)
    AppCompatTextView mTvConfLocation;
    @BindView(R2.id.tv_cof_remark)
    AppCompatTextView mTvConfRemark;

    public ConfirmMeetingItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(Object data, int position) {
        ConferenceBean conferenceBean = (ConferenceBean) data;
        mTvConfDate.setText(conferenceBean.getIsToDay());
        mTvConfTime.setText(conferenceBean.getStartTime());
        mTvConfTitle.setText(conferenceBean.getMeetingSubject());
        mTvConfLocation.setText(conferenceBean.getMeetingName());
        mTvConfRemark.setText(conferenceBean.getMeetingRemark());
        itemView.setOnClickListener(this);
    }

    @Override
    protected void onRelease() {
    }
}
