package com.gxecard.customerservice.dispatch;

import com.gxecard.customerservice.constant.AccountConfig;
import com.gxecard.customerservice.constant.ErrorMsg;
import com.gxecard.customerservice.constant.TradeStatus;
import com.gxecard.customerservice.entity.BaseRespMessage;
import com.gxecard.customerservice.entity.Charge;
import com.gxecard.customerservice.entity.resp.BasicRespMessage;
import com.gxecard.customerservice.service.AccessControll;
import com.gxecard.customerservice.service.MessageService;
import com.gxecard.customerservice.util.HttpRequest;
import com.gxecard.customerservice.util.RSACoder;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

import static net.sf.json.JSONObject.fromObject;

/**
 * 充值请求，转发前需要在统一账户扣款
 */
@Slf4j
public class MhRechargeApplyTransmit extends MhCommonTransmit {


    public MhRechargeApplyTransmit(MessageService messageService) {
        super(messageService);
    }

    public void transmit(int accessId, AccessControll.ApiInfo apiInfo, Map<String, String> param) throws Exception {
        String outTradeNo = getValue(param, "terminalTransNo");// 商户订单号
        // 申请扣款
        JSONObject result = chargeback(param);
        //   扣款结果
        String chargeResult = result.getString("result");
        if (chargeResult.contentEquals("0")) {
            // 扣款成功，记录到数据库
            String code = result.getString("code");
            String msg = result.getString("msg");
            String sign = result.getString("sign");
            String tradeNo = result.getString("tradeNo");
            String userId = result.getString("userId");
            String totalFee = result.getString("totalFee");
            String payFee = result.getString("PayFee");
            String payTime = result.getString("payTime");
            int status = TradeStatus.RECHARGED_NOT_COMFIRM;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date time = sdf.parse(payTime);
            Charge charge = new Charge(code, chargeResult, msg, sign, tradeNo, outTradeNo, userId, totalFee, payFee, time, status);
            Charge saveCharge = messageService.saveCharge(charge);
//            int update = messageService.update(saveTradeSql, );
            if (saveCharge != null) {
                // 申请充值
                int port = Integer.valueOf(apiInfo.getApiPort());
                String strParam = JSONObject.fromObject(param).toString();
                messageService.transmit(accessId, strParam, apiInfo.getApiIp(), port, deferredResult);
            } else {

            }
        } else {
            // 扣款失败，通知前端
            String errorDsc = ErrorMsg.getMsg(chargeResult);
            BaseRespMessage baseRespMessage = new BasicRespMessage();
            baseRespMessage.setMessageType(apiInfo.getApiCode());
            baseRespMessage.setErrorDescription(errorDsc);
            baseRespMessage.setResponseCode(chargeResult);
            baseRespMessage.setLogIndexId(accessId + "");
            deferredResult.setResult(baseRespMessage);
        }

    }

    /**
     * 到统一账户申请扣费
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    public JSONObject chargeback(Map<String, String> paramMap) throws Exception {
        Map<String, String> param = new TreeMap<String, String>();
        param.put("outTradeNo", getValue(paramMap, "terminalTransNo")); //商户订单号
        param.put("orderDesc", "wfcRecharege"); // 订单描述
        param.put("orderTime", getValue(paramMap, "orderTime")); //交易时间
        param.put("totalFee", getValue(paramMap, "rechargeAmount"));// 交易金额，单位分
        param.put("userNo", getValue(paramMap, "userNo")); // 统一账户的用户编号
        Random random = new Random();
        String randomStr = "";
        for (int i = 0; i < 16; i++) {
            randomStr += random.nextInt(10);
        }
        param.put("randomStr", randomStr);
        // 转成key=value 形式签名
        String data = mapToString(param);
        // 对参数签名
        String priKey = AccountConfig.priKey;
        String sign = RSACoder.sign(data.getBytes(), priKey);

        param.put("sign", sign);
        // 转成json
        JSONObject jsonParam = fromObject(param);
        String paramJson = jsonParam.toString();
        String result = HttpRequest.sendPost(AccountConfig.rechargeUrl, paramJson);
        log.info("扣费结果:{}", result);
        return JSONObject.fromObject(result);
    }


}
