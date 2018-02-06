package com.gxecard.customerservice.entity.resp;

import com.gxecard.customerservice.annotation.SocketRespParam;
import com.gxecard.customerservice.entity.BaseRespMessage;

/**
 * 个人信息修改申请响应报文
 */
public class ModInfoApplyRespMessage extends BaseRespMessage {
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

    private String mac = "";

    public String getMac() {
        return mac;
    }

    @SocketRespParam(order = 5, length = 8)
    public void setMac(String mac) {
        this.mac = mac;
    }

    private String specialInfo = "";

    public String getSpecialInfo() {
        return specialInfo;
    }

    @SocketRespParam(order = 6, length = 200)
    public void setSpecialInfo(String specialInfo) {
        this.specialInfo = specialInfo;
    }

    private String systemTime = "";

    public String getSystemTime() {
        return systemTime;
    }

    @SocketRespParam(order = 7, length = 14)
    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }

    private String printingNo = "";

    public String getPrintingNo() {
        return printingNo;
    }

    @SocketRespParam(order = 8, length = 16)
    public void setPrintingNo(String printingNo) {
        this.printingNo = printingNo;
    }
}