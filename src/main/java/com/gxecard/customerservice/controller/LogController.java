package com.gxecard.customerservice.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gxecard.customerservice.service.AccessControll;
import com.gxecard.customerservice.service.MessageService;
import com.gxecard.customerservice.util.ExportFileUtil;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author 吴洪全
 *
 */
@RestController
@Slf4j
public class LogController extends BaseController {

	@Autowired
	public LogController(MessageService messageService) {
		super(messageService);
	}

	@Value("${ftp.filePath.server}")
	private String localFilePath;

	@RequestMapping(value = "/exportLog.do", method = RequestMethod.POST)
	public void exportLog(String accessid, String access_user, String access_api, String access_status,
			String access_start, String access_end, HttpServletResponse response) {

		StringBuilder sql = new StringBuilder()
				.append("select t.accessid,t.access_user,t.api_code,IFNULL(t.response_code,'') as response_code ,t.access_ip,IFNULL( t.error_desc,'') as error_desc,CONCAT(t.access_status,t.success_trage) as status ,DATE_FORMAT( t.access_begin,'%Y-%m-%d %H:%i:%s') as access_begin")
				.append("  FROM https_access_log.access_log t").append(" WHERE 1=1");
		if ((null != accessid && !accessid.equals(""))) {
			sql.append(" and t.accessid = " + accessid);
		}
		if ((null != access_user && !access_user.equals(""))) {
			sql.append(" and t.access_user = '" + access_user + "'");
		}
		if ((null != access_api && !access_api.equals(""))) {
			String apiCode = AccessControll.getApiCodeByName(access_api);
			sql.append(" and t.api_code = " + apiCode);
		} else {
			// sqlCount.append(" and t.api_code IN (5002,1003,5004,1005,2057)
			// ");
			// 获取所有配置的api_code
			List<String> apiCode = AccessControll.getAllApiCode();
			sql.append(" and t.api_code IN (");
			int size = apiCode.size();
			for (int i = 0; i < size; i++) {
				sql.append(apiCode.get(i));
				if (i < size - 1) {
					sql.append(",");
				}
			}
			sql.append(")");
		}
		if ((null != access_status && !access_status.equals(""))) {
			// 其实包含两个字段的 success_trage，access_status所以要拆分值，分别赋值
			if (access_status.equals("11")) {// 查询接口正常状态的记录
				sql.append(" and t.access_status = 1 and t.success_trage = 1");
			} else {// 查询异常记录
				sql.append(" and not (t.access_status = 1 and t.success_trage = 1)");
			}
		}
		if ((null != access_start && !access_start.equals(""))) {
			sql.append(" and t.access_begin >= '" + access_start + "'");
		}
		if ((null != access_end && !access_end.equals(""))) {
			sql.append(" and t.access_end <= '" + access_end + "'");
		}
		log.info("export log sql： " + sql.toString());
		List queryResult = messageService.query(sql.toString());
		System.out.println(queryResult.toString());
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("accessid", "日志序号");
		map.put("access_begin", "访问时间");
		map.put("access_ip", "访问IP");
		map.put("api_code", "接口名称");
		map.put("response_code", "响应代码");
		map.put("error_desc", "错误描述");
		map.put("access_user", "访问用户");
		map.put("status", "接口状态(11为正常，其他为异常");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = dateFormat.format(new java.util.Date()) + ".csv";

		log.info("begin export file ,file directory ：" + localFilePath + fileName);

		ExportFileUtil.createCSVFile(queryResult, map, localFilePath, fileName);
		try {
			ExportFileUtil.exportFile(response, localFilePath, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("export file is completed");
	}

	@RequestMapping(value = "/queryLog.do", method = RequestMethod.POST)
	public JSONObject queryLog(String accessid, String access_user, String access_api, String access_status,
			String access_start, String access_end, int page, int rows, HttpServletResponse response) {
		// net.sf.json.JSONObject
		// Page<AccessLog> page = new Page<AccessLog>(rows);
		// page.setPageNo(pageNo);
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select t.accessid,t.access_user,t.api_name,t.api_code,IFNULL(t.response_code,'') as response_code,t.access_ip,IFNULL( t.error_desc,'') as error_desc ,CONCAT(t.access_status,t.success_trage) as status ,DATE_FORMAT( t.access_begin,'%Y-%m-%d %H:%i:%s') as access_begin")
				.append("  FROM https_access_log.access_log t").append(" WHERE 1=1");
		if ((null != accessid && !accessid.equals(""))) {
			sql.append(" and t.accessid = " + accessid);
		}
		if ((null != access_user && !access_user.equals(""))) {
			sql.append(" and t.access_user = '" + access_user + "'");
		}
		if ((null != access_api && !access_api.equals(""))) {
			String apiCode = AccessControll.getApiCodeByName(access_api);
			sql.append(" and t.api_code = " + apiCode);
		} else {
			// 获取所有配置的api_code
			List<String> apiCode = AccessControll.getAllApiCode();
			sql.append(" and t.api_code IN (");
			int size = apiCode.size();
			for (int i = 0; i < size; i++) {
				sql.append(apiCode.get(i));
				if (i < size - 1) {
					sql.append(",");
				}
			}
			sql.append(")");
		}
		if ((null != access_status && !access_status.equals(""))) {
			// 其实包含两个字段的 success_trage，access_status所以要拆分值，分别赋值
			if (access_status.equals("11")) {// 查询接口正常状态的记录
				sql.append(" and t.access_status = 1 and t.success_trage = 1");
			} else {// 查询异常记录

				sql.append(" and not (t.access_status = 1 and t.success_trage = 1)");
			}

		}
		if ((null != access_start && !access_start.equals(""))) {
			sql.append(" and t.access_begin >= '" + access_start + "'");
		}
		if ((null != access_end && !access_end.equals(""))) {
			sql.append(" and t.access_end <= '" + access_end + "'");
		}
		StringBuilder sqlCount = new StringBuilder();
		sqlCount.append("SELECT Count(*) as count").append("  FROM https_access_log.access_log t").append(" WHERE 1=1");
		if ((null != accessid && !accessid.equals(""))) {
			sqlCount.append(" and t.accessid = " + accessid);
		}
		if ((null != access_user && !access_user.equals(""))) {
			sqlCount.append(" and t.access_user = '" + access_user + "'");
		}
		if ((null != access_api && !access_api.equals(""))) {
			String apiCode = AccessControll.getApiCodeByName(access_api);
			sqlCount.append(" and t.api_code = " + apiCode);
		} else {
			// sqlCount.append(" and t.api_code IN (5002,1003,5004,1005,2057)
			// ");
			// 获取所有配置的api_code
			List<String> apiCode = AccessControll.getAllApiCode();
			sqlCount.append(" and t.api_code IN (");
			int size = apiCode.size();
			for (int i = 0; i < size; i++) {
				sqlCount.append(apiCode.get(i));
				if (i < size - 1) {
					sqlCount.append(",");
				}
			}
			sqlCount.append(")");
		}
		if ((null != access_status && !access_status.equals(""))) {
			// 其实包含两个字段的 success_trage，access_status所以要拆分值，分别赋值
			if (access_status.equals("11")) {// 查询接口正常状态的记录
				sqlCount.append(" and t.access_status = 1 and t.success_trage = 1");
			} else {// 查询异常记录
				sqlCount.append(" and not (t.access_status = 1 and t.success_trage = 1)");
			}
		}
		if ((null != access_start && !access_start.equals(""))) {
			sqlCount.append(" and t.access_begin >= '" + access_start + "'");
		}
		if ((null != access_end && !access_end.equals(""))) {
			sqlCount.append(" and t.access_end <= '" + access_end + "'");
		}
		
		JSONObject result = paginationQuery(rows, page, sql.toString(), sqlCount.toString());
		return result;
	}

	@RequestMapping(value = "/logStatistic.do", method = RequestMethod.POST)
	public JSONObject logStatistic(int page, int rows) {
		List<String> apiCodes = AccessControll.getAllApiCode();
		List<InnerLog> resultList = new ArrayList<InnerLog>();

		for (String apiCode : apiCodes) {
			String sql = "select a.accessid,a.access_ip,a.api_name,a.api_code,a.access_user "
					+ " from https_access_log.access_log a where a.api_code = ? limit 1";
			List<Map<String, Object>> list = messageService.query(sql, apiCode);
			InnerLog log = new InnerLog();
			if (null != list && list.size() > 0) {
				Map<String, Object> map = list.get(0);
				log.setAccessid(map.get("accessid") + "");
				log.setAccess_ip(map.get("access_ip") + "");
				log.setAccess_user(map.get("access_user") + "");
				log.setApi_code(map.get("api_code") + "");
				log.setApi_name(map.get("api_name") + "");
				String failSql = "select COUNT(*) as failCount from https_access_log.access_log a where a.api_code = ? and  not (a.success_trage=1 and a.access_status=1)";
				Map<String, Object> failMap = messageService.queryMap(failSql, apiCode);
				log.setFailCount(failMap.get("failCount") + "");

				String successSql = "select COUNT(*) as successCount from https_access_log.access_log a where a.api_code = ? and a.success_trage=1 and a.access_status=1";
				Map<String, Object> successMap = messageService.queryMap(successSql, apiCode);
				log.setSuccessCount(successMap.get("successCount") + "");
			} else {
				log.setApi_code(apiCode);
				log.setApi_name(AccessControll.getApiNameByMessageType(apiCode));
				log.setAccess_ip("无访问记录");
				log.setAccess_user("无访问记录");
				log.setAccessid("-1");
				log.setFailCount("0");
				log.setSuccessCount("0");
			}

			resultList.add(log);
		}
		JSONObject result = new JSONObject();
		int fromIndex = (page - 1) * rows;
		int toIndex;
		int total = resultList.size();
		if (page - 1 == total / rows) {
			// 最后一页
			toIndex = total;
		} else {
			toIndex = page * rows;
		}
		List<InnerLog> subList = resultList.subList(fromIndex, toIndex);
		JSONArray array = new JSONArray();
		for (InnerLog log : subList) {
			JSONObject jo = new JSONObject();
			jo.put("accessid", log.getAccessid());
			jo.put("access_ip", log.getAccess_ip());
			jo.put("api_code", log.getApi_code());
			String api_name = log.getApi_name();
			if (api_name.equals("")) {
				jo.put("api_name", AccessControll.getApiNameByMessageType(log.getApi_code()));
			} else {
				jo.put("api_name", log.getApi_name());
			}
			jo.put("access_user", log.getAccess_user());
			jo.put("failCount", log.getFailCount());
			jo.put("successCount", log.getSuccessCount());
			array.add(jo);
		}

		result.put("rows", array);
		result.put("total", resultList.size());
		return result;
	}

	private static class InnerLog {
		private String accessid;
		private String access_ip;
		private String api_code;
		private String api_name;
		private String access_user;
		private String failCount;
		private String successCount;

		public String getAccessid() {
			return accessid;
		}

		public void setAccessid(String accessid) {
			this.accessid = accessid;
		}

		public String getApi_name() {
			return api_name;
		}

		public void setApi_name(String api_name) {
			this.api_name = api_name;
		}

		public String getAccess_ip() {
			return access_ip;
		}

		public void setAccess_ip(String access_ip) {
			this.access_ip = access_ip;
		}

		public String getAccess_user() {
			return access_user;
		}

		public void setAccess_user(String access_user) {
			this.access_user = access_user;
		}

		public String getApi_code() {
			return api_code;
		}

		public void setApi_code(String api_code) {
			this.api_code = api_code;
		}

		public String getFailCount() {
			return failCount;
		}

		public void setFailCount(String failCount) {
			this.failCount = failCount;
		}

		public String getSuccessCount() {
			return successCount;
		}

		public void setSuccessCount(String successCount) {
			this.successCount = successCount;
		}

	}
}
