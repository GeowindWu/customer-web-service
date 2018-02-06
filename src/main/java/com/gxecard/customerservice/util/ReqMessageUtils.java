package com.gxecard.customerservice.util;


import com.google.common.base.CaseFormat;
import com.google.common.base.Preconditions;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Bytes;
import com.gxecard.customerservice.annotation.SocketReqParam;
import com.gxecard.customerservice.entity.BaseReqMessage;
import com.gxecard.customerservice.entity.req.*;
import com.gxecard.customerservice.entity.resp.InvoiceExtractionCodeRespMessage;
import com.gxecard.customerservice.exception.MessageException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.gxecard.customerservice.util.MessageUtil.CHARSET;

public class ReqMessageUtils {

    public static final Map<String, Class<? extends BaseReqMessage>> typeMessageMap;

    static {
        ImmutableMap.Builder<String, Class<? extends BaseReqMessage>> builder = ImmutableMap.builder();
        builder.put("5088", SellCardApplyReqMessage.class);
        builder.put("0089", SellCardConfirmReqMessage.class);
        builder.put("5021", ModInfoApplyReqMessage.class);
        builder.put("2002", ModInfoConfirmReqMessage.class);
        builder.put("5002", RechargeApplyReqMessage.class);

        builder.put("1003", RechargeConfirmReqMessage.class);
        builder.put("5004", RevokeApplyReqMessage.class);
        builder.put("1005", RevokeConfirmReqMessage.class);
        builder.put("5093", ReturnCardApplyReqMessage.class);
        builder.put("0094", ReturnCardConfirmReqMessage.class);

        builder.put("5095", NoCardReturnReqMessage.class);
        builder.put("2057", CardInfoQueryReqMessage.class);

        // zc:20170512  新增转值申请接口，领取余额接口
        builder.put("5029", RemainTransferReqMessage.class);
        builder.put("5096", ReceiveRemainMoneyReqMessage.class);

        // zc:20170801 新增卡片余额查询接口，用于退卡后查询卡片的情况
        builder.put("5023", QueryCardCashReqMessage.class);
        // zc:20170906 新增卡片售价查询接口，针对自助终端机
        builder.put("2061", SmartClientGetCardPriceReqMessage.class);
        builder.put("2058", ServerCheckReqMessage.class);
        builder.put("4088", SellCardApply4SmartClientReqMessage.class);
        // whq:20180119 2074 查询圈存记录状态 
        builder.put("2074",QueryRechargeRecordStatusReqMessage.class);
        //  whq: 发票提取码 2064
        builder.put("2064",InvoiceExtractionCodeReqMessage.class);
        typeMessageMap = builder.build();
    }

    private ReqMessageUtils() {

    }

    /**
     * 根据消息类型返回对应的请求消息class
     *
     * @param messageType 消息类型
     * @return 对应的请求消息class，如果没有匹配项，则返回null
     */
    public static Class<? extends BaseReqMessage> getReqMessageClass(String messageType) {
        return typeMessageMap.get(messageType);
    }

    public static byte[] convertMessageToBytes(BaseReqMessage message) {
        byte[] msgBody = convertMessageBodyToBytes(message);
        String messageType = message.getMessageType();
        byte[] msgHead = MessageUtil.buildMessageHead(messageType, msgBody.length);

        return Bytes.concat(msgHead, msgBody);
    }

    private static byte[] convertMessageBodyToBytes(BaseReqMessage message) {
        Method[] methods = message.getClass().getMethods();
        Optional<byte[]> body = Stream.of(methods)
                .filter(ReqMessageUtils::containsSocketReqParam)
                .sorted(getSocketReqParamComparator())
                .map(convertAttr2Bytes(message))
                .reduce((bytes1, bytes2) -> Bytes.concat(bytes1, bytes2));

        if (body.isPresent()) {
            return body.get();
        }
        throw new RuntimeException("报文体为null！");
    }

    private static Comparator<Method> getSocketReqParamComparator() {
        return (method1, method2) -> {
            int order1 = getSocketReqParam(method1).order();
            int order2 = getSocketReqParam(method2).order();
            return ComparisonChain.start().compare(order1, order2).result();
        };
    }

    private static Function<Method, byte[]> convertAttr2Bytes(BaseReqMessage message) {
        return method -> {
            try {
                int length = getSocketReqParam(method).length();
                String attrName = getAttrNameByMethod(method);
                Object invoke = method.invoke(message);
                String s;
                if (invoke != null) {
                    s = invoke.toString().trim();
                } else {
                    s = "";
                }
                byte[] bytes = s.getBytes(CHARSET);
                return leftPad(bytes, length, (byte) ' ', attrName);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        };
    }

    private static boolean containsSocketReqParam(Method method) {
        return getSocketReqParam(method) != null;
    }


    private static byte[] leftPad(byte[] bytes, int length, byte padding, String attrName) {
        Preconditions.checkNotNull(bytes);
        if (bytes.length > length) {
            throw new MessageException(String.format("attr[%s]:len[%d]>maxlen[%d]", attrName, bytes.length, length));
        }

        byte[] newBytes = new byte[length];
        int descPos = length - bytes.length;
        System.arraycopy(bytes, 0, newBytes, descPos, bytes.length);
        for (int i = 0; i < descPos; i++) {
            newBytes[i] = padding;
        }
        return newBytes;
    }

    private static SocketReqParam getSocketReqParam(Method method) {
        return method.getAnnotation(SocketReqParam.class);
    }

    private static String getAttrNameByMethod(Method method) {
        String methodName = method.getName();
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, methodName.replaceFirst("get", ""));
    }

}
