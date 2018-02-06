package com.gxecard.customerservice.util;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.gxecard.customerservice.entity.BaseRespMessage;
import com.gxecard.customerservice.entity.MessageHead;
import com.gxecard.customerservice.entity.req.QueryRechargeRecordStatusReqMessage;
import com.gxecard.customerservice.entity.resp.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class RespMessageUtils {

    private static final Map<String, Class<? extends BaseRespMessage>> typeMessageMap;

    static {
        ImmutableMap.Builder<String, Class<? extends BaseRespMessage>> builder = ImmutableMap.builder();
        builder.put("5088", SellCardApplyRespMessage.class);
        builder.put("0089", SellCardConfirmRespMessage.class);
        builder.put("5021", ModInfoApplyRespMessage.class);
        builder.put("2002", ModInfoConfirmRespMessage.class);
        builder.put("5002", RechargeApplyRespMessage.class);

        builder.put("1003", RechargeConfirmRespMessage.class);
        builder.put("5004", RevokeApplyRespMessage.class);
        builder.put("1005", RevokeConfirmRespMessage.class);
        builder.put("5093", ReturnCardApplyRespMessage.class);
        builder.put("0094", ReturnCardConfirmRespMessage.class);

        builder.put("5095", NoCardReturnRespMessage.class);
        builder.put("2057", CardInfoQueryRespMessage.class);

        // zc:20170512  新增转值申请接口，领取余额接口
        builder.put("5029", RemainTransferRespMessage.class);
        builder.put("5096", ReceiveRemainMoneyRespMessage.class);
        // zc:20170801 新增卡片余额查询接口，用于退卡后查询卡片的情况
        builder.put("5023", QueryCardCashRespMessage.class);
        // zc:20170906 新增卡片售价查询接口，针对自助终端机
        builder.put("2061", SmartClientGetCardPriceRespMessage.class);
        builder.put("2058", ServerCheckRespMessage.class);
        builder.put("4088", SellCardApply4SmartClientRespMessage.class);
        // whq:20180119 2074 查询圈存记录状态 
        builder.put("2074",QueryRechargeRecordStatusRespMessage.class);
        // whq:20180123,2064 发票接口提取码接口 
        builder.put("2064",InvoiceExtractionCodeRespMessage.class);
        typeMessageMap = builder.build();
    }

    private RespMessageUtils() {

    }

    /**
     * 根据消息类型返回对应的响应消息class
     *
     * @param messageType 消息类型
     * @return 对应的响应消息class，如果没有匹配项，则返回null
     */
    public static Class<? extends BaseRespMessage> getRespMessageClass(String messageType) {
        return typeMessageMap.get(messageType);
    }


    public static BaseRespMessage parseRespMessage(MessageHead head, byte[] bodyBytes) {
        String messageType = head.getMessageType();
        if (head.getBodyLength() != bodyBytes.length) {
            log.error("datagram  body length verification failed，expected：{}，actually：{}", head.getBodyLength(), bodyBytes.length);
            throw new RuntimeException("报文长度验证失败");
        }

        Class<? extends BaseRespMessage> messageClass = getRespMessageClass(messageType);
        if (messageClass == null) {
            log.error("unknow datagram type  -> {}", head.getMessageType());
            log.error("unkonw datagram body  -> {}", new String(bodyBytes, MessageUtil.CHARSET));

            BasicRespMessage unknownRespMessage = new BasicRespMessage();
            unknownRespMessage.setMessageType(head.getMessageType());
            unknownRespMessage.setResponseCode("99999");
            unknownRespMessage.setErrorDescription(new String(bodyBytes, MessageUtil.CHARSET));

            return unknownRespMessage;
        }

        //生成响应消息
        BaseRespMessage respMessage;
        try {
            respMessage = messageClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        respMessage = MessageUtil.createRespMessage(bodyBytes, respMessage);

        String respMessageType = respMessage.getMessageType();

        if (Strings.isNullOrEmpty(respMessageType)) {
            respMessageType = messageType;
            respMessage.setMessageType(respMessageType);
        }

        if (!messageType.equals(respMessageType)) {
            throw new RuntimeException("报文头与报文体类型不相符");
        }

        return respMessage;
    }

}
