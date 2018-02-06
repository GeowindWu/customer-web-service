package com.gxecard.customerservice.service;

import com.gxecard.customerservice.util.Utilitys;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class AccessControll {
	private final JdbcTemplate jdbcTemplate;
	private static List<AccessContUnit> accessControlls = new ArrayList<AccessContUnit>();
	private static List<ApiInfo> apiControl = new ArrayList<ApiInfo>();// 存数据库的接口信息

	@Autowired
	public AccessControll(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 返回系统管理的接口信息对列对象
	 * 
	 * @return
	 */
	public static List<ApiInfo> getApiControl() {
		return apiControl;
	}

	/**
	 * 获取所有的配置的接口的接口码的队列对象
	 * 
	 * @return
	 */
	public static List<String> getAllApiCode() {
		List<String> apiCodes = new ArrayList<String>();
		for (ApiInfo info : apiControl) {
			apiCodes.add(info.getApiCode());
		}
		return apiCodes;
	}

	public static String getApiCodeByName(String apiName) {
		String apiCode = "";
		for (ApiInfo info : apiControl) {
			if (info.getApiName().equals(apiName)) {
				apiCode = info.getApiCode();
			}
		}
		return apiCode;
	}

	public static String getApiNameByMessageType(String messageType) {
		String apiName = "";
		for (ApiInfo info : apiControl) {
			if (info.getApiCode().equalsIgnoreCase(messageType)) {
				apiName = info.getApiName();
				break;
			}
		}
		return apiName;
	}

	public ApiInfo getApiInfoByMessageType(String messageType) {
		ApiInfo target = null;
		for (ApiInfo info : apiControl) {
			if (info.getApiCode().equalsIgnoreCase(messageType)) {
				target = info;
				break;
			}
		}
		return target;
	}

	/**
	 * 校验是否有权限访问，用户必须匹配
	 * 
	 * @param accessUser
	 * @param accessIp
	 * @return
	 */
	public JSONObject checkAccessRule(String accessUser, String accessIp) {
		JSONObject checkResult = new JSONObject();
		initList(false);

		String msg = "";
		if (Utilitys.isBlank(accessUser) || Utilitys.isBlank(accessIp)) {
			msg = "User parameter is empty or IP address is empty";// 用户参数为空或者IP地址为空";
		} else {
			try {
				msg = "This user is not configured on the system";// 在系统中未配置有该用户
				for (AccessContUnit tmp : accessControlls) { // 如果不为空
					if (tmp != null && !Utilitys.isBlank(tmp.getAccessUser())) {//

						if (tmp.getAccessUser().equalsIgnoreCase(accessUser.trim())) {// 如果用户能够匹配
							msg = "This user does not have permission to access the system from the current IP";// 该用户无从当前IP访问系统的权限
							if (isIPMatch(accessIp, tmp)) {
								// 如果IP地址匹配
								msg = "This user has been disabled";// 该用户已被禁用
								if (tmp.getStatus() == 1) { // 如果状态为启用
									// 检查用户的有效时间
									msg = "This user is not valid";// 此用户不在有效期内
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									Date start = sdf.parse(tmp.getBeginTime());
									Date end = sdf.parse(tmp.getEndTime());
									Date current = new Date();
									if (current.after(start) && current.before(end)) {
										// 如果在有效期内
										checkResult.put("result", "true");
										checkResult.put("msg", "access pass");
										return checkResult;
									}
								}
							}
						}

					}
				}

			} catch (Exception e) {
				log.error(e.toString());
				msg = e.toString();
			}

		}
		checkResult.put("result", "false");
		checkResult.put("msg", msg);
		return checkResult;
	}

	/**
	 * 判断访问者的IP是否匹配后台配置的IP
	 * 
	 * @param accessIP
	 * @param unit
	 * @return
	 */
	private boolean isIPMatch(String accessIP, AccessContUnit unit) {
		String ips = unit.getAccessIp();
		if (ips.contentEquals("*")) {
			return true;
		}
		String[] split = ips.split(",");
		for (String configIp : split) {
			if (accessIP.contentEquals(configIp)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据messageType，检查系统是否存在该接口
	 * 
	 * @param messageType
	 *            需要访问的接口的编码
	 * @return 存在true，否则false
	 */
	public boolean existApiByMessageType(String messageType) {
		for (ApiInfo info : apiControl) {
			if (info.getApiCode().equalsIgnoreCase(messageType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取一个用户信息
	 * 
	 * @param accessUser
	 * @param
	 * @return
	 */
	public AccessContUnit loadAccessCont(String accessUser) {
		initList(false);
		if (Utilitys.isBlank(accessUser) || accessControlls == null)
			return null;
		accessUser = accessUser.trim();

		AccessContUnit controll = null;
		for (AccessContUnit tmp : accessControlls) { // 如果不为空
			if (tmp != null && Utilitys.isBlank(tmp.getAccessUser()) == false) {
				if (accessUser.equalsIgnoreCase(tmp.getAccessUser())) {
					controll = tmp;
					break;
				}
			}
		}
		return controll;
	}

	/**
	 * 初始化 访问控制细项
	 */
	public void initList(boolean reload) {
		if (reload) { // 如果强制重新刷新
			accessControlls = new ArrayList<AccessContUnit>();
			apiControl = new ArrayList<ApiInfo>();
		}
		if (accessControlls == null) {// 如果本来还未加载
			accessControlls = new ArrayList<AccessContUnit>();
		}
		if (apiControl == null) {
			apiControl = new ArrayList<ApiInfo>();
		}
		// 用户部分配置信息读取
		if (accessControlls.size() == 0) { // 如果没有数据，就认为是为加载数据
			// sql += " user_pub_key AS userPubkey , user_pri_key AS
			// userPriKey,user_key1 AS userKey1 ,user_key2 AS userKey2";
			String sql = "SELECT access_ip AS accessIp ,access_uuid AS accessUser ,effective_trage as status,DATE_FORMAT(begin_time,'%Y-%m-%d %H:%i:%s') as begin_time ,";
			sql += "DATE_FORMAT(end_time,'%Y-%m-%d %H:%i:%s') as end_time ,user_pub_key AS userPubkey , user_key1 AS userKey1 ,user_key2 AS userKey2";
			sql += " FROM access_control ";// WHERE effective_trage='1' AND
											// SYSDATE() > begin_time AND
											// SYSDATE() < end_time
			AccessControll.AccessContRowCallbackHandler mhandler = new AccessControll.AccessContRowCallbackHandler();
			// 执行完成后，数据就加载到accessControlls 里面了
			jdbcTemplate.query(sql, new Object[] {}, mhandler);
		}
		// 接口部分配置信息读取
		if (apiControl.size() == 0) {
			String sql = "select * from https_access_log.api";
			jdbcTemplate.query(sql, new Object[] {}, new ApiInfoRowCallbackHandler());
			log.info("api count is ：" + apiControl.size());
			for (ApiInfo info : apiControl) {
				log.info("api name is ：" + info.getApiName() + ",api code is ：" + info.getApiCode());
			}
		}
	}

	// 定义一个结果处理函数
	private class AccessContRowCallbackHandler implements RowCallbackHandler {

		public AccessContRowCallbackHandler() {// 保证不会重复
			accessControlls = new ArrayList<AccessContUnit>();
		}

		// 处理每一条记录
		public void processRow(ResultSet resultSet) throws SQLException {
			String accessUser = resultSet.getString("accessUser");
			String accessIp = resultSet.getString("accessIp");
			String userPubkey = resultSet.getString("userPubkey");
			String userKey1 = resultSet.getString("userKey1");
			String userKey2 = resultSet.getString("userKey2");
			int status = resultSet.getInt("status");
			String beginTime = resultSet.getString("begin_time");
			String endTime = resultSet.getString("end_time");
			if (Utilitys.isBlank(accessUser) == false) {
				AccessContUnit accessContUnit = new AccessContUnit();
				accessContUnit.setAccessUser(accessUser);
				accessContUnit.setAccessIp(accessIp);
				accessContUnit.setUserPubkey(userPubkey);
				accessContUnit.setUserKey1(userKey1);
				accessContUnit.setUserKey2(userKey2);
				accessContUnit.setStatus(status);
				accessContUnit.setBeginTime(beginTime);
				accessContUnit.setEndTime(endTime);
				accessControlls.add(accessContUnit);
			}
		}

	}

	/**
	 * 处理接口信息的
	 * 
	 * @author lenovoe
	 *
	 */
	private class ApiInfoRowCallbackHandler implements RowCallbackHandler {

		public ApiInfoRowCallbackHandler() {
			apiControl = new ArrayList<AccessControll.ApiInfo>();
		}

		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			String apiName = resultSet.getString("apiName");
			String apiCode = resultSet.getString("apiCode");
			String apiUrl = resultSet.getString("apiUrl");
			String apiIp = resultSet.getString("apiIp");
			String apiPort = resultSet.getString("apiPort");
			int apiType = resultSet.getInt("apiType");
			int apiStatus = resultSet.getInt("apiStatus");
			String keyStorePath = resultSet.getString("keyStorePath");
			String trustStorePath = resultSet.getString("trustStorePath");
			String keyStorePw = resultSet.getString("keyStorePw");
			String trustStorePw = resultSet.getString("trustStorePw");
			String keyPw = resultSet.getString("keyPw");

			ApiInfo apiInfo = new ApiInfo();
			apiInfo.setApiName(apiName);
			apiInfo.setApiCode(apiCode);
			apiInfo.setApiUrl(apiUrl);
			apiInfo.setApiIp(apiIp);
			apiInfo.setApiPort(apiPort);
			apiInfo.setApiType(apiType);
			apiInfo.setApiStatus(apiStatus);
			apiInfo.setKeyStorePath(keyStorePath);
			apiInfo.setTrustStorePath(trustStorePath);
			apiInfo.setKeyStorePw(keyStorePw);
			apiInfo.setTrustStorePw(trustStorePw);
			apiInfo.setKeyPw(keyPw);
			apiControl.add(apiInfo);
		}

	}

	// 访问控制单元
	public class AccessContUnit {

		private String accessUser;
		private String accessIp;

		// 用户的公私钥和密钥对
		private String userPubkey;
		private String userKey1, userKey2;

		// 用户状态
		private int status;
		private String beginTime;
		private String endTime;

		public String getAccessUser() {
			return accessUser;
		}

		public void setAccessUser(String accessUser) {
			this.accessUser = accessUser;
		}

		public String getAccessIp() {
			return accessIp;
		}

		public void setAccessIp(String accessIp) {
			this.accessIp = accessIp;
		}

		public String getUserPubkey() {
			return userPubkey;
		}

		public void setUserPubkey(String userPubkey) {
			this.userPubkey = userPubkey;
		}

		public String getUserKey1() {
			return userKey1;
		}

		public void setUserKey1(String userKey1) {
			this.userKey1 = userKey1;
		}

		public String getUserKey2() {
			return userKey2;
		}

		public void setUserKey2(String userKey2) {
			this.userKey2 = userKey2;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getBeginTime() {
			return beginTime;
		}

		public void setBeginTime(String beginTime) {
			this.beginTime = beginTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

	}

	/**
	 * 接口信息类
	 * 
	 * @author lenovoe
	 *
	 */
	public class ApiInfo {
		private String apiName;
		private String apiCode;// 接口码，也就是messageType
		private int apiType; // 接口类型，webservice:1或者socket:2
		private String apiUrl;// 接口的url，针对webservice接口
		private String apiIp;// 接口的IP，针对socket
		private String apiPort;// 接口的端口 ，针对socket
		private int apiStatus; // 接口状态，1启用，0禁用
		/**
		 * 密钥库文件路径
		 */
		private String keyStorePath;
		/**
		 * 信任库文件路径
		 */
		private String trustStorePath;
		/**
		 * 密钥库密码
		 */
		private String keyStorePw;
		/**
		 * 信任库密码
		 */
		private String trustStorePw;
		/**
		 * 私钥密码
		 */
		private String keyPw;

		public String getApiName() {
			return apiName;
		}

		public void setApiName(String apiName) {
			this.apiName = apiName;
		}

		public String getApiCode() {
			return apiCode;
		}

		public void setApiCode(String apiCode) {
			this.apiCode = apiCode;
		}

		public int getApiType() {
			return apiType;
		}

		public void setApiType(int apiType) {
			this.apiType = apiType;
		}

		public String getApiUrl() {
			return apiUrl;
		}

		public void setApiUrl(String apiUrl) {
			this.apiUrl = apiUrl;
		}

		public String getApiIp() {
			return apiIp;
		}

		public void setApiIp(String apiIp) {
			this.apiIp = apiIp;
		}

		public String getApiPort() {
			return apiPort;
		}

		public void setApiPort(String apiPort) {
			this.apiPort = apiPort;
		}

		public int getApiStatus() {
			return apiStatus;
		}

		public void setApiStatus(int apiStatus) {
			this.apiStatus = apiStatus;
		}

		public String getKeyStorePath() {
			return keyStorePath;
		}

		public void setKeyStorePath(String keyStorePath) {
			this.keyStorePath = keyStorePath;
		}

		public String getTrustStorePath() {
			return trustStorePath;
		}

		public void setTrustStorePath(String trustStorePath) {
			this.trustStorePath = trustStorePath;
		}

		public String getKeyStorePw() {
			return keyStorePw;
		}

		public void setKeyStorePw(String keyStorePw) {
			this.keyStorePw = keyStorePw;
		}

		public String getTrustStorePw() {
			return trustStorePw;
		}

		public void setTrustStorePw(String trustStorePw) {
			this.trustStorePw = trustStorePw;
		}

		public String getKeyPw() {
			return keyPw;
		}

		public void setKeyPw(String keyPw) {
			this.keyPw = keyPw;
		}

	}
}
