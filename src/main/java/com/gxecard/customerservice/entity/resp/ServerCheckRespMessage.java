package com.gxecard.customerservice.entity.resp;

import com.gxecard.customerservice.annotation.SocketRespParam;
import com.gxecard.customerservice.entity.BaseRespMessage;

/**
 * 自助终端机查询卡片售价响应
 */
public class ServerCheckRespMessage extends BaseRespMessage {


/****************************************/
/** 响应实体生成开始位置，自动代码生成。郑川 20170801 */

    /**
     * 属性描述：当前时间
     */
    private String currentTime = "";

    public String getCurrentTime() {
        return currentTime;
    }

    @SocketRespParam(order = 2, length = 14)
    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
/****************************************/
}
