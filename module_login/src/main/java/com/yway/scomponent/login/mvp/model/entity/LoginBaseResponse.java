
package com.yway.scomponent.login.mvp.model.entity;

import com.yway.scomponent.commonsdk.core.BaseResponse;

/**
 * ================================================
 * 如果你服务器返回的数据格式固定为这种方式(这里只提供思想,服务器返回的数据格式可能不一致,可根据自家服务器返回的格式作更改)
 * 指定范型即可改变 {@code data} 字段的类型, 达到重用 {@link LoginBaseResponse}
 * <p>
 * Created by AndroidKid19 on 26/09/2016 15:19
 * <a href="mailto:yuanw9@sina.com">Contact me</a>
 * <a href="https://github.com/AndroidKid19Coding">Follow me</a>
 * ================================================
 */
public class LoginBaseResponse<T> extends BaseResponse {
    /**
     * code : 1000
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmb29rZXkxMDAxOCIsImlhdCI6MTU5NDYxNDI3NSwibmJmIjoxNTk0NjE0Mjc1LCJleHAiOjE1OTUyMTkwNzUsImlkIjoxMDAxOCwiaGVhZGVyIjpudWxsfQ._kHJIpgV_O4IrD_EFiKMgX_NpcS_t-kX79M1bLmKMr8
     * doctor_id : 10018
     * role : physician
     * message : 操作成功
     */

    private String token;
    private int doctor_id;
    private String role;
    private int authentication;
    private String authentication_cn;

    public int getAuthentication() {
        return authentication;
    }

    public void setAuthentication(int authentication) {
        this.authentication = authentication;
    }

    public String getAuthentication_cn() {
        return authentication_cn == null ? "" : authentication_cn;
    }

    public void setAuthentication_cn(String authentication_cn) {
        this.authentication_cn = authentication_cn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
