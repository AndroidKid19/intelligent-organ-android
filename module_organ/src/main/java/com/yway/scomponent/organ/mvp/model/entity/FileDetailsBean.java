package com.yway.scomponent.organ.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 文件详情
 */
public class FileDetailsBean implements Parcelable {
    private String fileName;
    private String filePath;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fileName);
        dest.writeString(this.filePath);
    }

    public void readFromParcel(Parcel source) {
        this.fileName = source.readString();
        this.filePath = source.readString();
    }

    public FileDetailsBean() {
    }

    protected FileDetailsBean(Parcel in) {
        this.fileName = in.readString();
        this.filePath = in.readString();
    }

    public static final Creator<FileDetailsBean> CREATOR = new Creator<FileDetailsBean>() {
        @Override
        public FileDetailsBean createFromParcel(Parcel source) {
            return new FileDetailsBean(source);
        }

        @Override
        public FileDetailsBean[] newArray(int size) {
            return new FileDetailsBean[size];
        }
    };
}
