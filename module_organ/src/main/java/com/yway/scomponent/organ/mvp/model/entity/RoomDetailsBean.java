package com.yway.scomponent.organ.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class RoomDetailsBean implements Parcelable {
    private List<RoomDetailsBean> rows;
    /**
     * 会议室ID
     */
    private String id;
    /**
     * 会议日期
     */
    private String meetingDate;
    /**
     * 会议室 截至预约时间
     */
    private String appointmentEndTime;
    /**
     * 会议室 开始预约时间
     */
    private String appointmentStartTime;
    /**
     * 会议室位置
     */
    private String location;
    /**
     * 会议室名称
     */
    private String name;
    /**
     * 会议室容纳人数
     */
    private String seatsNumber;
    /**
     * 间隔时间 30分钟
     */
    private String splitTime;
    /**
     * 记录当前选择的时间
     * */
    private SubscribeTimeBean mSubscribeTimeBean;

    private List<SubscribeTimeBean> meetingTimeBatRspBOList;

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public SubscribeTimeBean getSubscribeTimeBean() {
        return mSubscribeTimeBean;
    }

    public void setSubscribeTimeBean(SubscribeTimeBean subscribeTimeBean) {
        mSubscribeTimeBean = subscribeTimeBean;
    }

    public List<RoomDetailsBean> getRows() {
        return rows;
    }

    public void setRows(List<RoomDetailsBean> rows) {
        this.rows = rows;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppointmentEndTime() {
        return appointmentEndTime;
    }

    public void setAppointmentEndTime(String appointmentEndTime) {
        this.appointmentEndTime = appointmentEndTime;
    }

    public String getAppointmentStartTime() {
        return appointmentStartTime;
    }

    public void setAppointmentStartTime(String appointmentStartTime) {
        this.appointmentStartTime = appointmentStartTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(String seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public String getSplitTime() {
        return splitTime;
    }

    public void setSplitTime(String splitTime) {
        this.splitTime = splitTime;
    }

    public List<SubscribeTimeBean> getMeetingTimeBatRspBOList() {
        return meetingTimeBatRspBOList;
    }

    public void setMeetingTimeBatRspBOList(List<SubscribeTimeBean> meetingTimeBatRspBOList) {
        this.meetingTimeBatRspBOList = meetingTimeBatRspBOList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.rows);
        dest.writeString(this.id);
        dest.writeString(this.meetingDate);
        dest.writeString(this.appointmentEndTime);
        dest.writeString(this.appointmentStartTime);
        dest.writeString(this.location);
        dest.writeString(this.name);
        dest.writeString(this.seatsNumber);
        dest.writeString(this.splitTime);
        dest.writeParcelable(this.mSubscribeTimeBean, flags);
        dest.writeTypedList(this.meetingTimeBatRspBOList);
    }

    public void readFromParcel(Parcel source) {
        this.rows = source.createTypedArrayList(RoomDetailsBean.CREATOR);
        this.id = source.readString();
        this.meetingDate = source.readString();
        this.appointmentEndTime = source.readString();
        this.appointmentStartTime = source.readString();
        this.location = source.readString();
        this.name = source.readString();
        this.seatsNumber = source.readString();
        this.splitTime = source.readString();
        this.mSubscribeTimeBean = source.readParcelable(SubscribeTimeBean.class.getClassLoader());
        this.meetingTimeBatRspBOList = source.createTypedArrayList(SubscribeTimeBean.CREATOR);
    }

    public RoomDetailsBean() {
    }

    protected RoomDetailsBean(Parcel in) {
        this.rows = in.createTypedArrayList(RoomDetailsBean.CREATOR);
        this.id = in.readString();
        this.meetingDate = in.readString();
        this.appointmentEndTime = in.readString();
        this.appointmentStartTime = in.readString();
        this.location = in.readString();
        this.name = in.readString();
        this.seatsNumber = in.readString();
        this.splitTime = in.readString();
        this.mSubscribeTimeBean = in.readParcelable(SubscribeTimeBean.class.getClassLoader());
        this.meetingTimeBatRspBOList = in.createTypedArrayList(SubscribeTimeBean.CREATOR);
    }

    public static final Creator<RoomDetailsBean> CREATOR = new Creator<RoomDetailsBean>() {
        @Override
        public RoomDetailsBean createFromParcel(Parcel source) {
            return new RoomDetailsBean(source);
        }

        @Override
        public RoomDetailsBean[] newArray(int size) {
            return new RoomDetailsBean[size];
        }
    };
}
