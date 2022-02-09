package com.yway.scomponent.commonsdk.core;

import java.util.List;

/**
 * @ProjectName: miemiemie-android
 * @Package: com.fwzx.scomponent.commonsdk.core
 * @ClassName: UploadFileBean
 * @Description:
 * @Author: Yuan
 * @CreateDate: 2020/12/28 10:40
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/12/28 10:40
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class UploadFileBean {
    private List<UploadFileBean> fileList;

    private String name;
    private String url;

    public List<UploadFileBean> getFileList() {
        return fileList;
    }

    public void setFileList(List<UploadFileBean> fileList) {
        this.fileList = fileList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
