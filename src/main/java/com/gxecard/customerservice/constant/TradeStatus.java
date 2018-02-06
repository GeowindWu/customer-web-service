package com.gxecard.customerservice.constant;

/**
 * 交易记录状态
 */
public class TradeStatus {
    /**
     * 扣费未确认
     */
    public static final int RECHARGED_NOT_COMFIRM = 0;
    /**
     *  扣费并确认
     */
    public static final int RECHARGED_AND_COMFIRM = 1;
    /**
     * 扣费后已充正
     */
    public static final int RECHARGED_AND_REFOUND = 2;
    /**
     * 扣费后冲正失败
     */
    public static final int RECHARGED_REFOUND_FAIL = 3;
}
