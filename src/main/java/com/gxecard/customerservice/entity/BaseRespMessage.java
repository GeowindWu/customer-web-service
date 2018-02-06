package com.gxecard.customerservice.entity;


import com.gxecard.customerservice.annotation.SocketRespParam;

public abstract class BaseRespMessage {
    private String messageType = "";
    private String responseCode = "";
    private String errorDescription = "";

    public String getMessageType() {
        return messageType;
    }

    @SocketRespParam(order = Integer.MIN_VALUE, length = 4)
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getResponseCode() {
        return responseCode;
    }

    @SocketRespParam(order = Integer.MAX_VALUE - 1, length = 5)
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    @SocketRespParam(order = Integer.MAX_VALUE, length = 12)
    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }


    /**********************************************************************/
    // 新增的日志记录Id，所有接口都要设置
    // 不设置注解，不需要在返回中设置值
    private String logIndexId = "";
    public void setLogIndexId(String logIndexId) {
        this.logIndexId = logIndexId;
    }
    public String getLogIndexId() {
        return logIndexId;
    }

}
