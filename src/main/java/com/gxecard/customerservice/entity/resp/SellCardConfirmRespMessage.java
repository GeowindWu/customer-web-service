package com.gxecard.customerservice.entity.resp;

import com.gxecard.customerservice.annotation.SocketRespParam;
import com.gxecard.customerservice.entity.BaseRespMessage;

/**
 * 售卡确认响应报文
 */
public class SellCardConfirmRespMessage extends BaseRespMessage {
    private String serialNo = "";

    public String getSerialNo() {
        return serialNo;
    }

    @SocketRespParam(order = 2, length = 16)
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    private String oldCardIdType = "";

    public String getOldCardIdType() {
        return oldCardIdType;
    }

    @SocketRespParam(order = 3, length = 2)
    public void setOldCardIdType(String oldCardIdType) {
        this.oldCardIdType = oldCardIdType;
    }

    private String oldCardIdNo = "";

    public String getOldCardIdNo() {
        return oldCardIdNo;
    }

    @SocketRespParam(order = 4, length = 36)
    public void setOldCardIdNo(String oldCardIdNo) {
        this.oldCardIdNo = oldCardIdNo;
    }

}