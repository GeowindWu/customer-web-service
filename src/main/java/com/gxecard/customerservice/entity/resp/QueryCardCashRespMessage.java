package com.gxecard.customerservice.entity.resp;

import com.gxecard.customerservice.annotation.SocketRespParam;
import com.gxecard.customerservice.entity.BaseRespMessage;

/**
 * 卡片余额查询,响应消息
 */
public class QueryCardCashRespMessage extends BaseRespMessage {

    /****************************************/
    /** 响应实体生成开始位置，自动代码生成。郑川 20170801 */

    /** 属性描述：终端编号 */
    private String terminalNo = "";
    public String getTerminalNo() {
        return terminalNo;
    }
    @SocketRespParam(order = 2, length = 16)
    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    /** 属性描述：网点编码 */
    private String outletNo = "";
    public String getOutletNo() {
        return outletNo;
    }
    @SocketRespParam(order = 3, length = 16)
    public void setOutletNo(String outletNo) {
        this.outletNo = outletNo;
    }

    /** 属性描述：退卡回执编码 */
    private String referenceNo = "";
    public String getReferenceNo() {
        return referenceNo;
    }
    @SocketRespParam(order = 4, length = 16)
    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    /** 属性描述：逻辑卡号 */
    private String cardNo = "";
    public String getCardNo() {
        return cardNo;
    }
    @SocketRespParam(order = 5, length = 19)
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /** 属性描述：卡片子类型 */
    private String cardSubType = "";
    public String getCardSubType() {
        return cardSubType;
    }
    @SocketRespParam(order = 6, length = 4)
    public void setCardSubType(String cardSubType) {
        this.cardSubType = cardSubType;
    }

    /** 属性描述：退卡日期 */
    private String returnCardDate = "";
    public String getReturnCardDate() {
        return returnCardDate;
    }
    @SocketRespParam(order = 7, length = 8)
    public void setReturnCardDate(String returnCardDate) {
        this.returnCardDate = returnCardDate;
    }

    /** 属性描述：卡片押金 */
    private String cardDeposit = "";
    public String getCardDeposit() {
        return cardDeposit;
    }
    @SocketRespParam(order = 8, length = 8)
    public void setCardDeposit(String cardDeposit) {
        this.cardDeposit = cardDeposit;
    }

    /** 属性描述：卡片余额 */
    private String cardBalance = "";
    public String getCardBalance() {
        return cardBalance;
    }
    @SocketRespParam(order = 9, length = 8)
    public void setCardBalance(String cardBalance) {
        this.cardBalance = cardBalance;
    }

    /** 属性描述：卡片应退余额 */
    private String realReturnCash = "";
    public String getRealReturnCash() {
        return realReturnCash;
    }
    @SocketRespParam(order = 10, length = 8)
    public void setRealReturnCash(String realReturnCash) {
        this.realReturnCash = realReturnCash;
    }

    /** 属性描述：查询状态 */
    private String queryStatus = "";
    public String getQueryStatus() {
        return queryStatus;
    }
    @SocketRespParam(order = 11, length = 1)
    public void setQueryStatus(String queryStatus) {
        this.queryStatus = queryStatus;
    }

    /****************************************/
}