package com.gxecard.customerservice.entity.req;

import com.gxecard.customerservice.annotation.SocketReqParam;
import com.gxecard.customerservice.entity.BaseReqMessage;

/**
 * 无卡退卡请求报文
 */
public class NoCardReturnReqMessage extends BaseReqMessage {
    private String terminalNo = "";

    @SocketReqParam(order = 2, length = 16)
    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    private String terminaTransNo = "";

    @SocketReqParam(order = 3, length = 16)
    public String getTerminaTransNo() {
        return terminaTransNo;
    }

    public void setTerminaTransNo(String terminaTransNo) {
        this.terminaTransNo = terminaTransNo;
    }

    private String outletCode = "";

    @SocketReqParam(order = 4, length = 16)
    public String getOutletCode() {
        return outletCode;
    }

    public void setOutletCode(String outletCode) {
        this.outletCode = outletCode;
    }

    private String cardEngravedNo = "";

    private String psamId = "";

    @SocketReqParam(order = 5, length = 16)
    public String getPsamId() {
        return psamId;
    }

    public void setPsamId(String psamId) {
        this.psamId = psamId;
    }

    @SocketReqParam(order = 6, length = 19)
    public String getCardEngravedNo() {
        return cardEngravedNo;
    }

    public void setCardEngravedNo(String cardEngravedNo) {
        this.cardEngravedNo = cardEngravedNo;
    }

    private String cardNo = "";

    @SocketReqParam(order = 7, length = 19)
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    private String cardStatus = "";

    @SocketReqParam(order = 8, length = 1)
    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    private String cardReturn = "";

    @SocketReqParam(order = 9, length = 1)
    public String getCardReturn() {
        return cardReturn;
    }

    public void setCardReturn(String cardReturn) {
        this.cardReturn = cardReturn;
    }

    private String certType = "";

    @SocketReqParam(order = 10, length = 2)
    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    private String certName = "";

    @SocketReqParam(order = 11, length = 36)
    public String getCertName() {
        return certName;
    }

    public void setCertName(String certName) {
        this.certName = certName;
    }

    private String operatorId = "";

    @SocketReqParam(order = 12, length = 8)
    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    private String proxyFlag = "";

    @SocketReqParam(order = 13, length = 1)
    public String getProxyFlag() {
        return proxyFlag;
    }

    public void setProxyFlag(String proxyFlag) {
        this.proxyFlag = proxyFlag;
    }

    private String cityCode = "";

    @SocketReqParam(order = 14, length = 4)
    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }



    /*******************************************************************************/
    /**
     退卡原因	C15	2	01卡片读取失败，02卡片使用无反应，03卡片损坏无法使用，04用户个人意愿退卡
     退卡方式	C16	1	1为退卡后领余额，2退卡后换卡
     开户银行	C17	10	开户银行编码（待定），退款方式为1时填写，记录转账银行卡信息。
     开户人姓名	C18	40	退卡方式为1时填写
     银行卡号	C19	20	退卡方式为1时填写
     */
    /*******************************************************************************/
    // 2017-06-09根据铭鸿文档新增
    private String returnCardReason = "";
    @SocketReqParam(order = 15, length = 2)
    public String getReturnCardReason() {
        return returnCardReason;
    }
    public void setReturnCardReason(String returnCardReason) {
        this.returnCardReason = returnCardReason;
    }
    private String returnCardType = "";
    @SocketReqParam(order = 16, length = 1)
    public String getReturnCardType() {
        return returnCardType;
    }
    public void setReturnCardType(String returnCardType) {
        this.returnCardType = returnCardType;
    }

    private String bankCode = "";
    @SocketReqParam(order = 17, length = 10)
    public String getBankCode() {
        return bankCode;
    }
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    private String bankUserName = "";
    @SocketReqParam(order = 18, length = 40)
    public String getBankUserName() {
        return bankUserName;
    }
    public void setBankUserName(String bankUserName) {
        this.bankUserName = bankUserName;
    }

    private String bankUserCardNo= "";
    @SocketReqParam(order = 19, length = 20)
    public String getBankUserCardNo() {
        return bankUserCardNo;
    }
    public void setBankUserCardNo(String bankUserCardNo) {
        this.bankUserCardNo = bankUserCardNo;
    }
}