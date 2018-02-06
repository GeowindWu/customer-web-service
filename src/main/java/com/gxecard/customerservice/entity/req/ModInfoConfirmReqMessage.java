package com.gxecard.customerservice.entity.req;

import com.gxecard.customerservice.annotation.SocketReqParam;
import com.gxecard.customerservice.entity.BaseReqMessage;

/**
 * 个人信息修改确认请求报文
 */
public class ModInfoConfirmReqMessage extends BaseReqMessage {
    private String terminalNo = "";

    @SocketReqParam(order = 2, length = 16)
    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    private String referenceNo = "";

    @SocketReqParam(order = 3, length = 16)
    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    private String cardNo = "";

    @SocketReqParam(order = 4, length = 19)
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    private String cardMasterType = "";

    @SocketReqParam(order = 5, length = 2)
    public String getCardMasterType() {
        return cardMasterType;
    }

    public void setCardMasterType(String cardMasterType) {
        this.cardMasterType = cardMasterType;
    }

    private String cardSubType = "";

    @SocketReqParam(order = 6, length = 4)
    public String getCardSubType() {
        return cardSubType;
    }

    public void setCardSubType(String cardSubType) {
        this.cardSubType = cardSubType;
    }

    private String writeCardResult = "";

    @SocketReqParam(order = 7, length = 1)
    public String getWriteCardResult() {
        return writeCardResult;
    }

    public void setWriteCardResult(String writeCardResult) {
        this.writeCardResult = writeCardResult;
    }

    private String operatorId = "";

    @SocketReqParam(order = 8, length = 8)
    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

}