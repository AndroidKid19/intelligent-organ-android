package com.yway.scomponent.organ.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 访客记录
 */
public class VisitorRecordBean {
    private List<VisitorRecordBean> rows;
    /**
     * id
     */
    private String id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 证件号码
     */
    private String certificateNumber;
    /**
     * 手机号
     */
    private String cellPhone;
    /**
     * 目的单位
     */
    private String orgId;
    /**
     * 目的单位名称
     */
    private String orgTitle;

    /**
     * 到访状态；0未到访；1已到访
     */
    private String visitStatus;
    /**
     * 到访时间
     */
    private String visitTime;

    private Long createLoginId;
    /**
     * 创建时间
     */
    private String createTime;

    private Long updateLoginId;
    /**
     * 修改时间
     */
    private String updateTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 本年访客总数
     */
    private int yearVisitRecordCount;
    /**
     * 本月访客总数
     */
    private int monthVisitRecordCount;
    /**
     * 今日 访客总数
     */
    private int todayVisitRecordCount;

    public List<VisitorRecordBean> getRows() {
        return rows;
    }

    public void setRows(List<VisitorRecordBean> rows) {
        this.rows = rows;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgTitle() {
        return orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public String getVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(String visitStatus) {
        this.visitStatus = visitStatus;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public Long getCreateLoginId() {
        return createLoginId;
    }

    public void setCreateLoginId(Long createLoginId) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getYearVisitRecordCount() {
        return yearVisitRecordCount;
    }

    public void setYearVisitRecordCount(int yearVisitRecordCount) {
        this.yearVisitRecordCount = yearVisitRecordCount;
    }

    public int getMonthVisitRecordCount() {
        return monthVisitRecordCount;
    }

    public void setMonthVisitRecordCount(int monthVisitRecordCount) {
        this.monthVisitRecordCount = monthVisitRecordCount;
    }

    public int getTodayVisitRecordCount() {
        return todayVisitRecordCount;
    }

    public void setTodayVisitRecordCount(int todayVisitRecordCount) {
        this.todayVisitRecordCount = todayVisitRecordCount;
    }
}
