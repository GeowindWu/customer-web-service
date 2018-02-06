package com.gxecard.customerservice.entity.req;

import com.gxecard.customerservice.annotation.SocketReqParam;
import com.gxecard.customerservice.entity.BaseReqMessage;

/**
 * 充值申请请求报文
 */
public class RechargeApplyReqMessage extends BaseReqMessage {
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

    private String terminalDeviceId = "";

    @SocketReqParam(order = 4, length = 16)
    public String getTerminalDeviceId() {
        return terminalDeviceId;
    }

    public void setTerminalDeviceId(String terminalDeviceId) {
        this.terminalDeviceId = terminalDeviceId;
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

    private String rechargeRandomNo = "";

    @SocketReqParam(order = 13, length = 8)
    public String getRechargeRandomNo() {
        return rechargeRandomNo;
    }

    public void setRechargeRandomNo(String rechargeRandomNo) {
        this.rechargeRandomNo = rechargeRandomNo;
    }

    private String cardOnlineTransCount = "";

    @SocketReqParam(order = 14, length = 4)
    public String getCardOnlineTransCount() {
        return cardOnlineTransCount;
    }

    public void setCardOnlineTransCount(String cardOnlineTransCount) {
        this.cardOnlineTransCount = cardOnlineTransCount;
    }

    private String cardOfflineTransCount = "";

    @SocketReqParam(order = 15, length = 4)
    public String getCardOfflineTransCount() {
        return cardOfflineTransCount;
    }

    public void setCardOfflineTransCount(String cardOfflineTransCount) {
        this.cardOfflineTransCount = cardOfflineTransCount;
    }

    private String businessType = "";

    @SocketReqParam(order = 16, length = 4)
    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    private String payManner = "";

    @SocketReqParam(order = 17, length = 2)
    public String getPayManner() {
        return payManner;
    }

    public void setPayManner(String payManner) {
        this.payManner = payManner;
    }

    private String rechargeAmount = "";

    @SocketReqParam(order = 18, length = 8)
    public String getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(String rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    private String cardBalance = "";

    @SocketReqParam(order = 19, length = 8)
    public String getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(String cardBalance) {
        this.cardBalance = cardBalance;
    }

    private String mac1 = "";

    @SocketReqParam(order = 20, length = 8)
    public String getMac1() {
        return mac1;
    }

    public void setMac1(String mac1) {
        this.mac1 = mac1;
    }

    private String operatorId = "";

    @SocketReqParam(order = 21, length = 8)
    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    private String cityCode = "";

    @SocketReqParam(order = 22, length = 4)
    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    private String rechargeManner = "";

    @SocketReqParam(order = 23, length = 1)
    public String getRechargeManner() {
        return rechargeManner;
    }

    public void setRechargeManner(String rechargeManner) {
        this.rechargeManner = rechargeManner;
    }

    private String overdraft = "";

    @SocketReqParam(order = 24, length = 8)
    public String getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(String overdraft) {
        this.overdraft = overdraft;
    }
    /**
     * 交易时间，请求统一账户用到
     * 不加socketReqParam注解，spring不会封装到socket请求中
     */
    private String orderTime = "";

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

    /**
     * 统一账户的用户编号
     */
    private String userNo = "";

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }
}