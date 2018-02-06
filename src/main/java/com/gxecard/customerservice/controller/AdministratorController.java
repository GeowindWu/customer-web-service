package com.gxecard.customerservice.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gxecard.customerservice.service.MessageService;
import com.gxecard.customerservice.util.Utilitys;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

@RestController
@Slf4j
public class AdministratorController extends BaseController {
	
	@Autowired
	public AdministratorController(MessageService messageService) {
		super(messageService);
	}

	@RequestMapping(value="/addAdmin.do",method=RequestMethod.POST)
	public JSONObject addAdmini(String operator ,String adminName,String department,String phoneNo,
			String status,String userType){
		JSONObject result = new JSONObject();
		if(isRoot(operator)){ //超级管理员
			String uuid = produceAccessUuid();
			String sqlInsert = "insert https_access_log.administrator (adminName,adminUUID,department,phoneNo,status,userType,password,gmt_create,gmt_modified)"
					+ "values('" + adminName + "','" + uuid + "','" +department+"','"+ phoneNo + "','"+status+"','"
					+ userType+"'," + "'123456', now(),now())";
			int update = messageService.update(sqlInsert);
			
			if(update > 0){
				result.put("serverStatus", "true");
				result.put("MSG", "新增成功");
			}else{
				result.put("serverStatus", "false");
				result.put("MSG", "新增失败");
			}
		}else{
			result.put("serverStatus", "false");
			result.put("MSG", "非超级管理员无新增权限");
		}
		
		
		return result;
	}
	
	@RequestMapping(value="/queryAdmin.do",method=RequestMethod.POST)
	public JSONObject queryAdmin(int page,int rows){
		String sql = "select admin_id,adminUUID,adminName,phoneNo,department,"
				+ "DATE_FORMAT(gmt_create,'%Y-%m-%d %H:%i:%s') as gmt_create,status"
				+ " from https_access_log.administrator ";
		String sqlCount = "select Count(*) as count"
				+ " from https_access_log.administrator ";
		JSONObject result = paginationQuery(rows, page, sql, sqlCount);
		return result;
	}
	
	@RequestMapping(value="/deleteAdmin.do",method=RequestMethod.POST)
	public JSONObject deleteAdmin(String operator, long adminId){
		JSONObject result = new JSONObject();
		if(isRoot(operator)){ //超级管理员
			String sql = "delete from https_access_log.administrator where admin_id = ?";
			int update = messageService.update(sql, adminId);
			if(update > 0){
				result.put("serverStatus", "true");
				result.put("MSG", "删除成功");
			}else{
				result.put("serverStatus", "false");
				result.put("MSG", "删除失败");
			}
		}else{
			result.put("serverStatus", "false");
			result.put("MSG", "非超级管理员无删除权限");
		}
		
		return result;
	}
	private boolean isRoot(String operator){
		String getOperatorInfo = "select userType from https_access_log.administrator where adminName = ?";
		Map<String, Object> operatorInfo = messageService.queryMap(getOperatorInfo, operator);
		String type = (String) operatorInfo.get("userType");
		if("1".contentEquals(type.trim())){ //超级管理员
			return true;
		}else{
			return false;
		}
	}
	@RequestMapping(value="/enableAdmin.do",method=RequestMethod.POST)
	public JSONObject userEnable(String operator,long adminId){
		JSONObject result = new JSONObject();
		if(isRoot(operator)){
			String sql ="update https_access_log.administrator set status = '正常' where admin_id = ?"; 
			int update = messageService.update(sql, adminId);
			if(update > 0){
				result.put("serverStatus", "true");
				result.put("MSG", "启用成功");
			}else{
				result.put("serverStatus", "false");
				result.put("MSG", "启用失败");
			}
		}else{
			result.put("serverStatus", "false");
			result.put("MSG", "非超级管理员无启用权限");
		}
		
		return result;
	}
	@RequestMapping(value="/disableAdmin.do",method=RequestMethod.POST)
	public JSONObject userDisable(String operator,long adminId){
		JSONObject result = new JSONObject();
		if(isRoot(operator)){
			String sql ="update https_access_log.administrator set status = '禁用' where admin_id = ?"; 
			int update = messageService.update(sql, adminId);
			if(update > 0){
				result.put("serverStatus", "true");
				result.put("MSG", "禁用成功");
			}else{
				result.put("serverStatus", "false");
				result.put("MSG", "禁用失败");
			}
		}else{
			result.put("serverStatus", "false");
			result.put("MSG", "非超级管理员无禁用权限");
		}
	
		return result;
	}
	
	/**
	 * 生成访问记录唯一UUID
	 * 
	 * @return
	 */
	public String produceAccessUuid() {
		String uuid = UUID.randomUUID().toString();
		String uuidIndex = Utilitys.getCurTimeByFromt("yyMMddHHmm") + "_"
				+ uuid;
		return uuidIndex;
	}
}
