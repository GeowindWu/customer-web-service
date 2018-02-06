package com.gxecard.customerservice.entity.req;

import com.gxecard.customerservice.annotation.SocketReqParam;
import com.gxecard.customerservice.entity.BaseReqMessage;

/**
 * 撤销申请请求报文
 */
public class RevokeApplyReqMessage extends BaseReqMessage {
    private String terminalNo = "";

    @SocketReqParam(order = 2, length = 16)
    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    private String terminalTransNo = "";

    @SocketReqParam(order = 3, length = 16)
    public String getTerminalTransNo() {
        return terminalTransNo;
    }

    public void setTerminalTransNo(String terminalTransNo) {
        this.terminalTransNo = terminalTransNo;
    }

    private String terminalDeviceNo = "";

    @SocketReqParam(order = 4, length = 16)
    public String getTerminalDeviceNo() {
        return terminalDeviceNo;
    }

    public void setTerminalDeviceNo(String terminalDeviceNo) {
        this.terminalDeviceNo = terminalDeviceNo;
    }

    private String outletCode = "";

    @SocketReqParam(order = 5, length = 16)
    public String getOutletCode() {
        return outletCode;
    }

    public void setOutletCode(String outletCode) {
        this.outletCode = outletCode;
    }

    private String proxyFlag = "";

    @SocketReqParam(order = 6, length = 1)
    public String getProxyFlag() {
        return proxyFlag;
    }

    public void setProxyFlag(String proxyFlag) {
        this.proxyFlag = proxyFlag;
    }

    private String cardNo = "";

    @SocketReqParam(order = 7, length = 19)
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    private String cardPhysicsNo = "";

    @SocketReqParam(order = 8, length = 16)
    public String getCardPhysicsNo() {
        return cardPhysicsNo;
    }

    public void setCardPhysicsNo(String cardPhysicsNo) {
        this.cardPhysicsNo = cardPhysicsNo;
    }

    private String cardMasterType = "";

    @SocketReqParam(order = 9, length = 2)
    public String getCardMasterType() {
        return cardMasterType;
    }

    public void setCardMasterType(String cardMasterType) {
        this.cardMasterType = cardMasterType;
    }

    private String cardSubType = "";

    @SocketReqParam(order = 10, length = 4)
    public String getCardSubType() {
        return cardSubType;
    }

    public void setCardSubType(String cardSubType) {
        this.cardSubType = cardSubType;
    }

    private String lastTransTerminalNo = "";

    @SocketReqParam(order = 11, length = 16)
    public String getLastTransTerminalNo() {
        return lastTransTerminalNo;
    }

    public void setLastTransTerminalNo(String lastTransTerminalNo) {
        this.lastTransTerminalNo = lastTransTerminalNo;
    }

    private String lastTransTime = "";

    @SocketReqParam(order = 12, length = 14)
    public String getLastTransTime() {
        return lastTransTime;
    }

    public void setLastTransTime(String lastTransTime) {
        this.lastTransTime = lastTransTime;
    }

    private String cardOnlineTransCount = "";

    @SocketReqParam(order = 13, length = 4)
    public String getCardOnlineTransCount() {
        return cardOnlineTransCount;
    }

    public void setCardOnlineTransCount(String cardOnlineTransCount) {
        this.cardOnlineTransCount = cardOnlineTransCount;
    }

    private String cardOfflineTransCount = "";

    @SocketReqParam(order = 14, length = 4)
    public String getCardOfflineTransCount() {
        return cardOfflineTransCount;
    }

    public void setCardOfflineTransCount(String cardOfflineTransCount) {
        this.cardOfflineTransCount = cardOfflineTransCount;
    }

    private String transType = "";

    @SocketReqParam(order = 15, length = 4)
    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    private String rechargeAmount = "";

    @SocketReqParam(order = 16, length = 8)
    public String getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(String rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    private String cardBalance = "";

    @SocketReqParam(order = 17, length = 8)
    public String getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(String cardBalance) {
        this.cardBalance = cardBalance;
    }

    private String operatorId = "";

    @SocketReqParam(order = 18, length = 8)
    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    private String granterId = "";

    @SocketReqParam(order = 19, length = 8)
    public String getGranterId() {
        return granterId;
    }

    public void setGranterId(String granterId) {
        this.granterId = granterId;
    }

    private String granterPassword = "";

    @SocketReqParam(order = 20, length = 32)
    public String getGranterPassword() {
        return granterPassword;
    }

    public void setGranterPassword(String granterPassword) {
        this.granterPassword = granterPassword;
    }

    private String cityCode = "";

    @SocketReqParam(order = 21, length = 4)
    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    private String randomNo = "";

    @SocketReqParam(order = 22, length = 8)
    public String getRandomNo() {
        return randomNo;
    }

    public void setRandomNo(String randomNo) {
        this.randomNo = randomNo;
    }
}