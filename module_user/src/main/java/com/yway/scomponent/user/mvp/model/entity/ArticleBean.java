package com.yway.scomponent.user.mvp.model.entity;

import java.util.List;

/**
 * 消息
 */
public class ArticleBean {
    private List<ArticleBean> rows;

    private String coverPictureUrl;

    private String title;
    private String createTime;
    private String id;
    private String articleId;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public List<ArticleBean> getRows() {
        return rows;
    }

    public void setRows(List<ArticleBean> rows) {
        this.rows = rows;
    }
}
