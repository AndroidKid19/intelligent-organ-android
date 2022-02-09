package com.yway.scomponent.organ.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @ProjectName: intelligent-organ-android
 * @Package: com.yway.scomponent.organ.mvp.model.entity
 * @ClassName: SubscribeTimeBean
 * @Description:
 * @Author: Yuan
 * @CreateDate: 2021/12/28 18:21
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/12/28 18:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SubscribeTimeBean implements Parcelable {
    private String key;
    private String time;
    private boolean isDisable;
    /**
     * 是否可预约 1 已预约 0 可预约
     * */
    private String whetherAppointment;

    public String getWhetherAppointment() {
        return whetherAppointment;
    }

    public void setWhetherAppointment(String whetherAppointment) {
        this.whetherAppointment = whetherAppointment;
    }

    public boolean isDisable() {
        return isDisable;
    }

    public void setDisable(boolean disable) {
        isDisable = disable;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.time);
        dest.writeByte(this.isDisable ? (byte) 1 : (byte) 0);
        dest.writeString(this.whetherAppointment);
    }

    public void readFromParcel(Parcel source) {
        this.key = source.readString();
        this.time = source.readString();
        this.isDisable = source.readByte() != 0;
        this.whetherAppointment = source.readString();
    }

    public SubscribeTimeBean() {
    }

    protected SubscribeTimeBean(Parcel in) {
        this.key = in.readString();
        this.time = in.readString();
        this.isDisable = in.readByte() != 0;
        this.whetherAppointment = in.readString();
    }

    public static final Creator<SubscribeTimeBean> CREATOR = new Creator<SubscribeTimeBean>() {
        @Override
        public SubscribeTimeBean createFromParcel(Parcel source) {
            return new SubscribeTimeBean(source);
        }

        @Override
        public SubscribeTimeBean[] newArray(int size) {
            return new SubscribeTimeBean[size];
        }
    };
}
