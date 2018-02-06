package com.gxecard.customerservice.entity.req;

import com.gxecard.customerservice.annotation.SocketReqParam;
import com.gxecard.customerservice.entity.BaseReqMessage;

/**
 * 售卡申请请求报文
 */
public class SellCardApply4SmartClientReqMessage extends BaseReqMessage {
    private String terminalNo = "";
    private String terminalTransNo = "";
    private String terminalDeviceNo = "";
    private String outletNo = "";
    private String proxyFlag = "";
    private String cardNo = "";
    private String cardPhysicsNo = "";
    private String cardMasterType = "";
    private String cardSubType = "";
    private String deposit = "";
    private String denomination = "";
    private String firstRecharge = "";
    private String cityCode = "";
    private String randomNo = "";
    private String operationType = "";
    private String oldCardNo = "";
    private String payManner = "";
    private String idNo = "";
    private String operatorId = "";
    private String activationDate = "";
    private String studentGrade = "";
    private String beihaiCardChangeCompany = "";
    private String beihaiCardChangeType = "";
    private String beihaiCardChangeRemark = "";
    private String beihaiCardChangeBalanceBefore = "";

    @SocketReqParam(order = 2, length = 16)
    public String getTerminalNo() {
        return terminalNo;
    }

    @SocketReqParam(order = 3, length = 16)
    public String getTerminalTransNo() {
        return terminalTransNo;
    }

    @SocketReqParam(order = 4, length = 16)
    public String getTerminalDeviceNo() {
        return terminalDeviceNo;
    }

    @SocketReqParam(order = 5, length = 16)
    public String getOutletNo() {
        return outletNo;
    }

    @SocketReqParam(order = 6, length = 1)
    public String getProxyFlag() {
        return proxyFlag;
    }

    @SocketReqParam(order = 7, length = 19)
    public String getCardNo() {
        return cardNo;
    }

    @SocketReqParam(order = 8, length = 16)
    public String getCardPhysicsNo() {
        return cardPhysicsNo;
    }

    @SocketReqParam(order = 9, length = 2)
    public String getCardMasterType() {
        return cardMasterType;
    }

    @SocketReqParam(order = 10, length = 4)
    public String getCardSubType() {
        return cardSubType;
    }

    @SocketReqParam(order = 11, length = 8)
    public String getDeposit() {
        return deposit;
    }

    @SocketReqParam(order = 12, length = 8)
    public String getDenomination() {
        return denomination;
    }

    @SocketReqParam(order = 13, length = 8)
    public String getFirstRecharge() {
        return firstRecharge;
    }

    @SocketReqParam(order = 14, length = 4)
    public String getCityCode() {
        return cityCode;
    }

    @SocketReqParam(order = 15, length = 8)
    public String getRandomNo() {
        return randomNo;
    }

    @SocketReqParam(order = 16, length = 1)
    public String getOperationType() {
        return operationType;
    }

    @SocketReqParam(order = 17, length = 19)
    public String getOldCardNo() {
        return oldCardNo;
    }

    @SocketReqParam(order = 18, length = 2)
    public String getPayManner() {
        return payManner;
    }

    @SocketReqParam(order = 19, length = 20)
    public String getIdNo() {
        return idNo;
    }

    @SocketReqParam(order = 20, length = 8)
    public String getOperatorId() {
        return operatorId;
    }

    @SocketReqParam(order = 21, length = 8)
    public String getActivationDate() {
        return activationDate;
    }

    @SocketReqParam(order = 22, length = 2)
    public String getStudentGrade() {
        return studentGrade;
    }

    @SocketReqParam(order = 23, length = 1)
    public String getBeihaiCardChangeCompany() {
        return beihaiCardChangeCompany;
    }

    @SocketReqParam(order = 24, length = 1)
    public String getBeihaiCardChangeType() {
        return beihaiCardChangeType;
    }

    @SocketReqParam(order = 25, length = 100)
    public String getBeihaiCardChangeRemark() {
        return beihaiCardChangeRemark;
    }

    @SocketReqParam(order = 26, length = 8)
    public String getBeihaiCardChangeBalanceBefore() {
        return beihaiCardChangeBalanceBefore;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    public void setTerminalTransNo(String terminalTransNo) {
        this.terminalTransNo = terminalTransNo;
    }

    public void setTerminalDeviceNo(String terminalDeviceNo) {
        this.terminalDeviceNo = terminalDeviceNo;
    }

    public void setOutletNo(String outletNo) {
        this.outletNo = outletNo;
    }

    public void setProxyFlag(String proxyFlag) {
        this.proxyFlag = proxyFlag;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public void setCardPhysicsNo(String cardPhysicsNo) {
        this.cardPhysicsNo = cardPhysicsNo;
    }

    public void setCardMasterType(String cardMasterType) {
        this.cardMasterType = cardMasterType;
    }

    public void setCardSubType(String cardSubType) {
        this.cardSubType = cardSubType;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public void setFirstRecharge(String firstRecharge) {
        this.firstRecharge = firstRecharge;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public void setRandomNo(String randomNo) {
        this.randomNo = randomNo;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public void setOldCardNo(String oldCardNo) {
        this.oldCardNo = oldCardNo;
    }

    public void setPayManner(String payManner) {
        this.payManner = payManner;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public void setActivationDate(String activationDate) {
        this.activationDate = activationDate;
    }

    public void setStudentGrade(String studentGrade) {
        this.studentGrade = studentGrade;
    }

    public void setBeihaiCardChangeCompany(String beihaiCardChangeCompany) {
        this.beihaiCardChangeCompany = beihaiCardChangeCompany;
    }

    public void setBeihaiCardChangeType(String beihaiCardChangeType) {
        this.beihaiCardChangeType = beihaiCardChangeType;
    }

    public void setBeihaiCardChangeRemark(String beihaiCardChangeRemark) {
        this.beihaiCardChangeRemark = beihaiCardChangeRemark;
    }

    public void setBeihaiCardChangeBalanceBefore(String beihaiCardChangeBalanceBefore) {
        this.beihaiCardChangeBalanceBefore = beihaiCardChangeBalanceBefore;
    }
}
