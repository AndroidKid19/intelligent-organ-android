package com.yway.scomponent.commonsdk.core;

import com.yway.scomponent.commonsdk.http.Api;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {
    private int code;
    private T data;
    private T list;
    /**
     * 接口返回code
     * 至于为什么是String 类型的我也很无奈
     * */
    private String respCode;
    /**
     * 接口返回message
     * */
    private String respDesc;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return respDesc == null ? "未知错误`" : respDesc;
    }

    public void setMessage(String respDesc) {
        this.respDesc = respDesc;
    }

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }


    /**
     * 请求是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        if (respCode.equals(Api.REQUEST_SUCCESS)) {
            return true;
        } else {
            return false;
        }
    }
}
