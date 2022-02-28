package com.yway.scomponent.organ.mvp.model.entity;

import java.util.List;

/**
 * 充值记录
 */
public class RechargeRecordBean {
    private List<RechargeRecordBean> rows;
    private List<RechargeRecordBean> list;
    /**
     * 主键id
     */
    private String id;
    /**
     * 用户账户表id
     */
    private String accountId;
    /**
     * 交易类型：0消费；1充值；2提现
     */
    private int transactionType;
    /**
     * 交易支付类型：0微信；1支付宝；2银行卡
     */
    private int transactionPayType;
    /**
     * 交易金额
     */
    private String transactionAmount;
    /**
     * 交易后账户余额
     */
    private String afterAccountBalance;
    /**
     * 交易id：对应微信/支付宝交易id
     */
    private String transactionId;
    /**
     * 商户订单号
     */
    private String outTradeNo;
    /**
     * 交易状态 0成功　1失败
     */
    private int transactionStatus;
    /**
     * 创建者
     */
    private String createLoginId;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新者
     */
    private String updateLoginId;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 备注
     */
    private String remark;

    /**
     * 账户
     */
    private String account;
    /**
     * 账户余额
     */
    private String accountBalance;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    public List<RechargeRecordBean> getRows() {
        return rows;
    }

    public void setRows(List<RechargeRecordBean> rows) {
        this.rows = rows;
    }

    public List<RechargeRecordBean> getList() {
        return list;
    }

    public void setList(List<RechargeRecordBean> list) {
        this.list = list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public int getTransactionPayType() {
        return transactionPayType;
    }

    public void setTransactionPayType(int transactionPayType) {
        this.transactionPayType = transactionPayType;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getAfterAccountBalance() {
        return afterAccountBalance;
    }

    public void setAfterAccountBalance(String afterAccountBalance) {
        this.afterAccountBalance = afterAccountBalance;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public int getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(int transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getCreateLoginId() {
        return createLoginId;
    }

    public void setCreateLoginId(String createLoginId) {
        this.createLoginId = createLoginId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateLoginId() {
        return updateLoginId;
    }

    public void setUpdateLoginId(String updateLoginId) {
        this.updateLoginId = updateLoginId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
