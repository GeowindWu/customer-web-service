package com.gxecard.customerservice.dispatch;

import com.gxecard.customerservice.service.AccessControll;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 策略模式
 * 传输，转发的策略
 */
public interface TransmitStrategy {
    public void transmit(int accessId, AccessControll.ApiInfo apiInfo, Map<String, String> param) throws Exception;

    public Object getResult();
}
