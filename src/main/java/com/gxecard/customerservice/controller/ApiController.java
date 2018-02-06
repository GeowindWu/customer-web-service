package com.gxecard.customerservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gxecard.customerservice.service.MessageService;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

@RestController
@Slf4j
public class ApiController extends BaseController {
	


	@Autowired
	public ApiController(MessageService messageService) {
		super(messageService);
	}

	@RequestMapping(value="/addApi.do",method=RequestMethod.POST)
	public JSONObject addApi(String apiName,String apiCode,int apiType,String apiUrl,String apiIp,String apiPort,int apiStatus,
			String keyStorePath,String trustStorePath,String keyStorePw,String trustStorePw,String keyPw){
		String sql = ""; 
		int update = -1;
		String msg = "";
		String status = "false";
		keyStorePath = keyStorePath ==null ? "" :keyStorePath;
		trustStorePath = trustStorePath ==null ? "" :trustStorePath;
		keyStorePw = keyStorePw ==null ? "" :keyStorePw;
		trustStorePw = trustStorePw ==null ? "" :trustStorePw;
		keyPw = keyPw ==null ? "" :keyPw;
		if(apiType == 1){//webservice
			sql = "insert into https_access_log.api (apiName,apiCode,apiType,apiUrl,apiStatus,keyStorePath,trustStorePath"
					+ ",keyStorePw,trustStorePw,keyPw) values(?,?,?,?,?,?,?,?,?,?) ";
			update = messageService.update(sql, apiName,apiCode,apiType,apiUrl,apiStatus,keyStorePath,trustStorePath,
					keyStorePw,trustStorePw,keyPw);
		
		}else if(apiType == 2){
			//socket
			sql = "insert into https_access_log.api (apiName,apiCode,apiType,apiIp,apiPort,apiStatus) values(?,?,?,?,?,?) ";
			update = messageService.update(sql, apiName,apiCode,apiType,apiIp,apiPort,apiStatus);
		}else{
			//非法类型
			msg = "非法接口类型";
		}
		log.info("add api sql：" + sql +"\n插入结果:" + update);
		JSONObject result = new JSONObject();
		if(update > 0){
			status = "true";
			msg = "新增成功";
		}else{
			status = "false";
			msg = "新增失败";
		}
		result.put("serverStatus", status);
		result.put("MSG", msg);
		return result;
	}
	
	@RequestMapping(value="/changeApi.do",method=RequestMethod.POST)
	public JSONObject changeApi(String apiName,String apiCode,int apiType,String apiUrl,String apiIp,String apiPort,String apiId
			,String keyStorePath,String trustStorePath,String keyStorePw,String trustStorePw,String keyPw){
		String sql = ""; 
		int update = -1;
		String msg = "";
		String status = "false";
		keyStorePath = keyStorePath ==null ? "" :keyStorePath;
		trustStorePath = trustStorePath ==null ? "" :trustStorePath;
		keyStorePw = keyStorePw ==null ? "" :keyStorePw;
		trustStorePw = trustStorePw ==null ? "" :trustStorePw;
		keyPw = keyPw ==null ? "" :keyPw;
		if(apiType == 1){//webservice
			sql = "update https_access_log.api set apiName = ? ,apiCode = ?,apiUrl = ? ,"
					+ " keyStorePath =?,trustStorePath = ?, keyStorePw = ?,trustStorePw = ?,keyPw = ?  "
					+ " where apiId = ?";
			update = messageService.update(sql, apiName,apiCode,apiUrl,keyStorePath,trustStorePath,
					keyStorePw,trustStorePw,keyPw,apiId);
		}else if(apiType == 2){//socket
			sql = "update https_access_log.api set apiName = ? ,apiCode = ?,apiIp = ?,apiPort = ?  where apiId = ?";
			update = messageService.update(sql, apiName,apiCode,apiIp,apiPort,apiId);
		}else{
			//非法类型
			msg = "非法接口类型";
		}
		log.info("update api sql：" + sql +"\n修改结果:" + update);
		JSONObject result = new JSONObject();
		if(update > 0){
			status = "true";
			msg = "修改成功";
		}else{
			status = "false";
			msg = "修改失败";
		}
		result.put("serverStatus", status);
		result.put("MSG", msg);
		return result;
	}
	
	@RequestMapping(value="queryApi.do",method=RequestMethod.POST)
	public JSONObject queryAllApi(int page,int rows){
		String sql = "select * from https_access_log.api";
		String sqlCount = "select Count(*) as count from https_access_log.api";
		JSONObject result = paginationQuery(rows, page, sql, sqlCount);
		return result;
	}
	
	@RequestMapping(value="/apiEnable.do",method=RequestMethod.POST)
	public JSONObject apiEnable(String apiId){
		String sql  = "update https_access_log.api set apiStatus = 1 where apiId = ?";
		int update = messageService.update(sql, apiId);
		JSONObject result = new JSONObject();
		if(update > 0){
			result.put("serverStatus","true");
			result.put("MSG", "启用成功");
		}else{
			result.put("serverStatus","false");
			result.put("MSG", "启用失败");
		}
		return result;
	}
	@RequestMapping(value="/apiDisable.do",method=RequestMethod.POST)
	public JSONObject apiDisable(String apiId){
		String sql  = "update https_access_log.api set apiStatus = 0 where apiId = ?";
		int update = messageService.update(sql, apiId);
		JSONObject result = new JSONObject();
		if(update > 0){
			result.put("serverStatus","true");
			result.put("MSG", "禁用成功");
		}else{
			result.put("serverStatus","false");
			result.put("MSG", "禁用失败");
		}
		return result;
	}
	@RequestMapping(value="/deleteApi.do",method=RequestMethod.POST)
	public JSONObject deleteApi(String apiId){
		String sql = "delete from https_access_log.api where apiId = ?";
		int update = messageService.update(sql, apiId);
		JSONObject result = new JSONObject();
		if(update > 0){
			result.put("serverStatus","true");
			result.put("MSG", "删除成功");
		}else{
			result.put("serverStatus","false");
			result.put("MSG", "删除失败");
		}
		return result;
	}
}
