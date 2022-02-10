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
 * 预约审核
 * ================================================
 */
public class DraftsItemHolder extends BaseHolder<MeetingRecordBean> {
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


    public DraftsItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(MeetingRecordBean data, int position) {
        mTvCreateOrg.setText(Utils.appendStr("发起人:",data.getCreateName()));
        mTvLoationName.setText(data.getMeetingRoomName());
        mTvRoomName.setText(data.getMeetingSubject());
        mTvTimeName.setText(data.getMeetingStartTime());

        mTvApplyState.setOnClickListener(this);
        itemView.setOnClickListener(this);
    }

    @Override
    protected void onRelease() {
    }
}
