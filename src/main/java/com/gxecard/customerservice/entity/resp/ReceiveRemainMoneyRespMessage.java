package com.gxecard.customerservice.entity.resp;

import com.gxecard.customerservice.annotation.SocketRespParam;
import com.gxecard.customerservice.entity.BaseRespMessage;

/**
 * Created by 郑川 on 2017/5/27.
 */
public class ReceiveRemainMoneyRespMessage extends BaseRespMessage {

    //    终端编号	C2	16	系统分配
    private String terminalNo = "";
    public String getTerminalNo() {
        return terminalNo;
    }

    @SocketRespParam(order = 2, length = 16)
    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    //    交易序列号	C3	16	系统分配
    private String transNo = "";

    public String getTransNo() {
        return transNo;
    }

    @SocketRespParam(order = 3, length = 16)
    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    //    系统参照号	C4	16
    private String referenceNo = "";

    public String getReferenceNo() {
        return referenceNo;
    }

    @SocketRespParam(order = 4, length = 16)
    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    //    应退押金	C5	8
    private String returnDeposit = "";

    public String getReturnDeposit() {
        return returnDeposit;
    }

    @SocketRespParam(order = 5, length = 8)
    public void setReturnDeposit(String returnDeposit) {
        this.returnDeposit = returnDeposit;
    }

    //    储值余额	C6	8	系统返回
    private String prePaidAmount = "";

    public String getPrePaidAmount() {
        return prePaidAmount;
    }

    @SocketRespParam(order = 6, length = 8)
    public void setPrePaidAmount(String prePaidAmount) {
        this.prePaidAmount = prePaidAmount;
    }


    //    手续费	C7	8	系统返回
    private String poundage = "";

    public String getPoundage() {
        return poundage;
    }

    @SocketRespParam(order = 7, length = 8)
    public void setPoundage(String poundage) {
        this.poundage = poundage;
    }


    //    应退余额	C8	8	系统返回
    private String returnMoney = "";

    public String getReturnMoney() {
        return returnMoney;
    }

    @SocketRespParam(order = 8, length = 8)
    public void setReturnMoney(String returnMoney) {
        this.returnMoney = returnMoney;
    }


    //    逻辑卡号	C9	19
    private String cardNo = "";

    public String getCardNo() {
        return cardNo;
    }

    @SocketRespParam(order = 9, length = 19)
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    //    卡类型	C10	4
    private String cardType = "";

    public String getCardType() {
        return cardType;
    }

    @SocketRespParam(order = 10, length = 4)
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    //    系统时间	C11	14	系统时间
    private String sysDate = "";

    public String getSysDate() {
        return sysDate;
    }

    @SocketRespParam(order = 11, length = 14)
    public void setSysDate(String sysDate) {
        this.sysDate = sysDate;
    }

    //    操作员编码	C12	8	数据库读出
    // 1.0.7 版本 接口 改成8 20170731日
    private String operatorId = "";

    public String getOperatorId() {
        return operatorId;
    }

    @SocketRespParam(order = 12, length = 8)
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    //    回执说明	C13	200
    private String referenceDesc = "";

    public String getReferenceDesc() {
        return referenceDesc;
    }

    @SocketRespParam(order = 13, length = 200)
    public void setReferenceDesc(String referenceDesc) {
        this.referenceDesc = referenceDesc;
    }

    //    备注	C14	50
    private String remark = "";

    public String getRemark() {
        return remark;
    }

    @SocketRespParam(order = 14, length = 50)
    public void setRemark(String remark) {
        this.remark = remark;
    }

    //    卡片印刻号	C15	19	系统返回
    private String cardRecordNo = "";

    public String getCardRecordNo() {
        return cardRecordNo;
    }

    @SocketRespParam(order = 15, length = 19)
    public void setCardRecordNo(String cardRecordNo) {
        this.cardRecordNo = cardRecordNo;
    }

}
