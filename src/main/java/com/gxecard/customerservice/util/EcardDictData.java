package com.gxecard.customerservice.util;

/**
 * Created by 郑川 on 2017/6/26.
 */
public class EcardDictData {
    //  输入输出参数的消息类型名称
    private final static String MessageTypeAttrName = "messageType";

    //  输入参数，访问用户的消息名称
    private final static String AccessUserAttrName = "accessUser";


    public static String getMessageTypeAttrName() {
        return MessageTypeAttrName;
    }

    public static String getAccessUserAttrName() {
        return AccessUserAttrName;
    }
}
