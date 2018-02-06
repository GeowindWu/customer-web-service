package com.gxecard.customerservice.dispatch;

import com.gxecard.customerservice.constant.AccountConfig;
import com.gxecard.customerservice.constant.ErrorMsg;
import com.gxecard.customerservice.constant.TradeStatus;
import com.gxecard.customerservice.dao.ChargeDao;
import com.gxecard.customerservice.entity.BaseRespMessage;
import com.gxecard.customerservice.entity.Charge;
import com.gxecard.customerservice.entity.resp.BasicRespMessage;
import com.gxecard.customerservice.service.AccessControll;
import com.gxecard.customerservice.service.MessageService;
import com.gxecard.customerservice.util.HttpRequest;
import com.gxecard.customerservice.util.RSACoder;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.text.SimpleDateFormat;
import java.util.*;

import static net.sf.json.JSONObject.fromObject;

/**
 * 充值确认接口
 */

@Slf4j
public class MhRechareComfirmTransmit extends MhCommonTransmit {


    public MhRechareComfirmTransmit(MessageService messageService) {
        super(messageService);

    }

    /**
     * 充值确认接口新增一个参数 tradeNo
     *
     * @param accessId
     * @param apiInfo
     * @param param
     * @throws Exception
     */

    @Override
    public void transmit(int accessId, AccessControll.ApiInfo apiInfo, Map<String, String> param) throws Exception {

        // 统一账户部分
        String outTradeNo = getValue(param, "terminalTransNo");
        if (!checkWriteCardResult(param)) {
            // 写卡失败，冲正订单
            Charge charge = messageService.findChargeByOutTradeNoEquals(outTradeNo);
            if (charge != null) {
                int status = charge.getStatus();
                if (status == TradeStatus.RECHARGED_NOT_COMFIRM || status == TradeStatus.RECHARGED_REFOUND_FAIL) {
                    // 扣费未确认或者充正失败的订单
                    JSONObject refundResult = refund(param, charge);
                    log.info("冲正结果：{}", refundResult.toString());
                    if (refundResult.getString("result").contentEquals("0")) {
                        // 冲正成功，修改状态
                        messageService.updateChargeStatusByOutTradeNoEquals(outTradeNo, TradeStatus.RECHARGED_AND_REFOUND);
                    } else {
                        // 冲正失败
                        String result = refundResult.getString("result");
                        String errorDsc = ErrorMsg.getMsg(result);
                        BaseRespMessage baseRespMessage = new BasicRespMessage();
                        baseRespMessage.setMessageType(apiInfo.getApiCode());
                        baseRespMessage.setErrorDescription(errorDsc);
                        baseRespMessage.setResponseCode(result);
                        baseRespMessage.setLogIndexId(accessId + "");
                        deferredResult.setResult(baseRespMessage);
                        // 更新状态
                        messageService.updateChargeStatusByOutTradeNoEquals(outTradeNo, TradeStatus.RECHARGED_REFOUND_FAIL);
                    }

                }
            } else {
                // 充值订单不存在
                String errorDsc = ErrorMsg.getMsg(ErrorMsg.NO_ORDER);
                BaseRespMessage baseRespMessage = new BasicRespMessage();
                baseRespMessage.setMessageType(apiInfo.getApiCode());
                baseRespMessage.setErrorDescription(errorDsc);
                baseRespMessage.setResponseCode(ErrorMsg.ERROR_CODE);
                baseRespMessage.setLogIndexId(accessId + "");
                deferredResult.setResult(baseRespMessage);

            }

        } else {
            // 写卡成功，更新记录交易状态
            messageService.updateChargeStatusByOutTradeNoEquals(outTradeNo, TradeStatus.RECHARGED_AND_COMFIRM);
        }

        // 无论什么结果，都要转发写卡确认请求到铭鸿
        int port = Integer.valueOf(apiInfo.getApiPort());
        String strParam = JSONObject.fromObject(param).toString();
        messageService.transmit(accessId, strParam, apiInfo.getApiIp(), port, deferredResult);


    }


    private String appId = "90003";

    private JSONObject refund(Map<String, String> param, Charge charge) throws Exception {
        Map<String, String> reParam = new TreeMap<String, String>();
        reParam.put("appId", AccountConfig.APPID);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        reParam.put("timestamp", date);
        reParam.put("outTradeNo", charge.getOutTradeNo());
        reParam.put("tradeNo", charge.getTradeNo());
        String randomStr = "";
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            randomStr += random.nextInt(10);
        }
        reParam.put("randomStr", randomStr);
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

    /**
     * 检查返回的写卡结果
     *
     * @param param
     * @return
     */
    private boolean checkWriteCardResult(Map<String, String> param) {
        String writeCardResult = getValue(param, "writeCardResult");
        if (writeCardResult.contentEquals("0")) {// 0 成功，1 失败， 2 未知
            return true;
        }
        return false;
    }
}
