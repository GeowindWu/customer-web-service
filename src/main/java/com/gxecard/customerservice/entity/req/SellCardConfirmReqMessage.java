package com.gxecard.customerservice.entity.req;

import com.gxecard.customerservice.annotation.SocketReqParam;
import com.gxecard.customerservice.entity.BaseReqMessage;

/**
 * 售卡确认请求报文
 */
public class SellCardConfirmReqMessage extends BaseReqMessage {
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

    private String writeCardResult = "";

    @SocketReqParam(order = 11, length = 1)
    public String getWriteCardResult() {
        return writeCardResult;
    }

    public void setWriteCardResult(String writeCardResult) {
        this.writeCardResult = writeCardResult;
    }

    private String operatorId = "";

    @SocketReqParam(order = 12, length = 8)
    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    private String operatorType = "";

    @SocketReqParam(order = 13, length = 1)
    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    private String oldCardNo = "";

    @SocketReqParam(order = 14, length = 19)
    public String getOldCardNo() {
        return oldCardNo;
    }

    public void setOldCardNo(String oldCardNo) {
        this.oldCardNo = oldCardNo;
    }
}