package com.gxecard.customerservice.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gxecard.customerservice.dispatch.DispatchCenter;
import com.gxecard.customerservice.entity.BaseReqMessage;
import com.gxecard.customerservice.util.*;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.gson.Gson;
import com.gxecard.customerservice.entity.BaseRespMessage;
import com.gxecard.customerservice.service.AccessControll;
import com.gxecard.customerservice.service.AccessControll.AccessContUnit;
import com.gxecard.customerservice.service.AccessControll.ApiInfo;
import com.gxecard.customerservice.service.CallSetDefferThread;
import com.gxecard.customerservice.service.MessageService;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/service")
@Slf4j
public class ServiceController {

	private MessageService messageService;
	private AccessControll accessControll;


	@Autowired
	public ServiceController(MessageService messageService, AccessControll accessControll) {
		this.messageService = messageService;
		this.accessControll = accessControll;
	}

	// TODO ：这个函数发生异常不会进入后续的处理流程中去，直接到反馈前台。
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public DeferredResult serve(String parameter, String ecardSign, HttpServletRequest request)
			throws Exception {
        // 定义要返回的结果
        DeferredResult deferredResult = new DeferredResult<>();
        int accessId = -100;
        try {
            // 第一步：写入文本日志
            String ipString = getRemoteAddress(request);
            log.info("IP:{},Parameter:{}", ipString, parameter);

            // 第二步：写入数据日志开始
            Map<String, String> intputMap = messageService.jsonString2Map(parameter); // 获取转换的对象
            String messageType = Utilitys.getMapAttrValue(intputMap, EcardDictData.getMessageTypeAttrName());
            String accessUser = Utilitys.getMapAttrValue(intputMap, EcardDictData.getAccessUserAttrName());
            // 记录 访问用户，消息（访问接口）类型，请求方ip，请求的入参
            String apiName = AccessControll.getApiNameByMessageType(messageType);
            accessId = messageService.insertDbLog(accessUser, messageType, apiName,ipString, parameter);
            if (accessId <= 0) {// 数据库日志写入失败，提醒前台
                log.error("Log error in server!");
                throw new RuntimeException("Log error in server!");
            }
            if (Utilitys.isBlank(messageType)){
                log.error("Important information loss!");
                throw new RuntimeException("Important information loss!");
            }
            JSONObject checkAccessRule = accessControll.checkAccessRule(accessUser, ipString);
            log.info("checkResult: result = {},msg={}",checkAccessRule.getString("result"),checkAccessRule.getString("msg"));
            if (checkAccessRule.getString("result").contentEquals("false")){ // 检查输入参数是否有效
                log.error(checkAccessRule.getString("msg"));
                throw new RuntimeException(checkAccessRule.getString("msg"));
            }
            //  验接口 ，根据messageType判断访问的接口是否存在
            boolean existApiByMessageType = accessControll.existApiByMessageType(messageType);
            if(!existApiByMessageType){
                log.error("Inaccessible interface");
                throw new RuntimeException("无可访问的接口");
            }
            // 验签以后再做 ---吴洪全
//			// 第三步：验证签名 所有同名用户只能用一个密钥，不然就不能搞通配符
            if (Utilitys.isBlank(ecardSign))
                throw new RuntimeException("sign is not allow empty!");
            String pubKey = RSACoder.getPublicKeyFromConfig(accessControll, accessUser);
            log.info("公钥：{}",pubKey);
//			// 验证签名
            log.info("签名：{}",ecardSign);
            boolean verify = RSACoder.verify(parameter.getBytes(), pubKey, ecardSign);
            log.info("验签结果：{}",verify);
            if (!verify) {
                throw new RuntimeException("params sign is not right!");
            }
            log.info("signature is right ,signature check passed");

            // 获取接口信息，然后进行分发
            ApiInfo apiInfo = accessControll.getApiInfoByMessageType(messageType);
            if(apiInfo == null){
                log.error("Inaccessible interface");
                throw new RuntimeException("无可访问的接口");
            }else{
                DispatchCenter dispatchCenter = new DispatchCenter( messageService, apiInfo);
                deferredResult  = dispatchCenter.dispatch(accessId, intputMap);



//				//分发
//				int apiType = apiInfo.getApiType();
//				if(apiType == 1){
//					// webservice接口 新接口
//					// 获取接口url
//					String apiUrl = apiInfo.getApiUrl();
//					String result = "";
//					// 参数去掉 messageType 和 accessUser 字段，剩下的才是转发需要的参数
////					intputMap.remove("messageType");
////					intputMap.remove("accessUser");
//					if(apiUrl.contains("https")){
//						HtppsConfig htppsConfig = new HtppsConfig(apiInfo.getKeyStorePath(),
//								apiInfo.getTrustStorePath(),apiInfo.getKeyStorePw(),apiInfo.getTrustStorePw(),apiInfo.getKeyPw());
//						result = HttpsRequest.httpsAccess(apiUrl, intputMap, "utf-8",htppsConfig);
//
//					}else{
//						// http
//						result = HttpRequest.httpPost(apiUrl, intputMap);
//					}
//					//原来铭洪的socket接口就按照原来的处理，新进来的接口返回结果通通不封装，直接返回就行了
//					DeferredResult<String> httpResult = new DeferredResult<String>();
//					//保存请求和返回结果到数据库
//					messageService.updateServerParams(accessId, true, new Gson().toJson(result), "",
//							"", parameter.getBytes(), new byte[]{}, new byte[]{});
//					httpResult.setResult(result);
//					return httpResult;
//				}else if(apiType == 2){
//					// socket
//					// 第四步：开始异步调用请求
//					String apiPort = apiInfo.getApiPort();
//					int port = Integer.valueOf(apiPort);
//					messageService.transmit(accessId, parameter, apiInfo.getApiIp(),port,deferredResult);
//				}else{
//					log.error("Illegal interface type");
//					throw new RuntimeException("非法接口类型");
//				}
            }

        } catch (Exception e) {
            log.info("error Info:{}", e.getMessage());
            // 延迟返回错误，保证先执行return defferedRsult;
            CallSetDefferThread thread = new CallSetDefferThread(accessId, messageService, deferredResult,
                    e.getMessage());
            thread.start();
        }
        return deferredResult;

	}

	/***
	 * 所有同名用户只能用一个密钥，不然就不能搞通配符
	 * 
	 * @param aCont
	 * @param accessUser
	 * @return
	 */
//	private RsaSignUtil initUserRsa(AccessControll aCont, String accessUser) {
//		// 所有同名用户只能用一个密钥，不然就不能搞通配符
//		AccessContUnit unit = aCont.loadAccessCont(accessUser);
//		if (unit == null)
//			throw new RuntimeException("not find user by userName:" + accessUser);
//
//		return new RsaSignUtil(unit.getUserPubkey(), "", unit.getUserKey1(), unit.getUserKey2(), "");
//	}

	/**
	 * 获取IP地址
	 *
	 * @param request
	 * @return
	 */
	private String getRemoteAddress(HttpServletRequest request) {
		if (request == null) {
			return "";
		}
		String address = request.getHeader("x-forwarded-for");
		if (address == null || address.length() == 0 || "unknown".equalsIgnoreCase(address)) {
			address = request.getHeader("Proxy-Client-IP");
		}
		if (address == null || address.length() == 0 || "unknown".equalsIgnoreCase(address)) {
			address = request.getHeader("WL-Proxy-Client-IP");
		}
		if (address == null || address.length() == 0 || "unknown".equalsIgnoreCase(address)) {
			address = request.getRemoteAddr();
		}
		return address;
	}
}
