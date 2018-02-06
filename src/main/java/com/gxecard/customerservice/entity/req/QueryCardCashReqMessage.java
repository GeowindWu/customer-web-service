package com.gxecard.customerservice.entity.req;

import com.gxecard.customerservice.annotation.SocketReqParam;
import com.gxecard.customerservice.entity.BaseReqMessage;

/**
 * 卡片余额查询,请求消息
 */
public class QueryCardCashReqMessage extends BaseReqMessage {
    /****************************************/
    /** 请求实体生成开始位置，自动代码生成。郑川 20170801  */

    /** 属性描述：终端编号 */
    private String terminalNo = "";
    @SocketReqParam(order = 2, length = 16)
    public String getTerminalNo() {
        return terminalNo;
    }
    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    /** 属性描述：网点编码 */
    private String outletNo = "";
    @SocketReqParam(order = 3, length = 16)
    public String getOutletNo() {
        return outletNo;
    }
    public void setOutletNo(String outletNo) {
        this.outletNo = outletNo;
    }

    /** 属性描述：城市编码 */
    private String cityCode = "";
    @SocketReqParam(order = 4, length = 4)
    public String getCityCode() {
        return cityCode;
    }
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    /** 属性描述：退卡回执编码 */
    private String referenceNo = "";
    @SocketReqParam(order = 5, length = 16)
    public String getReferenceNo() {
        return referenceNo;
    }
    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    /** 属性描述：逻辑卡号 */
    private String cardNo = "";
    @SocketReqParam(order = 6, length = 19)
    public String getCardNo() {
        return cardNo;
    }
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /** 属性描述：证件类型 */
    private String cardIdType = "";
    @SocketReqParam(order = 7, length = 2)
    public String getCardIdType() {
        return cardIdType;
    }
    public void setCardIdType(String cardIdType) {
        this.cardIdType = cardIdType;
    }

    /** 属性描述：证件号码 */
    private String cardIdNo = "";
    @SocketReqParam(order = 8, length = 36)
    public String getCardIdNo() {
        return cardIdNo;
    }
    public void setCardIdNo(String cardIdNo) {
        this.cardIdNo = cardIdNo;
    }

    /** 属性描述：查询类型 */
    private String queryType = "";
    @SocketReqParam(order = 9, length = 2)
    public String getQueryType() {
        return queryType;
    }
    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    /** 属性描述：操作员 */
    private String operatorId = "";
    @SocketReqParam(order = 10, length = 8)
    public String getOperatorId() {
        return operatorId;
    }
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    /****************************************/
}