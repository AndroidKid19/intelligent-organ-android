package com.yway.scomponent.organ.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 我的预约
 * 会议列表信息
 */
public class MeetingRecordBean implements Parcelable {
    private List<MeetingRecordBean> rows;
    private List<MeetingRecordBean> list;
    private boolean isConfIng;

    public boolean isConfIng() {
        return isConfIng;
    }

    public void setConfIng(boolean confIng) {
        isConfIng = confIng;
    }
    /**
     * ID
     * */
    private String id;
    /**
     * 会议状态
     * 0待提交审批（草稿），1已提交审批（待审批），2审批已通过，3审批被驳回
     * */
    private int approvalStatus;
    /**
     *发起人
     * */
    private String createName;
    /**
     * 会议室
     * */
    private String meetingRoomName;
    /**
     * 主题
     * */
    private String meetingSubject;
    /**
     * 开始时间
     * */
    private String meetingStartTime;
    /**
     * 结束时间
     * */
    private String meetingEndTime;
    /**
     * 手机号
     * */
    private String cellPhone;
    /**
     * 发起单位
     * */
    private String orgShortName;
    /**
     * 申请时间
     * */
    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<MeetingRecordBean> getList() {
        return list;
    }

    public void setList(List<MeetingRecordBean> list) {
        this.list = list;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getOrgShortName() {
        return orgShortName;
    }

    public void setOrgShortName(String orgShortName) {
        this.orgShortName = orgShortName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(int approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getMeetingRoomName() {
        return meetingRoomName;
    }

    public void setMeetingRoomName(String meetingRoomName) {
        this.meetingRoomName = meetingRoomName;
    }

    public String getMeetingSubject() {
        return meetingSubject;
    }

    public void setMeetingSubject(String meetingSubject) {
        this.meetingSubject = meetingSubject;
    }

    public String getMeetingStartTime() {
        return meetingStartTime;
    }

    public void setMeetingStartTime(String meetingStartTime) {
        this.meetingStartTime = meetingStartTime;
    }

    public String getMeetingEndTime() {
        return meetingEndTime;
    }

    public void setMeetingEndTime(String meetingEndTime) {
        this.meetingEndTime = meetingEndTime;
    }

    public List<MeetingRecordBean> getRows() {
        return rows;
    }

    public void setRows(List<MeetingRecordBean> rows) {
        this.rows = rows;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.rows);
        dest.writeString(this.id);
        dest.writeInt(this.approvalStatus);
        dest.writeString(this.createName);
        dest.writeString(this.meetingRoomName);
        dest.writeString(this.meetingSubject);
        dest.writeString(this.meetingStartTime);
        dest.writeString(this.meetingEndTime);
    }

    public void readFromParcel(Parcel source) {
        this.rows = source.createTypedArrayList(MeetingRecordBean.CREATOR);
        this.id = source.readString();
        this.approvalStatus = source.readInt();
        this.createName = source.readString();
        this.meetingRoomName = source.readString();
        this.meetingSubject = source.readString();
        this.meetingStartTime = source.readString();
        this.meetingEndTime = source.readString();
    }

    public MeetingRecordBean() {
    }

    protected MeetingRecordBean(Parcel in) {
        this.rows = in.createTypedArrayList(MeetingRecordBean.CREATOR);
        this.id = in.readString();
        this.approvalStatus = in.readInt();
        this.createName = in.readString();
        this.meetingRoomName = in.readString();
        this.meetingSubject = in.readString();
        this.meetingStartTime = in.readString();
        this.meetingEndTime = in.readString();
    }

    public static final Creator<MeetingRecordBean> CREATOR = new Creator<MeetingRecordBean>() {
        @Override
        public MeetingRecordBean createFromParcel(Parcel source) {
            return new MeetingRecordBean(source);
        }

        @Override
        public MeetingRecordBean[] newArray(int size) {
            return new MeetingRecordBean[size];
        }
    };
}
