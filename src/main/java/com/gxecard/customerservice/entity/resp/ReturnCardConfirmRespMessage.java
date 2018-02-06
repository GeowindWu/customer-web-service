package com.gxecard.customerservice.entity.resp;

import com.gxecard.customerservice.annotation.SocketRespParam;
import com.gxecard.customerservice.entity.BaseRespMessage;

/**
 * 退卡确认响应报文
 */
public class ReturnCardConfirmRespMessage extends BaseRespMessage {
    private String terminalNo= "";
    private String referenceNo= "";
    private String cardEngravedNo= "";
    private String receivedBalanceDate= "";

    public String getTerminalNo() {
        return terminalNo;
    }

    @SocketRespParam(order = 1, length = 16)
    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    @SocketRespParam(order = 2, length = 16)
    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getCardEngravedNo() {
        return cardEngravedNo;
    }

    @SocketRespParam(order = 3, length = 19)
    public void setCardEngravedNo(String cardEngravedNo) {
        this.cardEngravedNo = cardEngravedNo;
    }

    public String getReceivedBalanceDate() {
        return receivedBalanceDate;
    }

    @SocketRespParam(order = 4, length = 8)
    public void setReceivedBalanceDate(String receivedBalanceDate) {
        this.receivedBalanceDate = receivedBalanceDate;
    }
}
