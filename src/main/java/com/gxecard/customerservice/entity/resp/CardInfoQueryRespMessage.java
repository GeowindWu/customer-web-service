package com.gxecard.customerservice.entity.resp;

import com.gxecard.customerservice.annotation.SocketRespParam;
import com.gxecard.customerservice.entity.BaseRespMessage;

/**
 * 查询卡片芯片提供商、卡片制造商响应报文
 */
public class CardInfoQueryRespMessage extends BaseRespMessage {

    private String cardNo = "";
    private String chipProviderId = "";
    private String cardManufacturerId = "";

    public String getCardNo() {
        return cardNo;
    }

    @SocketRespParam(order = 2, length = 19)
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getChipProviderId() {
        return chipProviderId;
    }

    @SocketRespParam(order = 3, length = 4)
    public void setChipProviderId(String chipProviderId) {
        this.chipProviderId = chipProviderId;
    }

    public String getCardManufacturerId() {
        return cardManufacturerId;
    }

    @SocketRespParam(order = 4, length = 4)
    public void setCardManufacturerId(String cardManufacturerId) {
        this.cardManufacturerId = cardManufacturerId;
    }
}
