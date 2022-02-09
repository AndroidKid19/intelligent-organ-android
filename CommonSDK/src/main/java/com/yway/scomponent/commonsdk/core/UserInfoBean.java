package com.yway.scomponent.commonsdk.core;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class UserInfoBean implements Parcelable {
    private List<UserInfoBean> list;
    /**
     * ID
     */
    private String userId;
    /**
     * 电话
     */
    private String cellPhone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 登录名称默认手机号
     */
    private String loginName;
    /**
     * 真实姓名
     */
    private String name;
    /**
     * 性别
     */
    private int sex;
    /**
     * token
     */
    private String token;
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 认证状态：
     * 0待提交认证，1已提交认证（认证中），2认证已通过，3认证被驳回
     */
    private int certifiedStatus;

    /**
     * 身份证反面
     */
    private String afterIdCardUrl;

    /**
     * 身份证正面
     */
    private String frontIdCardUrl;

    /**
     * 证件号码
     */
    private String certificateNumber;
    /**
     * 头像
     */
    private String sysUserFilePath;
    /**
     * 组织机构ID
     */
    private String orgId;
    /**
     * 是否选中
     */
    private boolean isChecked;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getCertifiedStatus() {
        return certifiedStatus;
    }

    public void setCertifiedStatus(int certifiedStatus) {
        this.certifiedStatus = certifiedStatus;
    }

    public String getAfterIdCardUrl() {
        return afterIdCardUrl;
    }

    public void setAfterIdCardUrl(String afterIdCardUrl) {
        this.afterIdCardUrl = afterIdCardUrl;
    }

    public String getFrontIdCardUrl() {
        return frontIdCardUrl;
    }

    public void setFrontIdCardUrl(String frontIdCardUrl) {
        this.frontIdCardUrl = frontIdCardUrl;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getSysUserFilePath() {
        return sysUserFilePath;
    }

    public void setSysUserFilePath(String sysUserFilePath) {
        this.sysUserFilePath = sysUserFilePath;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public List<UserInfoBean> getList() {
        return list;
    }

    public void setList(List<UserInfoBean> list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.list);
        dest.writeString(this.userId);
        dest.writeString(this.cellPhone);
        dest.writeString(this.email);
        dest.writeString(this.loginName);
        dest.writeString(this.name);
        dest.writeInt(this.sex);
        dest.writeString(this.token);
        dest.writeString(this.nickName);
        dest.writeInt(this.certifiedStatus);
        dest.writeString(this.afterIdCardUrl);
        dest.writeString(this.frontIdCardUrl);
        dest.writeString(this.certificateNumber);
        dest.writeString(this.sysUserFilePath);
        dest.writeString(this.orgId);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        this.list = source.createTypedArrayList(UserInfoBean.CREATOR);
        this.userId = source.readString();
        this.cellPhone = source.readString();
        this.email = source.readString();
        this.loginName = source.readString();
        this.name = source.readString();
        this.sex = source.readInt();
        this.token = source.readString();
        this.nickName = source.readString();
        this.certifiedStatus = source.readInt();
        this.afterIdCardUrl = source.readString();
        this.frontIdCardUrl = source.readString();
        this.certificateNumber = source.readString();
        this.sysUserFilePath = source.readString();
        this.orgId = source.readString();
        this.isChecked = source.readByte() != 0;
    }

    public UserInfoBean() {
    }

    protected UserInfoBean(Parcel in) {
        this.list = in.createTypedArrayList(UserInfoBean.CREATOR);
        this.userId = in.readString();
        this.cellPhone = in.readString();
        this.email = in.readString();
        this.loginName = in.readString();
        this.name = in.readString();
        this.sex = in.readInt();
        this.token = in.readString();
        this.nickName = in.readString();
        this.certifiedStatus = in.readInt();
        this.afterIdCardUrl = in.readString();
        this.frontIdCardUrl = in.readString();
        this.certificateNumber = in.readString();
        this.sysUserFilePath = in.readString();
        this.orgId = in.readString();
        this.isChecked = in.readByte() != 0;
    }

    public static final Creator<UserInfoBean> CREATOR = new Creator<UserInfoBean>() {
        @Override
        public UserInfoBean createFromParcel(Parcel source) {
            return new UserInfoBean(source);
        }

        @Override
        public UserInfoBean[] newArray(int size) {
            return new UserInfoBean[size];
        }
    };
}
