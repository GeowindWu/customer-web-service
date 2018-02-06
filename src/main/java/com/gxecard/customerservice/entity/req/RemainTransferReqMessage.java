package com.gxecard.customerservice.entity.req;

import com.gxecard.customerservice.annotation.SocketReqParam;
import com.gxecard.customerservice.entity.BaseReqMessage;

/**
 * 转值申请请求报文
 */
public class RemainTransferReqMessage extends BaseReqMessage {
	// 终端编号	C2	16	系统分配。
    private String terminalNo = "";

    @SocketReqParam(order = 2, length = 16)
    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

	// 网点编码	C3	16	系统分配
    private String outletCode = "";

    @SocketReqParam(order = 3, length = 16)
    public String getOutletCode() {
        return outletCode;
    }

    public void setOutletCode(String outletCode) {
        this.outletCode = outletCode;
    }

    // PSAM卡号	C4	16	与终端编号值一致
    private String psamId = "";

    @SocketReqParam(order = 4, length = 16)
    public String getPsamId() {
        return psamId;
    }

    public void setPsamId(String psamId) {
        this.psamId = psamId;
    }

    // 退卡方式	C5	1	填空
    private String returnType = "";

    @SocketReqParam(order = 5, length = 1)
    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    // 旧卡逻辑卡号	C6	19`
    private String  oldCardNo= "";

    @SocketReqParam(order = 6, length = 19)
    public String getOldCardNo() {
        return oldCardNo;
    }

    public void setOldCardNo(String oldCardNo) {
        this.oldCardNo = oldCardNo;
    }

    //回执编号	C7	16	填空
    private String  serialNo= "";

    @SocketReqParam(order = 7, length = 16)
    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    //证件类型	C8	2
    private String cardIdType = "";

    @SocketReqParam(order = 8, length = 2)
    public String getCardIdType() {
        return cardIdType;
    }

    public void setCardIdType(String cardIdType) {
        this.cardIdType = cardIdType;
    }
    //证件号码	C9	36
    private String  cardIdNo= "";

    @SocketReqParam(order = 9, length = 36)
    public String getCardIdNo() {
        return cardIdNo;
    }

    public void setCardIdNo(String cardIdNo ) {
        this.cardIdNo = cardIdNo;
    }

    //代理点标志	C10	1	0客服网点1代理点
    private String proxyFlag = "";

    @SocketReqParam(order = 10, length = 1)
    public String getProxyFlag() {
        return proxyFlag;
    }

    public void setProxyFlag(String proxyFlag) {
        this.proxyFlag =proxyFlag ;
    }

    //新卡逻辑卡号	C11	19
    private String newCardNo = "";

    @SocketReqParam(order = 11, length = 19)
    public String getNewCardNo() {
        return newCardNo;
    }

    public void setNewCardNo(String newCardNo) {
        this.newCardNo = newCardNo;
    }

    //新卡类型	C12	4	卡片类型
    private String newCardType = "";

    @SocketReqParam(order = 12, length = 4)
    public String getNewCardType() {
        return newCardType;
    }

    public void setNewCardType(String newCardType) {
        this.newCardType = newCardType;
    }

    //新卡余额	C13	8
    private String cardBalance = "";

    @SocketReqParam(order = 13, length = 8)
    public String getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(String cardBalance) {
        this.cardBalance = cardBalance;
    }

    //新卡年审日期	C14	8	填空
    private String  inspectionDate= "";

    @SocketReqParam(order = 14, length = 8)
    public String getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(String inspectionDate) {
        this.inspectionDate =inspectionDate ;
    }

    //操作员	C15	8
    private String  operatorId= "";

    @SocketReqParam(order = 15, length = 8)
    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this. operatorId=operatorId ;
    }

    //授权操作员	C16	8	填空
    private String granterId = "";

    @SocketReqParam(order = 16, length = 8)
    public String getGranterId() {
        return granterId;
    }

    public void setGranterId(String granterId) {
        this.granterId = granterId;
    }

    //授权操作员密码	C17	32	填空
    private String  granterPassword= "";

    @SocketReqParam(order = 17, length = 32)
    public String getGranterPassword() {
        return granterPassword;
    }

    public void setGranterPassword(String granterPassword ) {
        this.granterPassword =granterPassword ;
    }

    //城市编码	C18	4	根据具体城市填写
    private String cityCode  = "";

    @SocketReqParam(order = 18, length = 4)
    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

}