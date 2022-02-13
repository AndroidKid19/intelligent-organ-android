package com.yway.scomponent.organ.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.yway.scomponent.commonsdk.core.AddressCompanyBean;
import com.yway.scomponent.commonsdk.core.UploadFileBean;
import com.yway.scomponent.commonsdk.core.UserInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: intelligent-organ-android
 * @Package: com.yway.scomponent.organ.mvp.model.entity
 * @ClassName: MeetingDetailsBean
 * @Description:
 * @Author: Yuan
 * @CreateDate: 2022/1/10 14:34
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/1/10 14:34
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MeetingDetailsBean implements Parcelable {
    private String id;
    /**
     * 发起人
     */
    private String createName;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 状态
     * 0待提交审批（草稿），1已提交审批（待审批），2审批已通过，3审批被驳
     */
    private String approvalStatus;

    /**
     * 所需设备（字典KEY）
     */
    private String meetingDeviceNeeds;

    /**
     * 报道时间
     */
    private String meetingCheckInTime;
    /**
     * 会议开始时间
     */
    private String meetingStartTime;

    /**
     * 会议结束时间
     */
    private String meetingEndTime;

    /**
     * 会议附件
     */
    private List<FileDetailsBean> meetingFileRspBOList;

    /**
     * 参会组织
     */
    private List<AddressCompanyBean> meetingOrganizationRspBOList;

    /**
     * 参会人员
     */
    private List<UserInfoBean> meetingPersonnelRspBOList;

    /**
     * 会议室ID
     */
    private String meetingRoomId;


    /**
     * 会议室名称
     */
    private String meetingRoomName;

    /**
     * 会议室设置
     */
    private String meetingSet;

    /**
     * 会议主题
     */
    private String meetingSubject;

    /**
     * 会议备注
     */
    private String remark;
    /**
     * 电话
     * */
    private String createCellPhone;
    /**
     * 人数
     * */
    private String seatsNumber;

    /**
     * 位置
     * */
    private String location;


    public String getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(String seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCreateCellPhone() {
        return createCellPhone;
    }

    public void setCreateCellPhone(String createCellPhone) {
        this.createCellPhone = createCellPhone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getMeetingDeviceNeeds() {
        return meetingDeviceNeeds;
    }

    public void setMeetingDeviceNeeds(String meetingDeviceNeeds) {
        this.meetingDeviceNeeds = meetingDeviceNeeds;
    }

    public String getMeetingCheckInTime() {
        return meetingCheckInTime;
    }

    public void setMeetingCheckInTime(String meetingCheckInTime) {
        this.meetingCheckInTime = meetingCheckInTime;
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

    public List<FileDetailsBean> getMeetingFileRspBOList() {
        return meetingFileRspBOList;
    }

    public void setMeetingFileRspBOList(List<FileDetailsBean> meetingFileRspBOList) {
        this.meetingFileRspBOList = meetingFileRspBOList;
    }

    public List<AddressCompanyBean> getMeetingOrganizationRspBOList() {
        return meetingOrganizationRspBOList;
    }

    public void setMeetingOrganizationRspBOList(List<AddressCompanyBean> meetingOrganizationRspBOList) {
        this.meetingOrganizationRspBOList = meetingOrganizationRspBOList;
    }

    public List<UserInfoBean> getMeetingPersonnelRspBOList() {
        return meetingPersonnelRspBOList;
    }

    public void setMeetingPersonnelRspBOList(List<UserInfoBean> meetingPersonnelRspBOList) {
        this.meetingPersonnelRspBOList = meetingPersonnelRspBOList;
    }

    public String getMeetingRoomId() {
        return meetingRoomId;
    }

    public void setMeetingRoomId(String meetingRoomId) {
        this.meetingRoomId = meetingRoomId;
    }

    public String getMeetingRoomName() {
        return meetingRoomName;
    }

    public void setMeetingRoomName(String meetingRoomName) {
        this.meetingRoomName = meetingRoomName;
    }

    public String getMeetingSet() {
        return meetingSet;
    }

    public void setMeetingSet(String meetingSet) {
        this.meetingSet = meetingSet;
    }

    public String getMeetingSubject() {
        return meetingSubject;
    }

    public void setMeetingSubject(String meetingSubject) {
        this.meetingSubject = meetingSubject;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.createName);
        dest.writeString(this.createTime);
        dest.writeString(this.approvalStatus);
        dest.writeString(this.meetingDeviceNeeds);
        dest.writeString(this.meetingCheckInTime);
        dest.writeString(this.meetingStartTime);
        dest.writeString(this.meetingEndTime);
        dest.writeTypedList(this.meetingFileRspBOList);
        dest.writeTypedList(this.meetingOrganizationRspBOList);
        dest.writeTypedList(this.meetingPersonnelRspBOList);
        dest.writeString(this.meetingRoomId);
        dest.writeString(this.meetingRoomName);
        dest.writeString(this.meetingSet);
        dest.writeString(this.meetingSubject);
        dest.writeString(this.remark);
        dest.writeString(this.createCellPhone);
        dest.writeString(this.seatsNumber);
        dest.writeString(this.location);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readString();
        this.createName = source.readString();
        this.createTime = source.readString();
        this.approvalStatus = source.readString();
        this.meetingDeviceNeeds = source.readString();
        this.meetingCheckInTime = source.readString();
        this.meetingStartTime = source.readString();
        this.meetingEndTime = source.readString();
        this.meetingFileRspBOList = source.createTypedArrayList(FileDetailsBean.CREATOR);
        this.meetingOrganizationRspBOList = source.createTypedArrayList(AddressCompanyBean.CREATOR);
        this.meetingPersonnelRspBOList = source.createTypedArrayList(UserInfoBean.CREATOR);
        this.meetingRoomId = source.readString();
        this.meetingRoomName = source.readString();
        this.meetingSet = source.readString();
        this.meetingSubject = source.readString();
        this.remark = source.readString();
        this.createCellPhone = source.readString();
        this.seatsNumber = source.readString();
        this.location = source.readString();
    }

    public MeetingDetailsBean() {
    }

    protected MeetingDetailsBean(Parcel in) {
        this.id = in.readString();
        this.createName = in.readString();
        this.createTime = in.readString();
        this.approvalStatus = in.readString();
        this.meetingDeviceNeeds = in.readString();
        this.meetingCheckInTime = in.readString();
        this.meetingStartTime = in.readString();
        this.meetingEndTime = in.readString();
        this.meetingFileRspBOList = in.createTypedArrayList(FileDetailsBean.CREATOR);
        this.meetingOrganizationRspBOList = in.createTypedArrayList(AddressCompanyBean.CREATOR);
        this.meetingPersonnelRspBOList = in.createTypedArrayList(UserInfoBean.CREATOR);
        this.meetingRoomId = in.readString();
        this.meetingRoomName = in.readString();
        this.meetingSet = in.readString();
        this.meetingSubject = in.readString();
        this.remark = in.readString();
        this.createCellPhone = in.readString();
        this.seatsNumber = in.readString();
        this.location = in.readString();
    }

    public static final Creator<MeetingDetailsBean> CREATOR = new Creator<MeetingDetailsBean>() {
        @Override
        public MeetingDetailsBean createFromParcel(Parcel source) {
            return new MeetingDetailsBean(source);
        }

        @Override
        public MeetingDetailsBean[] newArray(int size) {
            return new MeetingDetailsBean[size];
        }
    };
}
