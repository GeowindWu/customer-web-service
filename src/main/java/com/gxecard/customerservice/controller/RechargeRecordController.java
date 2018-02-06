package com.gxecard.customerservice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gxecard.customerservice.service.MessageService;
import com.gxecard.customerservice.util.HtppsConfig;
import com.gxecard.customerservice.util.HttpsRequest;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
@Slf4j
public class RechargeRecordController extends BaseController {
	@Value("${frontserver.query.url}")
	private String accessUrl ;
	@Value("${frontserver.truststore}")
	private String trustStorePath;
	@Value("${frontserver.keystore}")
	private String keystorePath;
	
	@Value("${frontserver.ksPw}")
	private String ksPw;
	@Value("${frontserver.tsPw}")
	private String tsPw;
	private  HtppsConfig defaultHttpConfig;
	@Autowired
	public RechargeRecordController(MessageService messageService , HtppsConfig defaultHttpConfig) {
		super(messageService);
		this.defaultHttpConfig = defaultHttpConfig;
	}
	
	
	@RequestMapping(value="/queryRechargeRecord.do",method=RequestMethod.POST)
	public JSONObject queryRechargeRecord(int page,int rows,String cardNo){
		JSONObject resultJson = new JSONObject();
		cardNo = cardNo == null ? "" :cardNo.replaceAll("\\s*", ""); //去掉空格，收尾，中间
		Map params = new HashMap<String, String>();
		params.put("cardNo", cardNo);
		try {
			defaultHttpConfig.setTrustStorePath(trustStorePath);
			defaultHttpConfig.setTrustStorePw(tsPw);
			defaultHttpConfig.setKeyStorePath(keystorePath);
			defaultHttpConfig.setKeyStorePw(ksPw);
			String result = HttpsRequest
					.httpsAccess(accessUrl, params, "utf-8",defaultHttpConfig);
			JSONObject joResult = JSONObject.fromObject(result);
			// String pageNo = joResult.getString("pageNo");
			// String pageSize = joResult.getString("pageSize");
			String serverStatus = joResult.getString("serviceStatus");
			if ("1".equals(serverStatus)) {
				// 查询正常，否则异常
				String totalRecord = joResult.getString("totalRecord");
				String totalMoney = joResult.getString("totalMoney");
				JSONArray jsonArray = joResult.getJSONArray("resultList");

				int fromIndex = (page - 1) * rows;
				int toIndex;
				int total = Integer.valueOf(totalRecord);
				if (page - 1 == total / rows) {
					// 最后一页
					toIndex = total;
				} else {
					toIndex = page * rows;
				}

				List<JSONObject> subList = jsonArray
						.subList(fromIndex, toIndex);
				List<InnerRecord> recordList = new ArrayList<InnerRecord>();
				for (JSONObject jo : subList) {
					InnerRecord record = new InnerRecord();
					record.setCardNo(jo.getString("cardNo"));
					record.setRefillId(jo.getString("refillId"));
					record.setRefillMoney(jo.getString("refillMoney"));
					record.setRefillStatus(jo.getString("refillStatus"));
					record.setRefillTime(jo.getString("refillTime"));
					record.setUserId(jo.getString("userId"));
					recordList.add(record);
				}
				resultJson.put("rows", recordList);
				resultJson.put("total", total);
			}else{
				// 查询异常
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("queryRechargeRecord exception " + e.toString());
		}
		return resultJson;
	}
	
	public static class InnerRecord {
		private String refillId;// 充值唯一标识
		private String refillTime;// 充值时间
		private String refillMoney;// 充值金额
		private String refillStatus;// 充值状态
		private String cardNo;// 卡号
		private String userId;// 客户ID

		public String getRefillId() {
			return refillId;
		}

		public void setRefillId(String refillId) {
			this.refillId = refillId;
		}

		public String getRefillTime() {
			return refillTime;
		}

		public void setRefillTime(String refillTime) {
			this.refillTime = refillTime;
		}

		public String getRefillMoney() {
			return refillMoney;
		}

		public void setRefillMoney(String refillMoney) {
			this.refillMoney = refillMoney;
		}

		public String getRefillStatus() {
			return refillStatus;
		}

		public void setRefillStatus(String refillStatus) {
			this.refillStatus = refillStatus;
		}

		public String getCardNo() {
			return cardNo;
		}

		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

	}
}
