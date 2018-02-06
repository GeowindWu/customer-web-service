package com.gxecard.customerservice.entity.resp;

import com.gxecard.customerservice.annotation.SocketRespParam;
import com.gxecard.customerservice.entity.BaseRespMessage;

/**
 * 转值申请响应报文
 */
public class RemainTransferRespMessage extends BaseRespMessage {
    //	终端编号	C2	16	系统分配
    private String terminalNo = "";

    public String geTerminalNot() {
        return terminalNo;
    }

    @SocketRespParam(order = 2, length = 16)
    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    //	网点编码	C3	16	系统分配
    private String outletNo = "";

    public String getOutletNo() {
        return outletNo;
    }

    @SocketRespParam(order = 3, length = 16)
    public void setOutletNo(String outletNo) {
        this.outletNo = outletNo;
    }

    //	退卡方式	C4	16
    private String returnCardType = "";

    public String getReturnCardType() {
        return returnCardType;
    }

    @SocketRespParam(order = 4, length = 1)
    public void setReturnCardType(String returnCardType) {
        this.returnCardType = returnCardType;
    }

    //	逻辑卡号	C5	19
    private String cardNo = "";

    public String getCardNo() {
        return cardNo;
    }

    @SocketRespParam(order = 5, length = 19)
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    //	代理点标志	C6	1
    private String proxyFlag = "";

    public String getProxyFlag() {
        return proxyFlag;
    }

    @SocketRespParam(order = 6, length = 1)
    public void setProxyFlag(String proxyFlag) {
        this.proxyFlag = proxyFlag;
    }

    //	旧卡余额	C7	8	卡片余额
    private String oldCardBalance = "";

    public String getOldCardBalance() {
        return oldCardBalance;
    }

    @SocketRespParam(order = 7, length = 8)
    public void setOldCardBalance(String oldCardBalance) {
        this.oldCardBalance = oldCardBalance;
    }

    //	余额上限	C8	8	卡片余额上限
    private String balanceUpperLimit = "";

    public String getBalanceUpperLimit() {
        return balanceUpperLimit;
    }

    @SocketRespParam(order = 8, length = 8)
    public void setBalanceUpperLimit(String balanceUpperLimit) {
        this.balanceUpperLimit = balanceUpperLimit;
    }

    //	押金	C9	8
    private String deposit = "";

    public String getDeposit() {
        return deposit;
    }

    @SocketRespParam(order = 9, length = 8)
    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    //	退卡日期	C10	8
    private String returnCardDate = "";

    public String getReturnCardDate() {
        return returnCardDate;
    }

    @SocketRespParam(order = 10, length = 8)
    public void setReturnCardDate(String returnCardDate) {
        this.returnCardDate = returnCardDate;
    }

    //	转值类型	C11	1	钱包转值
    private String transferType = "";

    public String getTransferType() {
        return transferType;
    }

    @SocketRespParam(order = 11, length = 1)
    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    //	新卡印刻号	C12	19	数据库读出
    private String cardNewEngravedNo = "";

    public String getCardNewEngravedNo() {
        return cardNewEngravedNo;
    }

    @SocketRespParam(order = 12, length = 19)
    public void setCardNewEngravedNo(String cardNewEngravedNo) {
        this.cardNewEngravedNo = cardNewEngravedNo;
    }

    //	系统时间	C13	14
    private String sysDate = "";

    public String getSysDate() {
        return sysDate;
    }

    @SocketRespParam(order = 13, length = 14)
    public void setSysDate(String sysDate) {
        this.sysDate = sysDate;
    }

    //	回执说明	C14	200
    private String referenceDesc = "";

    public String getReferenceDesc() {
        return referenceDesc;
    }

    @SocketRespParam(order = 14, length = 200)
    public void setReferenceDesc(String referenceDesc) {
        this.referenceDesc = referenceDesc;
    }

    //	回执编码	C15	16
    private String referenceNo = "";

    public String getReferenceNo() {
        return referenceNo;
    }

    @SocketRespParam(order = 15, length = 16)
    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }


    //	旧卡印刻号	C16	19
    private String cardOldEngravedNo = "";

    public String getCardOldEngravedNo() {
        return cardOldEngravedNo;
    }

    @SocketRespParam(order = 16, length = 19)
    public void setCardOldEngravedNo(String cardOldEngravedNo) {
        this.cardOldEngravedNo = cardOldEngravedNo;
    }
    //	新卡充值前余额	C17	8

    private String newCardBalanceBefore = "";

    public String getNewCardBalanceBefore() {
        return newCardBalanceBefore;
    }

    @SocketRespParam(order = 17, length = 8)
    public void setNewCardBalanceBefore(String newCardBalanceBefore) {
        this.newCardBalanceBefore = newCardBalanceBefore;
    }

    //	转值申请时间	C18	14	系统返回
    private String transferApplyDate = "";

    public String getTransferApplyDate() {
        return transferApplyDate;
    }

    @SocketRespParam(order = 18, length = 14)
    public void setTransferApplyDate(String transferApplyDate) {
        this.transferApplyDate = transferApplyDate;
    }
}