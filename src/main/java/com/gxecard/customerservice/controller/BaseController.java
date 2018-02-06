package com.gxecard.customerservice.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.gxecard.customerservice.service.AccessControll;
import com.gxecard.customerservice.service.MessageService;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

/**
 * 
 * @author 吴洪全
 *
 */
@Slf4j
public class BaseController {
	public MessageService messageService;
	
	public BaseController(MessageService messageService){
		this.messageService = messageService;
	}
	/**
	 * 
	 * @param rows 页大小
	 * @param page 第几页
	 * @param sql 
	 * @param sqlCount 必须写成 select Count(*) as count from xxx where xxx格式
	 * @return json
	 */
	public JSONObject paginationQuery(int rows,int page,String sql,String sqlCount){
		int start =  rows * (page -1);
		sql +=" limit " + start + "," + rows;
		log.info("pagination query sql：" + sql);
		log.info("sqlCount:" + sqlCount);
		JSONObject result = new JSONObject();
		try {
			List<Map<String,Object>> query = messageService.query(sql.toString());
			for(Map<String,Object> item : query){
				if(item.containsKey("api_name") && item.get("api_name").equals("")){
					item.put("api_name", AccessControll.getApiNameByMessageType(item.get("api_code")+""));
				}
			}
			Map<String, Object> queryCount = messageService.queryMap(sqlCount.toString());
			long total = (Long) queryCount.get("count");
			
			result.put("rows", query);//数据
			result.put("total",total+"");//总条数
		} catch (Exception e) {
			log.error(e.toString());
			result.put("rows", "{}");//数据
			result.put("total","0");//总条数
		}
		

		return result;
	}
	/**
	 * 处理请求并且返回客服端
	 * 
	 * @param response
	 */
	public boolean writeToClient(HttpServletResponse response, String returnInfo) {
		PrintWriter out = null;
		boolean trage = false;
		try {
			if (returnInfo == null)
				returnInfo = "";

			// 将数据写入前台
			out = response.getWriter();

			out.write(returnInfo);
			trage = true;
		} catch (Exception e) {
			trage = false;
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
		return trage;
	}
}
