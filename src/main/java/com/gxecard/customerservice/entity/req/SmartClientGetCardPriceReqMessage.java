package com.gxecard.customerservice.entity.req;

import com.gxecard.customerservice.annotation.SocketReqParam;
import com.gxecard.customerservice.entity.BaseReqMessage;

/**
 * Created by 郑川 on 20170906
 */
public class SmartClientGetCardPriceReqMessage extends BaseReqMessage {


    /****************************************/
    /** 请求实体生成开始位置，自动代码生成。郑川 20170801  */

    /**
     * 属性描述：接口代码
     */
    private String messageType = "";

    @SocketReqParam(order = 2, length = 4)
    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
     * 属性描述：网点编码
     */
    private String outletNo = "";

    @SocketReqParam(order = 3, length = 16)
    public String getOutletNo() {
        return outletNo;
    }

    public void setOutletNo(String outletNo) {
        this.outletNo = outletNo;
    }

    /**
     * 属性描述：终端编号
     */
    private String terminalNo = "";

    @SocketReqParam(order = 4, length = 16)
    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    /**
     * 属性描述：操作员编码
     */
    private String operatorId = "";

    @SocketReqParam(order = 5, length = 8)
    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * 属性描述：逻辑卡号
     */
    private String cardNo = "";

    @SocketReqParam(order = 6, length = 19)
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /**
     * 属性描述：活动编码
     */
    private String discountNo = "";

    @SocketReqParam(order = 7, length = 4)
    public String getDiscountNo() {
        return discountNo;
    }

    public void setDiscountNo(String discountNo) {
        this.discountNo = discountNo;
    }

    /**
     * 属性描述：预留条件
     */
    private String obligateCondition = "";

    @SocketReqParam(order = 8, length = 2)
    public String getObligateCondition() {
        return obligateCondition;
    }

    public void setObligateCondition(String obligateCondition) {
        this.obligateCondition = obligateCondition;
    }
/****************************************/
}
