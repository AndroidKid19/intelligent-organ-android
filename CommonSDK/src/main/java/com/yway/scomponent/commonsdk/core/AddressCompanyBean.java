package com.yway.scomponent.commonsdk.core;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class AddressCompanyBean implements Parcelable {
    /**
     * 通讯录列表
     */
    private List<AddressCompanyBean> sysOrgRspBOList;
    /**
     * 人员
     */
    private List<UserInfoBean> sysUserRspBOList;
    /**
     * 组织机构ID
     */
    private String orgId;
    /**
     * 组织机构父
     */
    private String parentId;
    /**
     * 组织机构名称
     */
    private String orgTitle;

    /**
     * 1是部门，0是人
     */
    private int flag;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public List<AddressCompanyBean> getSysOrgRspBOList() {
        return sysOrgRspBOList;
    }

    public void setSysOrgRspBOList(List<AddressCompanyBean> sysOrgRspBOList) {
        this.sysOrgRspBOList = sysOrgRspBOList;
    }

    public List<UserInfoBean> getSysUserRspBOList() {
        return sysUserRspBOList;
    }

    public void setSysUserRspBOList(List<UserInfoBean> sysUserRspBOList) {
        this.sysUserRspBOList = sysUserRspBOList;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getOrgTitle() {
        return orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.sysOrgRspBOList);
        dest.writeTypedList(this.sysUserRspBOList);
        dest.writeString(this.orgId);
        dest.writeString(this.parentId);
        dest.writeString(this.orgTitle);
        dest.writeInt(this.flag);
        dest.writeInt(this.count);
    }

    public void readFromParcel(Parcel source) {
        this.sysOrgRspBOList = new ArrayList<AddressCompanyBean>();
        source.readList(this.sysOrgRspBOList, AddressCompanyBean.class.getClassLoader());
        this.sysUserRspBOList = source.createTypedArrayList(UserInfoBean.CREATOR);
        this.orgId = source.readString();
        this.parentId = source.readString();
        this.orgTitle = source.readString();
        this.flag = source.readInt();
        this.count = source.readInt();
    }

    public AddressCompanyBean() {
    }

    protected AddressCompanyBean(Parcel in) {
        this.sysOrgRspBOList = new ArrayList<AddressCompanyBean>();
        in.readList(this.sysOrgRspBOList, AddressCompanyBean.class.getClassLoader());
        this.sysUserRspBOList = in.createTypedArrayList(UserInfoBean.CREATOR);
        this.orgId = in.readString();
        this.parentId = in.readString();
        this.orgTitle = in.readString();
        this.flag = in.readInt();
        this.count = in.readInt();
    }

    public static final Parcelable.Creator<AddressCompanyBean> CREATOR = new Parcelable.Creator<AddressCompanyBean>() {
        @Override
        public AddressCompanyBean createFromParcel(Parcel source) {
            return new AddressCompanyBean(source);
        }

        @Override
        public AddressCompanyBean[] newArray(int size) {
            return new AddressCompanyBean[size];
        }
    };
}
