package com.yway.scomponent.organ.mvp.model.entity;

import com.yway.scomponent.organ.R;

import tellh.com.recyclertreeview_lib.LayoutItemType;

/**
 *
 */

public class OrganTreeBean implements LayoutItemType {
    public String dirName;

    public OrganTreeBean(String dirName) {
        this.dirName = dirName;
    }

    @Override
    public int getLayoutId() {
        return R.layout.organ_address_item_org;
    }
}
