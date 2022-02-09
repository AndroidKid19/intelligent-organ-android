package com.yway.scomponent.commonsdk.core;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @Description: 字典实体羔羊
 */
public class DictClassifyBean implements Parcelable {

    /**
     * 字典解析 好多层，我TM心态蹦了
     */
    private List<DictClassifyBean> list;
    private List<DictClassifyBean> sysDictRspBOList;
    /**
     * 字典KEY
     */
    private String sysDictClassify;
    /**
     * 字典id
     */
    private String sysDictId;
    /**
     * 字典code
     */
    private String sysDictCode;
    /**
     * 字典name
     */
    private String sysDictName;


    /**
     * 设备 字典
     */
    private List<DictClassifyBean> dictDevice;


    /**
     * 会议报道时间 字典
     */
    private List<DictClassifyBean> dictMeetingCheckInTime;

    public List<DictClassifyBean> getDictMeetingCheckInTime() {
        return dictMeetingCheckInTime;
    }

    public void setDictMeetingCheckInTime(List<DictClassifyBean> dictMeetingCheckInTime) {
        this.dictMeetingCheckInTime = dictMeetingCheckInTime;
    }

    public List<DictClassifyBean> getDictDevice() {
        return dictDevice;
    }

    public void setDictDevice(List<DictClassifyBean> dictDevice) {
        this.dictDevice = dictDevice;
    }

    public List<DictClassifyBean> getList() {
        return list;
    }

    public void setList(List<DictClassifyBean> list) {
        this.list = list;
    }

    public List<DictClassifyBean> getSysDictRspBOList() {
        return sysDictRspBOList;
    }

    public void setSysDictRspBOList(List<DictClassifyBean> sysDictRspBOList) {
        this.sysDictRspBOList = sysDictRspBOList;
    }

    public String getSysDictClassify() {
        return sysDictClassify;
    }

    public void setSysDictClassify(String sysDictClassify) {
        this.sysDictClassify = sysDictClassify;
    }

    public String getSysDictId() {
        return sysDictId;
    }

    public void setSysDictId(String sysDictId) {
        this.sysDictId = sysDictId;
    }

    public String getSysDictCode() {
        return sysDictCode;
    }

    public void setSysDictCode(String sysDictCode) {
        this.sysDictCode = sysDictCode;
    }

    public String getSysDictName() {
        return sysDictName;
    }

    public void setSysDictName(String sysDictName) {
        this.sysDictName = sysDictName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.list);
        dest.writeTypedList(this.sysDictRspBOList);
        dest.writeString(this.sysDictClassify);
        dest.writeString(this.sysDictId);
        dest.writeString(this.sysDictCode);
        dest.writeString(this.sysDictName);
        dest.writeTypedList(this.dictDevice);
        dest.writeTypedList(this.dictMeetingCheckInTime);
    }

    public void readFromParcel(Parcel source) {
        this.list = source.createTypedArrayList(DictClassifyBean.CREATOR);
        this.sysDictRspBOList = source.createTypedArrayList(DictClassifyBean.CREATOR);
        this.sysDictClassify = source.readString();
        this.sysDictId = source.readString();
        this.sysDictCode = source.readString();
        this.sysDictName = source.readString();
        this.dictDevice = source.createTypedArrayList(DictClassifyBean.CREATOR);
        this.dictMeetingCheckInTime = source.createTypedArrayList(DictClassifyBean.CREATOR);
    }

    public DictClassifyBean() {
    }

    protected DictClassifyBean(Parcel in) {
        this.list = in.createTypedArrayList(DictClassifyBean.CREATOR);
        this.sysDictRspBOList = in.createTypedArrayList(DictClassifyBean.CREATOR);
        this.sysDictClassify = in.readString();
        this.sysDictId = in.readString();
        this.sysDictCode = in.readString();
        this.sysDictName = in.readString();
        this.dictDevice = in.createTypedArrayList(DictClassifyBean.CREATOR);
        this.dictMeetingCheckInTime = in.createTypedArrayList(DictClassifyBean.CREATOR);
    }

    public static final Creator<DictClassifyBean> CREATOR = new Creator<DictClassifyBean>() {
        @Override
        public DictClassifyBean createFromParcel(Parcel source) {
            return new DictClassifyBean(source);
        }

        @Override
        public DictClassifyBean[] newArray(int size) {
            return new DictClassifyBean[size];
        }
    };
}
