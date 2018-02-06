package com.gxecard.customerservice.constant;

import java.util.HashMap;
import java.util.Map;

public class ErrorMsg {

    public static final String NO_ORDER = "0007";
    public static final String ERROR_CODE = "99999";
    private static final String prefix = "0113004";
    private  static Map<String,String> msg = new HashMap<String,String>(16);
    static{
        //   放入数据 msg.put()
        msg.put("9998",prefix + "02001"); //扣费订单已存在
        msg.put("9995",prefix + "02002"); // 验签失败
        msg.put("0006",prefix + "02003"); //余额不足
        msg.put("0004",prefix + "02004"); //授权码验证异常
        msg.put("0007",prefix + "02005"); //冲正订单不存在
        msg.put("0008",prefix + "02006"); //订单已退款，无法冲正

    }
    /**
     * 根据统一账户的结果码，获取对应的云充值平台的码
     * @param result
     * @return 不存在此key，则返回空值
     */
    public static String getMsg(String result){
        if(msg.containsKey(result)){
            return msg.get(result);
        }
        return "";
    }
}
