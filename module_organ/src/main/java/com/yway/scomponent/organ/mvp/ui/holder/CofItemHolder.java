package com.yway.scomponent.organ.mvp.ui.holder;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import com.jess.arms.base.BaseHolder;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.mvp.model.entity.ConferenceBean;
import butterknife.BindView;

/**
 * ================================================
 * 会议
 * ================================================
 */
public class CofItemHolder extends BaseHolder<Object> {
    @BindView(R2.id.view_metting)
    View mView;
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

    public CofItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(Object data, int position) {
        if (data instanceof ConferenceBean){
            ConferenceBean conferenceBean = (ConferenceBean) data;
            mTvConfDate.setText(conferenceBean.getIsToDay());
            mTvConfTime.setText(conferenceBean.getStartTime());
            mTvConfTitle.setText(conferenceBean.getMeetingSubject());
            mTvConfLocation.setText(conferenceBean.getMeetingName());
            mTvConfRemark.setText(conferenceBean.getMeetingRemark());
            mView.setOnClickListener(this);
        }
    }

    @Override
    protected void onRelease() {
    }
}
