package com.gxecard.customerservice.Scheduled.task;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gxecard.customerservice.service.AccessControll;
import com.gxecard.customerservice.service.MessageService;
import com.gxecard.customerservice.util.ExportFileUtil;
import com.gxecard.customerservice.util.FTPClientUtil;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

@Service
@Slf4j
public class AssistAcountTask implements Runnable {

	public MessageService messageService;
	private AccessControll accessControll;
	
	private String serverPath;
	private String remotePath;
	private String url;
	private String port;
	private String userName;
	private String password;
	
	@Autowired
	public AssistAcountTask(MessageService messageService,AccessControll accessControll,@Value("${ftp.filePath.server}")String serverpath,
			@Value("${ftp.filePath.remote}")String remotePath,	@Value("${ftp.url}") String url,@Value("${ftp.port}") String port,
			@Value("${ftp.userName}") String userName,@Value("${ftp.password}") String password ){
		super();
		this.messageService = messageService;
		this.accessControll = accessControll;
		this.serverPath = serverpath;
		this.remotePath = remotePath;
		this.url = url;
		this.port = port;
		this.userName = userName;
		this.password = password;
	}

	@Override
	public void run() {
		log.info("thread start up，system is creating assist account file");
		// 更新权限控制
		 accessControll.initList(true);
		queryDataAndExoport();
		log.info("timing task is completed");
	}

	private void queryDataAndExoport() {
		JSONObject resutl = new JSONObject();
		JSONObject accessInfo = messageService.getAccessInfo();
		int status = accessInfo.getInt("status");
		if (status == 1) {
			// 获取昨天
			// 当前时间
			Calendar calendar = Calendar.getInstance();
			// 日历翻回昨天
			calendar.add(calendar.DATE, -1);// 把日期往前减少一天，若想把日期向后推一天则将负数改为正数
			// 获取昨天的开始时间和结束时间
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			// 得到开始时间
			Date yesterday_start = calendar.getTime();
		
			// 再修改
			calendar.set(Calendar.HOUR, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			// 得到昨天的结束时间
			Date yesterday_end = calendar.getTime();
		

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String start = sdf.format(yesterday_start);
			String end = sdf.format(yesterday_end);
			log.info("query conditions - beginTime: " + start);
			log.info("query conditions - endTine:" + end);
			String sql = "SELECT t.accessid,t.accessUser,t.api_name,t.api_code,t.access_ip, DATE_FORMAT(t.access_begin,'%Y-%m-%d %H:%i:%s') as access_begin,t.cardNo,t.terminalNo,t.outletCode,"
					+ "t.rechargeAmount,t.writeCardResult,t.rechargeManner FROM https_access_log.access_assist t WHERE 1=1";
			String parm = "";
			parm = " AND t.access_begin >='" + start + "'";
			parm = parm + " AND t.access_begin<='" + end + "'";
			sql = sql + parm;

			List<Map<String,Object>> query = messageService.query(sql.toString());
			log.info("query sql：" + sql);
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
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
			String fileName = dateFormat.format(new java.util.Date()) + ".csv";
			//生成文件
			ExportFileUtil.createCSVFile(query, map, serverPath, fileName);
			InputStream input;
			try {
				input = new FileInputStream(serverPath  + fileName);
				log.info("temp file directory ：" + serverPath + fileName);
				int portInt = Integer.valueOf(port);
			FTPClientUtil.uploadFile(url, portInt, userName, password, remotePath, fileName, input);
			}catch (Exception e) {
				log.error("time task to create file create exception ");
				log.error(e.toString());
			}
		}
		

	}

}
