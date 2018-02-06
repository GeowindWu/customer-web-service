package com.gxecard.customerservice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gxecard.customerservice.service.AccessControll;
import com.gxecard.customerservice.service.MessageService;
import com.gxecard.customerservice.util.HtppsConfig;
import com.gxecard.customerservice.util.HttpsRequest;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
@Slf4j
public class SystemMonitorController extends BaseController {
	@Value("${system.systemMonitor}")
	private String monitorUrl;
	private  HtppsConfig defaultHttpConfig;
	public SystemMonitorController(MessageService messageService,HtppsConfig defaultHttpConfig) {
		super(messageService);
		this.defaultHttpConfig = defaultHttpConfig;
	}

	@RequestMapping(value="/monitor.do",method=RequestMethod.POST)
	public JSONObject apiMonitor(int page,int rows){
		Map<String,String> params = new HashMap<String,String>();
		JSONObject paramContent = new JSONObject();
		paramContent.put("messageType", "2058");
		paramContent.put("terminalNo", "0");
		paramContent.put("accessUser", "system_user");
		params.put("parameter", paramContent.toString());
		String httpsAccess; 
		String replaceCode = "99999";
		try {
			httpsAccess = HttpsRequest.httpsAccess(monitorUrl, params, "utf-8",defaultHttpConfig);
			log.info("clock api return ：" +httpsAccess);
			JSONObject json = JSONObject.fromObject(httpsAccess);
			String responseCode = (String) json.get("responseCode");
			if("00000".equals(responseCode)){
				replaceCode = responseCode;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 具体接口编码含义查看手册 
		List<String> apiCodes = AccessControll.getAllApiCode();
		// apiCodes.add("5088");
		// apiCodes.add("0089");
		// apiCodes.add("5021");
		// apiCodes.add("2002");
//		apiCodes.add("5002");
//		apiCodes.add("1003");
//		apiCodes.add("5004");
//		apiCodes.add("1005");
//		apiCodes.add("2057");
		// apiCodes.add("5093");
		// apiCodes.add("0094");
		// apiCodes.add("5095");
		// apiCodes.add("5029");
		// apiCodes.add("5023");
		// apiCodes.add("5096");
		String queryStr = "select api_name,api_code, DATE_FORMAT(access_begin,'%Y-%m-%d %H:%i:%s') as access_begin,response_code from https_access_log.access_log where api_code = ? limit 1";
		JSONObject result = new JSONObject();
		
		List queryResult = new ArrayList<Map<String,String>>();
		for (String code : apiCodes) {
			List list = messageService.query(queryStr,code);
			if (null != list && list.size() != 0) {
				Map<String,String> map = (Map<String, String>) list.get(0);
				if(map.get("api_name").equals("")){
					map.put("api_name", AccessControll.getApiNameByMessageType(code));
				}
				queryResult.add(map);
			}else{
				Map<String,String> map = new HashMap<String, String>();
				map.put("api_name", AccessControll.getApiNameByMessageType(code));
				map.put("api_code", code);
				map.put("access_begin", "无访问记录");
				map.put("response_code", "99999");
				queryResult.add(map);
			}
		}
		
		int fromIndex = (page - 1) * rows;
		int toIndex;
		int total = queryResult.size();
		if (page - 1 == total / rows) {
			// 最后一页
			toIndex = total;
		} else {
			toIndex = page * rows;
		}
		result.put("rows", queryResult.subList(fromIndex, toIndex));
		result.put("total", queryResult.size());
		// 修改result的response，根据前面查询结果
		JSONArray array = result.getJSONArray("rows");
		for(int i = 0; i < array.size(); i ++){
			JSONObject jo = array.getJSONObject(i);
			jo.put("response_code",replaceCode);
		}
		return result;
	}
}
