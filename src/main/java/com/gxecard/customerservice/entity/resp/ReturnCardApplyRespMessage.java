package com.gxecard.customerservice.entity.resp;

import com.gxecard.customerservice.annotation.SocketRespParam;
import com.gxecard.customerservice.entity.BaseRespMessage;

/**
 * 退卡申请响应报文
 */
public class ReturnCardApplyRespMessage extends BaseRespMessage {
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

    private String cardNo = "";

    public String getCardNo() {
        return cardNo;
    }

    @SocketRespParam(order = 4, length = 19)
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    private String returnTime = "";

    public String getReturnTime() {
        return returnTime;
    }

    @SocketRespParam(order = 5, length = 14)
    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    private String mac = "";

    public String getMac() {
        return mac;
    }

    @SocketRespParam(order = 6, length = 8)
    public void setMac(String mac) {
        this.mac = mac;
    }

    private String specialTime = "";

    public String getSpecialTime() {
        return specialTime;
    }

    @SocketRespParam(order = 7, length = 200)
    public void setSpecialTime(String specialTime) {
        this.specialTime = specialTime;
    }

    private String printingNo = "";

    public String getPrintingNo() {
        return printingNo;
    }

    @SocketRespParam(order = 8, length = 19)
    public void setPrintingNo(String printingNo) {
        this.printingNo = printingNo;
    }
}