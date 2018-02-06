package com.gxecard.customerservice.dispatch;

import com.gxecard.customerservice.service.AccessControll;
import com.gxecard.customerservice.service.MessageService;

import java.util.Map;
import java.util.Set;

/**
 * 转发到http接口的通用类
 */
public class HttpCommonTransmit implements TransmitStrategy {
    /**
     * http请求返回的结果
     */
    private String response;
    public MessageService messageService;

    public HttpCommonTransmit(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void transmit(int accessId, AccessControll.ApiInfo apiInfo, Map<String, String> param) throws Exception {

    }

    @Override
    public Object getResult() {
        return response;
    }

    public void setResult(String result) {
        this.response = result;
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
