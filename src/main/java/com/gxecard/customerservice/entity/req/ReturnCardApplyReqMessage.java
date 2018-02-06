package com.gxecard.customerservice.entity.req;

import com.gxecard.customerservice.annotation.SocketReqParam;
import com.gxecard.customerservice.entity.BaseReqMessage;

/**
 * 退卡申请请求报文
 */
public class ReturnCardApplyReqMessage extends BaseReqMessage {
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

    private String cardNo = "";

    @SocketReqParam(order = 6, length = 19)
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    private String cardMasterType = "";

    @SocketReqParam(order = 7, length = 2)
    public String getCardMasterType() {
        return cardMasterType;
    }

    public void setCardMasterType(String cardMasterType) {
        this.cardMasterType = cardMasterType;
    }

    private String cardSubType = "";

    @SocketReqParam(order = 8, length = 4)
    public String getCardSubType() {
        return cardSubType;
    }

    public void setCardSubType(String cardSubType) {
        this.cardSubType = cardSubType;
    }

    private String activationDate = "";

    @SocketReqParam(order = 9, length = 8)
    public String getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(String activationDate) {
        this.activationDate = activationDate;
    }

    private String cardStatus = "";

    @SocketReqParam(order = 10, length = 1)
    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    private String deposit = "";

    @SocketReqParam(order = 11, length = 8)
    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    private String purseBalance = "";

    @SocketReqParam(order = 12, length = 8)
    public String getPurseBalance() {
        return purseBalance;
    }

    public void setPurseBalance(String purseBalance) {
        this.purseBalance = purseBalance;
    }

    private String operatorId = "";

    @SocketReqParam(order = 13, length = 8)
    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    private String randomNo = "";

    @SocketReqParam(order = 14, length = 8)
    public String getRandomNo() {
        return randomNo;
    }

    public void setRandomNo(String randomNo) {
        this.randomNo = randomNo;
    }

    private String proxyFlag = "";

    @SocketReqParam(order = 15, length = 1)
    public String getProxyFlag() {
        return proxyFlag;
    }

    public void setProxyFlag(String proxyFlag) {
        this.proxyFlag = proxyFlag;
    }

    private String cardPhysicsNo = "";

    @SocketReqParam(order = 16, length = 16)
    public String getCardPhysicsNo() {
        return cardPhysicsNo;
    }

    public void setCardPhysicsNo(String cardPhysicsNo) {
        this.cardPhysicsNo = cardPhysicsNo;
    }

    private String cityCode = "";

    @SocketReqParam(order = 17, length = 4)
    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    /*******************************************************************************/
    /**
     退卡原因	C18	2	01卡片读取失败，02卡片使用无反应，03卡片损坏无法使用，04用户个人意愿退卡
     退卡方式	C19	1	1为退卡领余额，2退卡后换卡
     开户银行	C20	10	开户银行编码（待定），退卡方式为1时填写
     开户人姓名	C21	40	退卡方式为1时填写
     银行卡号	C22	20	退卡方式为1时填写
     */
    /*******************************************************************************/
    // 2017-06-09根据铭鸿文档新增
    private String returnCardReason = "";
    @SocketReqParam(order = 18, length = 2)
    public String getReturnCardReason() {
        return returnCardReason;
    }
    public void setReturnCardReason(String returnCardReason) {
        this.returnCardReason = returnCardReason;
    }
    private String returnCardType = "";
    @SocketReqParam(order = 19, length = 1)
    public String getReturnCardType() {
        return returnCardType;
    }
    public void setReturnCardType(String returnCardType) {
        this.returnCardType = returnCardType;
    }

    private String bankCode = "";
    @SocketReqParam(order = 20, length = 10)
    public String getBankCode() {
        return bankCode;
    }
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    private String bankUserName = "";
    @SocketReqParam(order = 21, length = 40)
    public String getBankUserName() {
        return bankUserName;
    }
    public void setBankUserName(String bankUserName) {
        this.bankUserName = bankUserName;
    }

    private String bankUserCardNo= "";
    @SocketReqParam(order = 22, length = 20)
    public String getBankUserCardNo() {
        return bankUserCardNo;
    }
    public void setBankUserCardNo(String bankUserCardNo) {
        this.bankUserCardNo = bankUserCardNo;
    }


}