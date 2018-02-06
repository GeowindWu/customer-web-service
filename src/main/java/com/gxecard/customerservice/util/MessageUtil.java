package com.gxecard.customerservice.util;


import com.google.common.base.Strings;
import com.google.common.collect.ComparisonChain;
import com.google.common.primitives.Bytes;
import com.gxecard.customerservice.annotation.SocketRespParam;
import com.gxecard.customerservice.entity.BaseRespMessage;
import com.gxecard.customerservice.entity.MessageHead;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.gxecard.customerservice.entity.MessageHead.MESSAGE_HEAD_SIZE;
import static com.gxecard.customerservice.entity.MessageHead.PATTERN_MESSAGE_TYPE;

public class MessageUtil {

    public static final Charset CHARSET = Charset.forName("GBK");


    private MessageUtil() {

    }

    public static MessageHead createMessageHead(byte[] headBytes) {
        String messageType = String.format("%02X%02X", headBytes[0], headBytes[1]);
        byte[] version = Arrays.copyOfRange(headBytes, 2, 4);
        byte[] encryption = Arrays.copyOfRange(headBytes, 4, 5);

        int bodyLength = ((headBytes[5] & 0xff) << 8 | (headBytes[6] & 0X000000FF)) - MESSAGE_HEAD_SIZE;

        return new MessageHead(messageType, bodyLength, version, encryption);
    }

    public static byte[] convertMessageHeadToBytes(MessageHead messageHead) {
        String messageType = messageHead.getMessageType();
        int length = messageHead.getBodyLength();
        byte[] version = messageHead.getVersion();
        byte[] encryption = messageHead.getEncryption();

        return buildMessageHead(messageType, length, version, encryption);
    }

    public static byte[] buildMessageHead(String messageType, int bodySize) {
        return buildMessageHead(messageType, bodySize, new byte[]{0, 0}, new byte[]{0});
    }


    public static byte[] buildMessageHead(String messageType, int bodySize, byte[] version, byte[] encryption) {
        if (!PATTERN_MESSAGE_TYPE.matcher(messageType).matches()) {
            throw new RuntimeException("信息类型码格式错误");
        }

        byte[] messageTypeBytes = convertMessageTypeToBytes(messageType);
        int totalSize = MESSAGE_HEAD_SIZE + bodySize;
        byte[] size = new byte[2];
        size[0] = (byte) (((totalSize) >>> 8) & 0xFF);
        size[1] = (byte) (totalSize & 0xFF);

        return Bytes.concat(messageTypeBytes, version, encryption, size);
    }

    /**
     *
     * @param bodyBytes 报文体byte数组
     * @param respMessage 报文体对应类型的实体类
     * @return
     */
    public static BaseRespMessage createRespMessage(byte[] bodyBytes, BaseRespMessage respMessage) {
        Method[] methods = respMessage.getClass().getMethods();
        // 1.对所有的方法进行过滤，过滤掉没有注解的，和不是String 返回类型的
        // 2.排序，根据注解order 进行排序

        List<Method> paramMethods = Stream.of(methods).filter(method -> {
            Class<?>[] parameterTypes = method.getParameterTypes();
            SocketRespParam annotation = method.getAnnotation(SocketRespParam.class);
            return Arrays.equals(parameterTypes, new Class<?>[]{String.class}) && annotation != null;
        }).sorted((o1, o2) -> {
            int order1 = o1.getAnnotation(SocketRespParam.class).order();
            int order2 = o2.getAnnotation(SocketRespParam.class).order();
            return ComparisonChain.start().compare(order1, order2).result();
        }).collect(Collectors.toList());

        // 3.检查长度是否一致
        checkMessageBody(paramMethods, bodyBytes.length);

        // 4.根据注解长度，截取串，并且设置所有的set属性
        int startIndex = 0;
        int endIndex;
        for (Method paramMethod : paramMethods) {
            int paramLength = paramMethod.getAnnotation(SocketRespParam.class).length();
            endIndex = startIndex + paramLength;
            String param = new String(Arrays.copyOfRange(bodyBytes, startIndex, endIndex), CHARSET).trim();
            try {
                paramMethod.invoke(respMessage, param);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            startIndex = endIndex;
        }

        return respMessage;
    }

    private static void checkMessageBody(List<Method> paramMethods, int bodyLength) {
        Optional<Integer> reduce = paramMethods.stream()
                .map(method -> method.getAnnotation(SocketRespParam.class).length())
                .reduce((integer, integer2) -> integer + integer2);
        Integer integer = reduce.orElse(0);
        if (!integer.equals(bodyLength)) {
            throw new RuntimeException("报文体长度与消息配置不一致,定义长度:" + bodyLength +",传入报文长度："+integer);
        }
    }

    private static byte[] convertMessageTypeToBytes(String s) {
        if (Strings.isNullOrEmpty(s)) {
            return new byte[0];
        }
        int length = s.length();
        byte[] bytes;
        if (length % 2 == 0) {
            bytes = new byte[length / 2];
        } else {
            bytes = new byte[length / 2 + 1];
        }

        for (int i = length % 2, j = 0; i < length; i += 2, j++) {
            String numStr = s.substring(i, i + 2);
            bytes[j] = (byte) Integer.parseUnsignedInt(numStr, 16);
        }

        return bytes;
    }


}
