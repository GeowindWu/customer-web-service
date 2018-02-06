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
public class UserController extends BaseController {
	
	@Autowired
	public UserController(MessageService messageService) {
		super(messageService);
	}

	@RequestMapping(value="/queryUser.do",method=RequestMethod.POST)
	public JSONObject queryUser(int page,int rows){
		String sql = "select tid,IFNULL(user_name,'') as user_name,access_ip,access_uuid,IFNULL(user_pub_key,'') as pub_key,DATE_FORMAT(begin_time,'%Y-%m-%d %H:%i:%s') as begin_time,DATE_FORMAT(end_time,'%Y-%m-%d %H:%i:%s') as end_time,effective_trage from "
				+ " https_access_log.access_control";
		String sqlCount =  "select Count(*) as count from  https_access_log.access_control";
		
		JSONObject result = paginationQuery(rows, page, sql, sqlCount);
				
		return result;
	}
	
	@RequestMapping(value="/addUser.do",method=RequestMethod.POST)
	public JSONObject addUser(String accessName,String accessIP,String accessUUID,String pubKey,String beginTime,String endTime){
	
		String sql = "insert into https_access_log.access_control (user_name,access_ip,access_uuid,user_pub_key,begin_time,end_time,user_key,user_randomkey,"
				+ "parameter_key,user_code,uuid_version,user_key1,user_key2,effective_trage)"
				+ " values('" + accessName+"','"+accessIP+"','"+accessUUID + "','" + pubKey  +"','"+beginTime + "','"+endTime + "','"+ "','" +"','"+ "','"+
				"','"+ "','"+ "','"+ "',"+ 1+ ")";
		int update = messageService.update(sql);
		JSONObject result = new JSONObject();
		if(update > 0){
			result.put("serverStatus", "true");
			result.put("MSG", "新增成功");
		}else{
			result.put("serverStatus", "false");
			result.put("MSG", "新增失败");
		}
		return result;
	}
	
	@RequestMapping(value="/deleteUser.do",method=RequestMethod.POST)
	public JSONObject deleteUser(String userId){
		String sql = "delete from https_access_log.access_control where tid = ? ";
		int update = messageService.update(sql, userId);
		JSONObject result = new JSONObject();
		if(update > 0){
			result.put("serverStatus", "true");
			result.put("MSG", "删除成功");
		}else{
			result.put("serverStatus", "false");
			result.put("MSG", "删除失败");
		}
		return result;
	}
	
	
	@RequestMapping(value="/changeUser.do",method=RequestMethod.POST)
	public JSONObject changeUser(String userId,String accessName,String accessIP,String accessUUID,String pubKey,String beginTime,String endTime){
		String sql = "update  https_access_log.access_control set user_name = ?,access_ip= ?,access_uuid = ?,user_pub_key = ? ,begin_time = ?,end_time = ? where tid = ?";
		int update = messageService.update(sql,accessName,accessIP,accessUUID,pubKey,beginTime,endTime,userId);
		JSONObject result = new JSONObject();
		if(update > 0){
			result.put("serverStatus", "true");
			result.put("MSG", "修改成功");
		}else{
			result.put("serverStatus", "false");
			result.put("MSG", "修改失败");
		}
		return result;
	}

	
	@RequestMapping(value="/userEnable.do",method=RequestMethod.POST)
	public JSONObject userEnable(String userId){
		String sql  = "update https_access_log.access_control set effective_trage = 1 where tId = ?";
		int update = messageService.update(sql, userId);
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
	@RequestMapping(value="/userDisable.do",method=RequestMethod.POST)
	public JSONObject userDisable(String userId){
		String sql  = "update https_access_log.access_control set effective_trage = 0 where tId = ?";
		int update = messageService.update(sql, userId);
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
}
