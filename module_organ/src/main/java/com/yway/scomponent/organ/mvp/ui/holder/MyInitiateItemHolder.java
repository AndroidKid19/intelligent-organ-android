package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.jess.arms.base.BaseHolder;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.mvp.model.entity.MeetingRecordBean;

import butterknife.BindView;

/**
 * ================================================
 * 预约列表
 * ================================================
 */
public class MyInitiateItemHolder extends BaseHolder<MeetingRecordBean> {
    /**
     * 发起人
     * */
    @BindView(R2.id.tv_create_org)
    AppCompatTextView mTvCreateOrg;
    /**
     * 状态
     *  ‘1’：待审批；  ‘2’：通过；  ‘3’：驳回
     * */
    @BindView(R2.id.tv_apply_state)
    AppCompatTextView mTvApplyState;
    /**
     * 会议室
     * */
    @BindView(R2.id.tv_loation_name)
    AppCompatTextView mTvLoationName;
    /**
     * 主题
     * */
    @BindView(R2.id.tv_room_name)
    AppCompatTextView mTvRoomName;
    /**
     * 时间
     * */
    @BindView(R2.id.tv_time_name)
    AppCompatTextView mTvTimeName;


    public MyInitiateItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(MeetingRecordBean data, int position) {
        mTvCreateOrg.setText(Utils.appendStr("发起人:",data.getCreateName()));
        mTvLoationName.setText(data.getMeetingRoomName());
        mTvRoomName.setText(data.getMeetingSubject());
        mTvTimeName.setText(data.getMeetingStartTime());

        switch (data.getApprovalStatus()){
            case 1:
                mTvApplyState.setText("待审批");
                mTvApplyState.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.public_color_background_gradual_end));
                break;
            case 2:
                mTvApplyState.setText("已通过");
                mTvApplyState.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.public_color_text_success));
                break;
            case 3:
                mTvApplyState.setText("已驳回");
                mTvApplyState.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.public_color_text_sign));
                break;
        }
        itemView.setOnClickListener(this);
    }

    @Override
    protected void onRelease() {
    }
}
