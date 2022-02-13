package com.yway.scomponent.organ.mvp.model.entity;

/**
 * 是否收藏
 */
public class FavoritesBean {
    /**
     * 文章收藏状态；
     * 1代表文章已收藏
     * 0代表文章未收藏
     */
    private String favoritesStatus;

    public String getFavoritesStatus() {
        return favoritesStatus;
    }

    public void setFavoritesStatus(String favoritesStatus) {
        this.favoritesStatus = favoritesStatus;
    }
}
