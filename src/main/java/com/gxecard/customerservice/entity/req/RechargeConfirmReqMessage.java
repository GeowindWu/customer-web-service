package com.gxecard.customerservice.entity.req;

import com.gxecard.customerservice.annotation.SocketReqParam;
import com.gxecard.customerservice.entity.BaseReqMessage;

/**
 * 充值确认请求报文
 */
public class RechargeConfirmReqMessage extends BaseReqMessage {
    private String terminalNo = "";

    @SocketReqParam(order = 2, length = 16)
    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    private String referenceNo = "";

    @SocketReqParam(order = 3, length = 16)
    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    private String terminalTransNo = "";

    @SocketReqParam(order = 4, length = 16)
    public String getTerminalTransNo() {
        return terminalTransNo;
    }

    public void setTerminalTransNo(String terminalTransNo) {
        this.terminalTransNo = terminalTransNo;
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

    private String cardMasterType = "";

    @SocketReqParam(order = 8, length = 2)
    public String getCardMasterType() {
        return cardMasterType;
    }

    public void setCardMasterType(String cardMasterType) {
        this.cardMasterType = cardMasterType;
    }

    private String cardSubType = "";

    @SocketReqParam(order = 9, length = 4)
    public String getCardSubType() {
        return cardSubType;
    }

    public void setCardSubType(String cardSubType) {
        this.cardSubType = cardSubType;
    }

    private String cardOnlineTransCount = "";

    @SocketReqParam(order = 10, length = 4)
    public String getCardOnlineTransCount() {
        return cardOnlineTransCount;
    }

    public void setCardOnlineTransCount(String cardOnlineTransCount) {
        this.cardOnlineTransCount = cardOnlineTransCount;
    }

    private String cardOfflineTransCount = "";

    @SocketReqParam(order = 11, length = 4)
    public String getCardOfflineTransCount() {
        return cardOfflineTransCount;
    }

    public void setCardOfflineTransCount(String cardOfflineTransCount) {
        this.cardOfflineTransCount = cardOfflineTransCount;
    }

    private String transTime = "";

    @SocketReqParam(order = 12, length = 14)
    public String getTransTime() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    private String businessType = "";

    @SocketReqParam(order = 13, length = 4)
    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    private String payManner = "";

    @SocketReqParam(order = 14, length = 2)
    public String getPayManner() {
        return payManner;
    }

    public void setPayManner(String payManner) {
        this.payManner = payManner;
    }

    private String rechargeAmount = "";

    @SocketReqParam(order = 15, length = 8)
    public String getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(String rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    private String cardBalance = "";

    @SocketReqParam(order = 16, length = 8)
    public String getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(String cardBalance) {
        this.cardBalance = cardBalance;
    }

    private String tac = "";

    @SocketReqParam(order = 17, length = 8)
    public String getTac() {
        return tac;
    }

    public void setTac(String tac) {
        this.tac = tac;
    }

    private String writeCardResult = "";

    @SocketReqParam(order = 18, length = 1)
    public String getWriteCardResult() {
        return writeCardResult;
    }

    public void setWriteCardResult(String writeCardResult) {
        this.writeCardResult = writeCardResult;
    }

    private String operatorId = "";

    @SocketReqParam(order = 19, length = 8)
    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
}