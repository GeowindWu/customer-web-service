package com.gxecard.customerservice.entity.resp;

import com.gxecard.customerservice.annotation.SocketRespParam;
import com.gxecard.customerservice.entity.BaseRespMessage;

/**
 * 无卡退卡响应报文
 */
public class NoCardReturnRespMessage extends BaseRespMessage {
    private String terminalNo = "";

    public String getTerminalNo() {
        return terminalNo;
    }

    @SocketRespParam(order = 2, length = 16)
    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    private String terminalTransNo = "";

    public String getTerminalTransNo() {
        return terminalTransNo;
    }

    @SocketRespParam(order = 3, length = 16)
    public void setTerminalTransNo(String terminalTransNo) {
        this.terminalTransNo = terminalTransNo;
    }

    private String referenceNo = "";

    public String getReferenceNo() {
        return referenceNo;
    }

    @SocketRespParam(order = 4, length = 16)
    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    private String cardNo = "";

    public String getCardNo() {
        return cardNo;
    }

    @SocketRespParam(order = 5, length = 19)
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    private String cardType = "";

    public String getCardType() {
        return cardType;
    }

    @SocketRespParam(order = 6, length = 4)
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    private String handleTime = "";

    public String getHandleTime() {
        return handleTime;
    }

    @SocketRespParam(order = 7, length = 14)
    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    private String receivedBalanceDate = "";

    public String getReceivedBalanceDate() {
        return receivedBalanceDate;
    }

    @SocketRespParam(order = 8, length = 8)
    public void setReceivedBalanceDate(String receivedBalanceDate) {
        this.receivedBalanceDate = receivedBalanceDate;
    }

    private String operatorId = "";

    public String getOperatorId() {
        return operatorId;
    }

    @SocketRespParam(order = 9, length = 8)
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    private String specialInfo = "";

    public String getSpecialInfo() {
        return specialInfo;
    }

    @SocketRespParam(order = 10, length = 200)
    public void setSpecialInfo(String specialInfo) {
        this.specialInfo = specialInfo;
    }

    private String username = "";

    public String getUsername() {
        return username;
    }

    @SocketRespParam(order = 11, length = 50)
    public void setUsername(String username) {
        this.username = username;
    }

    private String printingNo = "";

    public String getPrintingNo() {
        return printingNo;
    }

    @SocketRespParam(order = 12, length = 19)
    public void setPrintingNo(String printingNo) {
        this.printingNo = printingNo;
    }
}