package com.yway.scomponent.login.mvp.model.entity;

/**
 * @ProjectName: miemiemie-android
 * @Package: com.yway.scomponent.login.mvp.model.entity
 * @ClassName: TagInfo
 * @Description:
 * @Author: Yuan
 * @CreateDate: 2020/10/29 10:51
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/29 10:51
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TagInfo {
    private String tagNumber;
    private String tagRiss;
    private int tagScanCount;

    public String getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(String tagNumber) {
        this.tagNumber = tagNumber;
    }

    public String getTagRiss() {
        return tagRiss;
    }

    public void setTagRiss(String tagRiss) {
        this.tagRiss = tagRiss;
    }

    public int getTagScanCount() {
        return tagScanCount;
    }

    public void setTagScanCount(int tagScanCount) {
        this.tagScanCount = tagScanCount;
    }

    @Override
    public String toString() {
        return "{" +
                "'" + tagNumber + '\'' +
                ", Rssi='" + tagRiss + '\'' +
                ", Count=" + tagScanCount +
                '}';
    }
}
