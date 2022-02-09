package com.yway.scomponent.organ.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 复选Bean
 */
public class CheckBoxBean implements Parcelable {
    /**
     * checkbox 名称
     */
    private String checkBoxName;
    /**
     * code
     */
    private String sysDictCode;
    /**
     * 是否选中
     */
    private boolean isChecked;

    public String getSysDictCode() {
        return sysDictCode;
    }

    public void setSysDictCode(String sysDictCode) {
        this.sysDictCode = sysDictCode;
    }

    public String getCheckBoxName() {
        return checkBoxName;
    }

    public void setCheckBoxName(String checkBoxName) {
        this.checkBoxName = checkBoxName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.checkBoxName);
        dest.writeString(this.sysDictCode);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        this.checkBoxName = source.readString();
        this.sysDictCode = source.readString();
        this.isChecked = source.readByte() != 0;
    }

    public CheckBoxBean() {
    }

    protected CheckBoxBean(Parcel in) {
        this.checkBoxName = in.readString();
        this.sysDictCode = in.readString();
        this.isChecked = in.readByte() != 0;
    }

    public static final Creator<CheckBoxBean> CREATOR = new Creator<CheckBoxBean>() {
        @Override
        public CheckBoxBean createFromParcel(Parcel source) {
            return new CheckBoxBean(source);
        }

        @Override
        public CheckBoxBean[] newArray(int size) {
            return new CheckBoxBean[size];
        }
    };
}
