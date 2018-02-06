package com.gxecard.customerservice.dispatch;

import com.google.gson.Gson;
import com.gxecard.customerservice.constant.AccountConfig;
import com.gxecard.customerservice.constant.TradeStatus;
import com.gxecard.customerservice.entity.BaseRespMessage;
import com.gxecard.customerservice.entity.Charge;
import com.gxecard.customerservice.service.AccessControll;
import com.gxecard.customerservice.service.MessageService;
import com.gxecard.customerservice.util.HttpRequest;
import com.gxecard.customerservice.util.RSACoder;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.context.request.async.DeferredResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 统一账户扣费
 */
@Slf4j
public class TYChargeApplyTransmit extends HttpCommonTransmit {


    public TYChargeApplyTransmit(MessageService messageService) {
        super(messageService);
    }

    @Override
    public void transmit(int accessId, AccessControll.ApiInfo apiInfo, Map<String, String> param) throws Exception {
        // 去掉accessUser，messageType
        param.remove("accessUser");
        param.remove("messageType");
        // 参数排序
        Map<String, String> orderParam = new TreeMap<String, String>();
        Set<Map.Entry<String, String>> entries = param.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            orderParam.put(entry.getKey(), entry.getValue());
        }
        String paramStr = mapToString(orderParam);
        // 签名
        String sign = RSACoder.sign(paramStr.getBytes(), AccountConfig.priKey);
        orderParam.put("sign", sign);
        // 转json
        JSONObject jsonParam = JSONObject.fromObject(orderParam);
        String paramJson = jsonParam.toString();
        String result = HttpRequest.sendPost(apiInfo.getApiUrl(), paramJson);
        log.info("扣费结果:{}", result);
        // 保存结果（返回前端的）
        setResult(result);
        // 保存返回结果到数据库
        // 处理流程是否正常
        boolean successTrage = JSONObject.fromObject(result).getString("result").contentEquals("0") ? true : false;
        String inputStr = new Gson().toJson(param);
        // 记录输入输出
        messageService.updateServerParams(accessId, successTrage, result, inputStr);
        // 统一如果扣费成功，记录这笔扣费到数据库
        saveCharge(result);
    }

    private void saveCharge(String resultStr) throws ParseException {
        JSONObject result = JSONObject.fromObject(resultStr);
        String code = result.getString("code");
        String msg = result.getString("msg");
        String sign = result.getString("sign");
        String tradeNo = result.getString("tradeNo");
        String userId = result.getString("userId");
        String totalFee = result.getString("totalFee");
        String payFee = result.getString("PayFee");
        String payTime = result.getString("payTime");
        String chargeResult = result.getString("result");
        String outTradeNo = result.getString("outTradeNo");
        int status = TradeStatus.RECHARGED_NOT_COMFIRM;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = sdf.parse(payTime);
        Charge charge = new Charge(code, chargeResult, msg, sign, tradeNo, outTradeNo, userId, totalFee, payFee, time, status);
        Charge saveCharge = messageService.saveCharge(charge);
    }
}
