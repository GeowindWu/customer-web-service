package com.gxecard.customerservice.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gxecard.customerservice.service.MessageService;
import com.gxecard.customerservice.util.ExportFileUtil;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

/**
 * 辅助对账
 * 
 * @author 吴洪全
 *
 */
@RestController
@Slf4j
public class AssistAcountController extends BaseController {
	@Autowired
	public AssistAcountController(MessageService messageService) {
		super(messageService);
	}

	@Value("${ftp.filePath.server}")
	private String localFilePath;

	@RequestMapping(value = "/queryAssistCount.do", method = RequestMethod.POST)
	public JSONObject queryAssistAcount(String startDate, String endDate, int page, int rows) {
		JSONObject resutl = new JSONObject();
		JSONObject accessInfo = messageService.getAccessInfo();
		int status = accessInfo.getInt("status");
		if (status == 1) {
			String parm = "";
			if (startDate != null && startDate.length() > 0) {
				parm = " AND t.access_begin >='" + startDate + "'";
			}
			if (endDate != null && endDate.length() > 0) {
				parm = parm + " AND t.access_begin<='" + endDate + "'";
			}
			String sql = "SELECT t.accessid,t.accessUser,t.api_name,t.api_code,t.access_ip, DATE_FORMAT(t.access_begin,'%Y-%m-%d %H:%i:%s') as access_begin,t.cardNo,t.terminalNo,t.outletCode,"
					+ "t.rechargeAmount,t.writeCardResult,t.rechargeManner FROM https_access_log.access_assist t WHERE 1=1";
			sql = sql + parm;
			String sqlCount = "select Count(*) as count FROM https_access_log.access_assist t WHERE 1=1";
			sqlCount = sqlCount + parm;
			log.info("assist account pagination query sql ：" + sql);
			log.info("sqlCount:" + sqlCount);
			resutl = paginationQuery(rows, page, sql, sqlCount);
		}

		return resutl;
	}

	@RequestMapping(value = "/exportAssist.do", method = RequestMethod.POST)
	public void exportAssist(String startDate, String endDate, HttpServletResponse response) {
		JSONObject resutl = new JSONObject();
		JSONObject accessInfo = messageService.getAccessInfo();
		int status = accessInfo.getInt("status");
		if (status == 1) {
			String parm = "";
			if (startDate != null && startDate.length() > 0) {
				parm = " AND t.access_begin >='" + startDate + "'";
			}
			if (endDate != null && endDate.length() > 0) {
				parm = parm + " AND t.access_begin<='" + endDate + "'";
			}
			String sql = "SELECT t.accessid,t.accessUser,t.api_name,t.api_code,t.access_ip, DATE_FORMAT(t.access_begin,'%Y-%m-%d %H:%i:%s') as access_begin,t.cardNo,t.terminalNo,t.outletCode,"
					+ "t.rechargeAmount,t.writeCardResult,t.rechargeManner FROM https_access_log.access_assist t WHERE 1=1";
			sql = sql + parm;
			log.info("assist account pagination query sql: " + sql);
			List list = messageService.query(sql);

			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("accessid", "id");
			map.put("accessUser", "访问用户");
			map.put("api_name", "接口名称");
			map.put("api_code", "接口码");
			map.put("access_ip", "IP");
			map.put("access_begin", "时间");
			map.put("cardNo", "卡号");
			map.put("terminalNo", "终端编号");
			map.put("outletCode", "网点编码");
			map.put("rechargeAmount", "充值金额");
			map.put("writeCardResult", "写卡标识(1：成功；0：失败)");
			map.put("rechargeManner", "充值方式(0：普通充值 1：转值)");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName = dateFormat.format(new java.util.Date()) + ".csv";
			try {
				ExportFileUtil.createCSVFile(list, map, localFilePath, fileName);
				ExportFileUtil.exportFile(response, localFilePath, fileName);
				// result.put("serverStatus", "true");
				// result.put("MSG", "成功");
			} catch (IOException e) {
				e.printStackTrace();
				log.error("IOExcetion in method exportAssist() " + e.toString());
			}
		}
	}
}
