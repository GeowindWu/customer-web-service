package com.gxecard.customerservice.entity.resp;

import com.gxecard.customerservice.annotation.SocketRespParam;
import com.gxecard.customerservice.entity.BaseRespMessage;

/**
 * 售卡请求响应报文
 */
public class SellCardApply4SmartClientRespMessage extends BaseRespMessage {
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

    private String enableTime = "";

    public String getEnableTime() {
        return enableTime;
    }

    @SocketRespParam(order = 5, length = 14)
    public void setEnableTime(String enableTime) {
        this.enableTime = enableTime;
    }

    private String validEndDate = "";

    public String getValidEndDate() {
        return validEndDate;
    }

    @SocketRespParam(order = 6, length = 8)
    public void setValidEndDate(String validEndDate) {
        this.validEndDate = validEndDate;
    }

    private String annualInspectionDate = "";

    public String getAnnualInspectionDate() {
        return annualInspectionDate;
    }

    @SocketRespParam(order = 7, length = 8)
    public void setAnnualInspectionDate(String annualInspectionDate) {
        this.annualInspectionDate = annualInspectionDate;
    }

    private String cardDeposit = "";

    public String getCardDeposit() {
        return cardDeposit;
    }

    @SocketRespParam(order = 8, length = 8)
    public void setCardDeposit(String cardDeposit) {
        this.cardDeposit = cardDeposit;
    }

    private String mac = "";

    public String getMac() {
        return mac;
    }

    @SocketRespParam(order = 9, length = 8)
    public void setMac(String mac) {
        this.mac = mac;
    }

    private String specialInfo = "";

    public String getSpecialInfo() {
        return specialInfo;
    }

    @SocketRespParam(order = 10, length = 200)
    public void setSpecialInfo(String specialInfo) {
        this.specialInfo = specialInfo;
    }

    private String inspectionFlag = "";

    public String getInspectionFlag() {
        return inspectionFlag;
    }

    @SocketRespParam(order = 11, length = 2)
    public void setInspectionFlag(String inspectionFlag) {
        this.inspectionFlag = inspectionFlag;
    }

    private String printingNo = "";

    public String getPrintingNo() {
        return printingNo;
    }

    @SocketRespParam(order = 12, length = 19)
    public void setPrintingNo(String printingNo) {
        this.printingNo = printingNo;
    }

    private String cost = "";

    public String getCost() {
        return cost;
    }

    @SocketRespParam(order = 13, length = 8)
    public void setCost(String cost) {
        this.cost = cost;
    }

    private String cardFee = "";

    public String getCardFee() {
        return cardFee;
    }

    @SocketRespParam(order = 14, length = 8)
    public void setCardFee(String cardFee) {
        this.cardFee = cardFee;
    }
}