package com.yway.scomponent.organ.mvp.model.entity;

/**
 * 支付详情
 */
public class PayDetailsBean {

    private PayDetailsBean rspMap;

    public PayDetailsBean getRspMap() {
        return rspMap;
    }

    public void setRspMap(PayDetailsBean rspMap) {
        this.rspMap = rspMap;
    }
    private String packageValue;

    private String appid;

    private String sign;
    /**
     * 商户号
     * */
    private String partnerid;

    /**
     * 预支付id
     * */
    private String prepayid;

    private String noncestr;

    private String timestamp;

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
