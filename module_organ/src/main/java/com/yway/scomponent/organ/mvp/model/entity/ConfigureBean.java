package com.yway.scomponent.organ.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @ProjectName: intelligent-organ-android
 * @Package: com.yway.scomponent.organ.mvp.model.entity
 * @ClassName: ConfigureBean
 * @Description:
 * @Author: Yuan
 * @CreateDate: 2022/2/21 10:27
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/2/21 10:27
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ConfigureBean implements Parcelable {
    private List<ConfigureBean> list;

    public List<ConfigureBean> getList() {
        return list;
    }

    public void setList(List<ConfigureBean> list) {
        this.list = list;
    }

    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名称【非数据库字段】
     */
    private String userName;
    /**
     * 类型: 1会议预约审批人员配置，2、会务员配置，3、预约会议人员配置，3、访客记录''
     */
    private int type;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.list);
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeInt(this.type);
    }

    public void readFromParcel(Parcel source) {
        this.list = source.createTypedArrayList(ConfigureBean.CREATOR);
        this.userId = source.readString();
        this.userName = source.readString();
        this.type = source.readInt();
    }

    public ConfigureBean() {
    }

    protected ConfigureBean(Parcel in) {
        this.list = in.createTypedArrayList(ConfigureBean.CREATOR);
        this.userId = in.readString();
        this.userName = in.readString();
        this.type = in.readInt();
    }

    public static final Creator<ConfigureBean> CREATOR = new Creator<ConfigureBean>() {
        @Override
        public ConfigureBean createFromParcel(Parcel source) {
            return new ConfigureBean(source);
        }

        @Override
        public ConfigureBean[] newArray(int size) {
            return new ConfigureBean[size];
        }
    };
}
