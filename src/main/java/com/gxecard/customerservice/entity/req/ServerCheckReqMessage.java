package com.gxecard.customerservice.entity.req;

import com.gxecard.customerservice.annotation.SocketReqParam;
import com.gxecard.customerservice.entity.BaseReqMessage;

/**
 * Created by 郑川 on 20170906
 */
public class ServerCheckReqMessage extends BaseReqMessage {


/****************************************/
/** 请求实体生成开始位置，自动代码生成。郑川 20170801  */

    /**
     * 属性描述：接口代码-2058
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
     * 属性描述：终端编号
     */
    private String terminalNo = "";

    @SocketReqParam(order = 3, length = 1)
    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }
/****************************************/
}
