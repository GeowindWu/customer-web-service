package com.gxecard.customerservice.dispatch;

import com.gxecard.customerservice.constant.AccountConfig;
import com.gxecard.customerservice.entity.BaseRespMessage;
import com.gxecard.customerservice.service.AccessControll;
import com.gxecard.customerservice.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.Set;

/**
 * 转发到铭鸿服务器的通用实现类
 */
public class MhCommonTransmit implements TransmitStrategy {

    public DeferredResult<BaseRespMessage> deferredResult = new DeferredResult<BaseRespMessage>();
    ;
    public MessageService messageService;


    public MhCommonTransmit(MessageService messageService) {
        this.messageService = messageService;
    }


    @Override
    public void transmit(int accessId, AccessControll.ApiInfo apiInfo, Map<String, String> param) throws Exception {
        int port = Integer.valueOf(apiInfo.getApiPort());
        String strParam = mapToString(param);
        messageService.transmit(accessId, strParam, apiInfo.getApiIp(), port, deferredResult);
    }

    @Override
    public Object getResult() {
        return deferredResult;
    }

    public String mapToString(Map<String, String> map) {
        StringBuilder paramStr = new StringBuilder();
        int index = 0;
        int size = map.size();
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            paramStr.append(entry.getKey());
            paramStr.append("=");
            paramStr.append(entry.getValue());
            if (index < size - 1) {
                paramStr.append("&");
            }
            index++;
        }
        return paramStr.toString();
    }

    public String getValue(Map<String, String> map, String key) {
        if (map.containsKey(key)) {
            return (String) map.get(key);
        }
        return "";
    }

    public String getObjectValue(Map<String, Object> map, String key) {
        if (map.containsKey(key)) {
            return (String) map.get(key);
        }
        return "";
    }
}
