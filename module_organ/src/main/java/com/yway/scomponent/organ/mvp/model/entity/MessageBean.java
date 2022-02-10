package com.yway.scomponent.organ.mvp.model.entity;

import java.util.List;

/**
 * 消息
 */
public class MessageBean {
    private List<MessageBean> rows;

    private String coverPictureUrl;

    private String title;
    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCoverPictureUrl() {
        return coverPictureUrl;
    }

    public void setCoverPictureUrl(String coverPictureUrl) {
        this.coverPictureUrl = coverPictureUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MessageBean> getRows() {
        return rows;
    }

    public void setRows(List<MessageBean> rows) {
        this.rows = rows;
    }
}
