package com.yway.scomponent.login.mvp.model.entity;


import java.io.Serializable;

public class LoginTokenBean implements Serializable {


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
