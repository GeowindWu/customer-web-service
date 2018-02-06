package com.gxecard.customerservice.entity.resp;

import com.gxecard.customerservice.annotation.SocketRespParam;
import com.gxecard.customerservice.entity.BaseRespMessage;

/**
 * 撤销申请响应报文
 */
public class RevokeApplyRespMessage extends BaseRespMessage {
    private String referenceNo = "";

    public String getReferenceNo() {
        return referenceNo;
    }

    @SocketRespParam(order = 2, length = 16)
    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    private String terminalNo = "";

    public String getTerminalNo() {
        return terminalNo;
    }

    @SocketRespParam(order = 3, length = 16)
    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    private String terminalTransNo = "";

    public String getTerminalTransNo() {
        return terminalTransNo;
    }

    @SocketRespParam(order = 4, length = 16)
    public void setTerminalTransNo(String terminalTransNo) {
        this.terminalTransNo = terminalTransNo;
    }

    private String cardNo = "";

    public String getCardNo() {
        return cardNo;
    }

    @SocketRespParam(order = 5, length = 19)
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    private String mac1 = "";

    public String getMac1() {
        return mac1;
    }

    @SocketRespParam(order = 6, length = 8)
    public void setMac1(String mac1) {
        this.mac1 = mac1;
    }

    private String revokeTime = "";

    public String getRevokeTime() {
        return revokeTime;
    }

    @SocketRespParam(order = 7, length = 14)
    public void setRevokeTime(String revokeTime) {
        this.revokeTime = revokeTime;
    }

    private String specialInfo = "";

    public String getSpecialInfo() {
        return specialInfo;
    }

    @SocketRespParam(order = 8, length = 200)
    public void setSpecialInfo(String specialInfo) {
        this.specialInfo = specialInfo;
    }

    private String printingNo = "";

    public String getPrintingNo() {
        return printingNo;
    }

    @SocketRespParam(order = 9, length = 19)
    public void setPrintingNo(String printingNo) {
        this.printingNo = printingNo;
    }
}