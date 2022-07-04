package com.yway.scomponent.user.mvp.model.entity;
import com.blankj.utilcode.util.StringUtils;

import java.io.Serializable;

public class AppVersion implements Serializable {

    /**
     * app版本记录id
     */
    private Long id;
    /**
     * 类型：0为ios端 1为android端
     */
    private int type;
    /**
     * 升级方式：0为推荐升级 1为强制升级
     */
    private int upgradeMode;
    /**
     * 版本
     */
    private String version;
    /**
     * 用户版本
     */
    private String userVersion;
    /**
     * 安装包下载地址
     */
    private String packageDownloadLink;
    /**
     * 安装包大小
     */
    private String packageSize;

    private Long createLoginId;
    /**
     * 创建时间作为发布时间
     */
    private String createTime;

    private Long updateLoginId;

    private String updateTime;
    /**
     * 0 正常　1 删除
     */
    private int isDelete;
    /**
     * 升级说明
     */
    private String remark;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUpgradeMode() {
        return upgradeMode;
    }

    public void setUpgradeMode(int upgradeMode) {
        this.upgradeMode = upgradeMode;
    }

    public String getVersion() {
        return StringUtils.isEmpty(version) ? "1" : version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUserVersion() {
        return userVersion;
    }

    public void setUserVersion(String userVersion) {
        this.userVersion = userVersion;
    }

    public String getPackageDownloadLink() {
        return packageDownloadLink;
    }

    public void setPackageDownloadLink(String packageDownloadLink) {
        this.packageDownloadLink = packageDownloadLink;
    }

    public String getPackageSize() {
        return packageSize;
    }

    public void setPackageSize(String packageSize) {
        this.packageSize = packageSize;
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

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
