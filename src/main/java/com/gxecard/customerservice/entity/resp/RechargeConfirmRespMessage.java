package com.gxecard.customerservice.entity.resp;

import com.gxecard.customerservice.annotation.SocketRespParam;
import com.gxecard.customerservice.entity.BaseRespMessage;

/**
 * 充值确认响应报文
 */
public class RechargeConfirmRespMessage extends BaseRespMessage {
    private String serviceSerial = "";

    public String getServiceSerial() {
        return serviceSerial;
    }

    @SocketRespParam(order = 2, length = 16)
    public void setServiceSerial(String serviceSerial) {
        this.serviceSerial = serviceSerial;
    }

    private String terminalNo = "";

    public String getTerminalNo() {
        return terminalNo;
    }

    @SocketRespParam(order = 3, length = 16)
    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    private String terminalTransNo = "";

    public String getTerminalTransNo() {
        return terminalTransNo;
    }

    @SocketRespParam(order = 4, length = 16)
    public void setTerminalTransNo(String terminalTransNo) {
        this.terminalTransNo = terminalTransNo;
    }

    private String cardNo = "";

    public String getCardNo() {
        return cardNo;
    }

    @SocketRespParam(order = 5, length = 19)
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}