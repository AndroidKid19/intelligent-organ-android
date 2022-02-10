package com.yway.scomponent.organ.mvp.ui.holder;

import android.view.View;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.jess.arms.base.BaseHolder;
import com.yway.scomponent.commonsdk.utils.Utils;
import com.yway.scomponent.organ.R2;
import com.yway.scomponent.organ.mvp.model.entity.MeetingRecordBean;

import butterknife.BindView;

/**
 * ================================================
 * 已准备
 * ================================================
 */
public class AfterPrepareItemHolder extends BaseHolder<MeetingRecordBean> {
    /**
     * 发起机构
     * */
    @BindView(R2.id.tv_create_org)
    AppCompatTextView mTvCreateOrg;
    /**
     * 发起人
     * */
    @BindView(R2.id.tv_create_user)
    AppCompatTextView mTvCreateUser;
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

    public AfterPrepareItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(MeetingRecordBean data, int position) {
        mTvCreateOrg.setText(Utils.appendStr("发起人:",data.getOrgShortName()));
        mTvLoationName.setText(data.getMeetingRoomName());
        mTvRoomName.setText(data.getMeetingSubject());
        mTvTimeName.setText(data.getCreateTime());
        mTvCreateUser.setText(Utils.appendStr(data.getCreateName()," ",data.getCellPhone()));
        mTvCreateUser.setOnClickListener(this);
        itemView.setOnClickListener(this);
    }

    @Override
    protected void onRelease() {
    }
}
