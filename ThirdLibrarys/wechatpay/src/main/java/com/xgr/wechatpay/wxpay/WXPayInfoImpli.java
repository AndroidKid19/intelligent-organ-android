/*
******************************* Copyright (c)*********************************\
**
**                 (c) Copyright 2017, King, china
**                          All Rights Reserved
**                                
**                              By(King)
**                         
**------------------------------------------------------------------------------
*/
package com.xgr.wechatpay.wxpay;


import com.xgr.easypay.base.IPayInfo;

/**
 * 文 件 名: WXPayInfoImpli
 * 创建日期: 2022/3/1 19:03
 * 邮   箱: yuanw9@sina.com
 * 描述 ：
 */
public class WXPayInfoImpli implements IPayInfo {
    /**
     * sign : ECE311C3DF76E009E6F37F05C350625F
     * timestamp : 1474886901
     * partnerid : 1391669502
     * package : Sign=WXPay
     * appid : wx46a24ab145becbde
     * nonceStr : 0531a4a42fa846fe8a7563847cd24c2a
     * prepayId : wx20160926184820acbd9357100240402425
     */

    /**
     * 签名
     * */
    private String sign;
    /**
     * 时间错
     * */
    private String timestamp;
    /**
     * 商户号
     * */
    private String partnerid;
    /**
     * 扩展字段，这里固定填写Sign = WXpay
     * */
    private String packageValue;
    /**
     * 微信APPID
     * */
    private String appid;
    /**
     * 随机字符串
     * */
    private String nonceStr;
    /**
     * 预支付交易会话ID
     * */
    private String prepayId;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

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

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

}
