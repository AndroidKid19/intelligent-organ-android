package com.yway.scomponent.organ.mvp.model.entity;

import java.util.List;

/**
 * 会议
 */
public class ConferenceBean {
    private List<ConferenceBean> rows;

    private boolean isConfIng;

    public boolean isConfIng() {
        return isConfIng;
    }

    public void setConfIng(boolean confIng) {
        isConfIng = confIng;
    }



    /**
     * 会议记录id
     */
    private String meetingRecordId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 创建人
     */
    private String createLoginId;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 修改人
     */
    private Long updateLoginId;
    /**
     * 修改时间
     */
    private String updateTime;
    /**
     * 0 正常 1 删除
     */
    private Integer isDelete;
    /**
     * 备注
     */
    private String remark;

    /**
     * 会议主题
     */
    private String meetingSubject;
    /**
     * 会议名称
     */
    private String meetingName;
    /**
     * 会议备注
     */
    private String meetingRemark;

    /**
     * 返回字段"今天",如果不是今天则返回 日期
     */
    private String isToDay;

    /**
     *  会议时间时间  格式为 HH:mm
     */
    private String startTime;

    /**
     * 会议开始时间
     */
    private String meetingStartTime;


    public String getMeetingRecordId() {
        return meetingRecordId;
    }

    public void setMeetingRecordId(String meetingRecordId) {
        this.meetingRecordId = meetingRecordId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateLoginId() {
        return createLoginId;
    }

    public void setCreateLoginId(String createLoginId) {
        this.createLoginId = createLoginId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateLoginId() {
        return updateLoginId;
    }

    public void setUpdateLoginId(Long updateLoginId) {
        this.updateLoginId = updateLoginId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMeetingSubject() {
        return meetingSubject;
    }

    public void setMeetingSubject(String meetingSubject) {
        this.meetingSubject = meetingSubject;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getMeetingRemark() {
        return meetingRemark;
    }

    public void setMeetingRemark(String meetingRemark) {
        this.meetingRemark = meetingRemark;
    }

    public String getIsToDay() {
        return isToDay;
    }

    public void setIsToDay(String isToDay) {
        this.isToDay = isToDay;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getMeetingStartTime() {
        return meetingStartTime;
    }

    public void setMeetingStartTime(String meetingStartTime) {
        this.meetingStartTime = meetingStartTime;
    }

    public List<ConferenceBean> getRows() {
        return rows;
    }

    public void setRows(List<ConferenceBean> rows) {
        this.rows = rows;
    }
}
