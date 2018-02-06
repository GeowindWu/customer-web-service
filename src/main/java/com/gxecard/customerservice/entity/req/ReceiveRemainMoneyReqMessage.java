package com.gxecard.customerservice.entity.req;

import com.gxecard.customerservice.annotation.SocketReqParam;
import com.gxecard.customerservice.entity.BaseReqMessage;

/**
 * Created by 郑川 on 2017/5/27.
 */
public class ReceiveRemainMoneyReqMessage extends BaseReqMessage {

    //    终端编号	C2	16
    private String terminalNo = "";

    @SocketReqParam(order = 2, length = 16)
    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    //    网点编码	C3	16
    private String outletCode = "";

    @SocketReqParam(order = 3, length = 16)
    public String getOutletCode() {
        return outletCode;
    }

    public void setOutletCode(String outletCode) {
        this.outletCode = outletCode;
    }

    //    PSAM卡号	C4	16
    private String terminaTransNo = "";

    @SocketReqParam(order = 4, length = 16)
    public String getTerminaTransNo() {
        return terminaTransNo;
    }

    public void setTerminaTransNo(String terminaTransNo) {
        this.terminaTransNo = terminaTransNo;
    }

    //    业务类型	C5	4
    private String businessType = "";

    @SocketReqParam(order = 5, length = 4)
    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    //    退卡方式	C6	1
    private String returnCardType = "";

    @SocketReqParam(order = 6, length = 1)
    public String getReturnCardType() {
        return returnCardType;
    }

    public void setReturnCardType(String returnCardType) {
        this.returnCardType = returnCardType;
    }

    //    逻辑卡号	C7	19
    private String cardNo = "";

    @SocketReqParam(order = 7, length = 19)
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    //    退卡回执编号	C8	16
    private String returnCardReferenceNo = "";

    @SocketReqParam(order = 8, length = 16)
    public String getReturnCardReferenceNo() {
        return returnCardReferenceNo;
    }

    public void setReturnCardReferenceNo(String returnCardReferenceNo) {
        this.returnCardReferenceNo = returnCardReferenceNo;
    }

    //    证件类型	C9	2
    private String certType = "";

    @SocketReqParam(order = 9, length = 2)
    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    //    证件号码	C10	36
    private String cardIdNo = "";

    @SocketReqParam(order = 10, length = 36)
    public String getCardIdNo() {
        return cardIdNo;
    }

    public void setCardIdNo(String cardIdNo) {
        this.cardIdNo = cardIdNo;
    }


    //    操作员	C11	8
    private String operatorId = "";

    @SocketReqParam(order = 11, length = 8)
    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    //    授权操作员	C12	8
    private String granterId = "";

    @SocketReqParam(order = 12, length = 8)
    public String getGranterId() {
        return granterId;
    }

    public void setGranterId(String granterId) {
        this.granterId = granterId;
    }

    //    授权操作员密码	C13	32
    private String granterPassword = "";

    @SocketReqParam(order = 13, length = 32)
    public String getGranterPassword() {
        return granterPassword;
    }

    public void setGranterPassword(String granterPassword) {
        this.granterPassword = granterPassword;
    }


    //    代理点标志	C14	1
    private String proxyFlag = "";

    @SocketReqParam(order = 14, length = 1)
    public String getProxyFlag() {
        return proxyFlag;
    }

    public void setProxyFlag(String proxyFlag) {
        this.proxyFlag = proxyFlag;
    }

    //    授权标志	C15	1
    private String grantedFlag = "";

    @SocketReqParam(order = 15, length = 1)
    public String getGrantedFlag() {
        return grantedFlag;
    }

    public void setGrantedFlag(String grantedFlag) {
        this.grantedFlag = grantedFlag;
    }
}
