package com.yway.scomponent.organ.mvp.model.entity;

import com.yway.scomponent.organ.R;
import tellh.com.recyclertreeview_lib.LayoutItemType;

/**
 */

public class UserTreeBean implements LayoutItemType {
    public String fileName;
    private String userHead;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public UserTreeBean(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int getLayoutId() {
        return R.layout.organ_address_item_user;
    }
}
