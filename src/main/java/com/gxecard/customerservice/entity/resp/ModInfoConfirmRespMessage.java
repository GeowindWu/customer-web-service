package com.gxecard.customerservice.entity.resp;

import com.gxecard.customerservice.annotation.SocketRespParam;
import com.gxecard.customerservice.entity.BaseRespMessage;

/**
 * 个人信息修改确认响应报文
 */
public class ModInfoConfirmRespMessage extends BaseRespMessage {
    private String serialNo = "";

    public String getSerialNo() {
        return serialNo;
    }

    @SocketRespParam(order = 2, length = 16)
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
}