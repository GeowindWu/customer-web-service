package com.gxecard.customerservice.entity.req;

import com.gxecard.customerservice.annotation.SocketReqParam;
import com.gxecard.customerservice.entity.BaseReqMessage;

/**
 * 退卡确认请求报文
 */
public class ReturnCardConfirmReqMessage extends BaseReqMessage {
    private String terminalNo = "";
    private String referenceNo = "";
    private String lockStatus = "";
    private String terminalTransNo = "";

    @SocketReqParam(order = 2, length = 16)
    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    @SocketReqParam(order = 3, length = 16)
    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    @SocketReqParam(order = 4, length = 1)
    public String getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(String lockStatus) {
        this.lockStatus = lockStatus;
    }

    @SocketReqParam(order = 5, length = 16)
    public String getTerminalTransNo() {
        return terminalTransNo;
    }

    public void setTerminalTransNo(String terminalTransNo) {
        this.terminalTransNo = terminalTransNo;
    }
}
