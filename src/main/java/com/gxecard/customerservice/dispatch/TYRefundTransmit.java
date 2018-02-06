package com.gxecard.customerservice.dispatch;

import com.gxecard.customerservice.constant.AccountConfig;
import com.gxecard.customerservice.constant.TradeStatus;
import com.gxecard.customerservice.service.AccessControll;
import com.gxecard.customerservice.service.MessageService;
import com.gxecard.customerservice.util.HttpRequest;
import com.gxecard.customerservice.util.RSACoder;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

import static net.sf.json.JSONObject.fromObject;

/**
 * 统一账户冲正订单
 */
@Slf4j
public class TYRefundTransmit extends HttpCommonTransmit {

    public TYRefundTransmit(MessageService messageService) {
        super(messageService);
    }

    @Override
    public void transmit(int accessId, AccessControll.ApiInfo apiInfo, Map<String, String> param) throws Exception {
        // 获取订单号
        String outTradeNo = getValue(param, "outTradeNo");
        // 冲正
        JSONObject refundResult = refund(param);
        log.info("冲正结果：{}", refundResult.toString());
        if (refundResult.getString("result").contentEquals("0")) {
            // 冲正成功，修改状态
            messageService.updateChargeStatusByOutTradeNoEquals(outTradeNo, TradeStatus.RECHARGED_AND_REFOUND);
        } else {
            // 更新状态
            messageService.updateChargeStatusByOutTradeNoEquals(outTradeNo, TradeStatus.RECHARGED_REFOUND_FAIL);
        }
        setResult(refundResult.toString());


    }

    private JSONObject refund(Map<String, String> param) throws Exception {
        Map<String, String> reParam = new TreeMap<String, String>();
        reParam.put("appId", AccountConfig.APPID);
        reParam.put("timestamp", getValue(param, "timestamp"));
        reParam.put("outTradeNo", getValue(param, "outTradeNo"));
//        reParam.put("tradeNo", "");
        reParam.put("randomStr", getValue(param, "randomStr"));
        String paramStr = mapToString(reParam);
        // 签名
        String sign = RSACoder.sign(paramStr.getBytes(), AccountConfig.priKey);
        reParam.put("sign", sign);
        // 转成json
        JSONObject jsonParam = fromObject(reParam);
        String paramJson = jsonParam.toString();
        String result = HttpRequest.sendPost(AccountConfig.refundUrl, paramJson);
        return JSONObject.fromObject(result);

    }
}
