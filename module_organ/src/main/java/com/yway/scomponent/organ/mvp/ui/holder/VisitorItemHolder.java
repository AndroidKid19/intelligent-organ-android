package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.jess.arms.base.BaseHolder;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.mvp.model.entity.VisitorRecordBean;

import butterknife.BindView;

/**
 * ================================================
 * 智慧访客
 * ================================================
 */
public class VisitorItemHolder extends BaseHolder<VisitorRecordBean> {

    @BindView(R2.id.tv_time_name)
    AppCompatTextView mTvTime;

    @BindView(R2.id.tv_user_name)
    AppCompatTextView mTvUserName;

    @BindView(R2.id.tv_phone)
    AppCompatTextView mTvPhone;

    @BindView(R2.id.tv_organ_name)
    AppCompatTextView mTvOrganName;
    @BindView(R2.id.tv_status)
    AppCompatTextView mTvStatus;

    public VisitorItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(VisitorRecordBean data, int position) {
        mTvTime.setText(data.getCreateTime());
        mTvUserName.setText(data.getName());
        mTvPhone.setText(data.getCellPhone());
        mTvOrganName.setText(data.getOrgTitle());
        if (data.getVisitStatus() == 1){
            mTvStatus.setText("已到访");
            mTvTime.setText(data.getVisitStatus());
        }
        itemView.setOnClickListener(this);
    }

    @Override
    protected void onRelease() {
    }
}
