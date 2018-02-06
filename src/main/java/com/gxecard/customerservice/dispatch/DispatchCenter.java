package com.gxecard.customerservice.dispatch;

import com.google.gson.Gson;
import com.gxecard.customerservice.entity.AccessLog;
import com.gxecard.customerservice.entity.BaseRespMessage;
import com.gxecard.customerservice.service.AccessControll;
import com.gxecard.customerservice.service.MessageService;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;

/**
 * 分发，调度中心
 */
public class DispatchCenter {

    private MessageService messageService;
    private AccessControll.ApiInfo apiInfo;

    public DispatchCenter(MessageService messageService, AccessControll.ApiInfo apiInfo) {
        this.messageService = messageService;
        this.apiInfo = apiInfo;
    }

    /**
     * 分发，转发
     *
     * @param accessId
     * @param inputMap
     * @throws Exception
     */
    public DeferredResult dispatch(int accessId, Map<String, String> inputMap) throws Exception {
        TransmitStrategy transmitStrategy = getTransmitStrategy(apiInfo, inputMap);
        transmitStrategy.transmit(accessId, apiInfo, inputMap);
        // 返回结果
        Object dispatchResult = transmitStrategy.getResult();
        if (dispatchResult instanceof String) {
            // 如果返回结果是字符串，则是http请求
            DeferredResult<String> stringDeferredResult = new DeferredResult<String>();
            stringDeferredResult.setResult((String) dispatchResult);
            return stringDeferredResult;
        } else if (dispatchResult instanceof DeferredResult<?>) {
            // 如果是DeferredResult,则是铭鸿的
            return (DeferredResult) dispatchResult;
        } else {
            throw new RuntimeException("response type Not expected");
        }

    }

    /**
     * 获取分发策略
     *
     * @param apiInfo
     * @param inputMap
     * @return
     */
    private TransmitStrategy getTransmitStrategy(AccessControll.ApiInfo apiInfo, Map<String, String> inputMap) {
        int apiType = apiInfo.getApiType();
        String messageType = inputMap.get("messageType");
        return getStrategy(apiType, messageType);
    }

    @Bean
    public TransmitStrategy getStrategy(int apiType, String messageType) {

        TransmitStrategy strategy = null;
        if (apiType == 1) {
            // http的，非铭鸿
            if (messageType.contentEquals("10001")) {
                strategy = new TYChargeApplyTransmit(messageService);
            } else if (messageType.contentEquals("10002")) {
                strategy = new TYRefundTransmit(messageService);
            }
        } else if (apiType == 2) {
            // 铭鸿的socket
            if (messageType.contentEquals("5002")) {
                // 充值申请
                strategy = new MhRechargeApplyTransmit(messageService);
            } else if (messageType.contentEquals("1003")) {
                // 充值确认

                strategy = new MhRechareComfirmTransmit(messageService);
            } else {
                // 其他通用
                strategy = new MhCommonTransmit(messageService);
            }
        }
        return strategy;


    }
}
