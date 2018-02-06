package com.gxecard.customerservice.entity.resp;

import com.gxecard.customerservice.annotation.SocketRespParam;
import com.gxecard.customerservice.entity.BaseRespMessage;

/**
 * 充值申请响应报文
 */
public class RechargeApplyRespMessage extends BaseRespMessage {
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

    private String mac2 = "";

    public String getMac2() {
        return mac2;
    }

    @SocketRespParam(order = 6, length = 8)
    public void setMac2(String mac2) {
        this.mac2 = mac2;
    }

    private String rechargeTime = "";

    public String getRechargeTime() {
        return rechargeTime;
    }

    @SocketRespParam(order = 7, length = 14)
    public void setRechargeTime(String rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    private String specialInfo = "";

    public String getSpecialInfo() {
        return specialInfo;
    }

    @SocketRespParam(order = 8, length = 200)
    public void setSpecialInfo(String specialInfo) {
        this.specialInfo = specialInfo;
    }

    private String minRecharge = "";

    public String getMinRecharge() {
        return minRecharge;
    }

    @SocketRespParam(order = 9, length = 8)
    public void setMinRecharge(String minRecharge) {
        this.minRecharge = minRecharge;
    }

    private String maxBalance = "";

    public String getMaxBalance() {
        return maxBalance;
    }

    @SocketRespParam(order = 10, length = 8)
    public void setMaxBalance(String maxBalance) {
        this.maxBalance = maxBalance;
    }

    private String printingNo = "";

    public String getPrintingNo() {
        return printingNo;
    }

    @SocketRespParam(order = 11, length = 19)
    public void setPrintingNo(String printingNo) {
        this.printingNo = printingNo;
    }
}