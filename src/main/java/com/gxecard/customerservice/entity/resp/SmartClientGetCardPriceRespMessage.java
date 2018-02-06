package com.gxecard.customerservice.entity.resp;

import com.gxecard.customerservice.annotation.SocketRespParam;
import com.gxecard.customerservice.entity.BaseRespMessage;

/**
 * 自助终端机查询卡片售价响应
 */
public class SmartClientGetCardPriceRespMessage extends BaseRespMessage {

    /****************************************/
    /** 响应实体生成开始位置，自动代码生成。郑川 20170801 */

    /**
     * 属性描述：卡号
     */
    private String cardNo = "";

    public String getCardNo() {
        return cardNo;
    }

    @SocketRespParam(order = 2, length = 19)
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /**
     * 属性描述：卡子类型
     */
    private String cardType = "";

    public String getCardType() {
        return cardType;
    }

    @SocketRespParam(order = 3, length = 4)
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    /**
     * 属性描述：记名卡标志
     */
    private String realNameTrage = "";

    public String getRealNameTrage() {
        return realNameTrage;
    }

    @SocketRespParam(order = 4, length = 1)
    public void setRealNameTrage(String realNameTrage) {
        this.realNameTrage = realNameTrage;
    }

    /**
     * 属性描述：押金
     */
    private String deposit = "";

    public String getDeposit() {
        return deposit;
    }

    @SocketRespParam(order = 5, length = 8)
    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    /**
     * 属性描述：成本
     */
    private String cost = "";

    public String getCost() {
        return cost;
    }

    @SocketRespParam(order = 6, length = 8)
    public void setCost(String cost) {
        this.cost = cost;
    }

    /**
     * 属性描述：手续费
     */
    private String poundage = "";

    public String getPoundage() {
        return poundage;
    }

    @SocketRespParam(order = 7, length = 8)
    public void setPoundage(String poundage) {
        this.poundage = poundage;
    }
    /****************************************/
}
