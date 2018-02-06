package com.gxecard.customerservice.service;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.gxecard.customerservice.dao.AccessLogDao;
import com.gxecard.customerservice.dao.ChargeDao;
import com.gxecard.customerservice.entity.AccessLog;
import com.gxecard.customerservice.entity.Charge;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.primitives.Bytes;
import com.gxecard.customerservice.client.CustomerServiceClientHandler;
import com.gxecard.customerservice.client.RespMessageDecoder;
import com.gxecard.customerservice.entity.BaseReqMessage;
import com.gxecard.customerservice.entity.BaseRespMessage;
import com.gxecard.customerservice.service.preparedstatement.CommonPreparedStatementCreator;
import com.gxecard.customerservice.service.preparedstatement.InsertPreparedStatementCreator;
import com.gxecard.customerservice.util.ReqMessageUtils;
import com.gxecard.customerservice.util.Utilitys;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

/**
 * 关于数据库操作方法的说明，之前的代码是用 jdbcTemplate，手动写sql语句的办法，很麻烦
 * 我后面的方法用jpa，面向对象式的操作数据库，便于维护和修改。但之前的我也懒得改了，一改用改很多，而且得反复验证正确性
 * 目前没有这么多时间，就先这样混合着。新增的方法就用jpa
 */

@Service
@Slf4j
public class MessageService {

	@Autowired
	private ChargeDao chargeDao;
	@Autowired
    private AccessLogDao accessLogDao;
	private final String customerServiceHost;
	private final int customerServicePort;

	private final JdbcTemplate jdbcTemplate;

	private ObjectMapper mapper = new ObjectMapper();
	// mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
	// false);

	/**
	 * 保存一条交易扣费记录
	 * @param charge
	 * @return
	 */
	public Charge saveCharge(Charge charge){
		return chargeDao.save(charge);
	}

	/**
	 * 通过id获取扣费记录
	 * @param id
	 * @return
	 */
	public Charge findChargeById(Integer id){
		return chargeDao.findOne(id);
	}

	/**
	 * 通过outTradeNo查找
	 * @param outTradeNo
	 * @return
	 */
	public Charge findChargeByOutTradeNoEquals(String outTradeNo){
		return chargeDao.findChargeByOutTradeNoEquals(outTradeNo);
	}
	/**
	 * 更新状态
	 * @param outTradeNo 根据此值查找
	 * @param status 更新值
	 * @return 更新后对象
	 */
	public Charge updateChargeStatusByOutTradeNoEquals(String outTradeNo,int status){
		Charge target = chargeDao.findChargeByOutTradeNoEquals(outTradeNo);
		target.setStatus(status);
		return  chargeDao.save(target);
	}
	/****************************************************************/
	public String queryLogList(String logid) { // 数据查询
		String sql = "select * from access_log t where t.accessid = ? ";
		MyRowCallbackHandler mhandler = new MyRowCallbackHandler();
		jdbcTemplate.query(sql, new Object[] { logid }, mhandler);
		return mhandler.getResult();
	}

	
	/**
	 * 数据查询，返回List
	 * 
	 * @param sql
	 * @return
	 */
	public List query(String sql) { // 数据查询
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
		return result;
	}
	public List query(String sql,Object... args) { // 数据查询
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql,args);
		return result;
	}
	public Map<String, Object> queryMap(String sql) { // 数据查询
		Map<String, Object> result = jdbcTemplate.queryForMap(sql);
		return result;
	}
	public Map<String, Object> queryMap(String sql,Object...args) { // 数据查询
		Map<String, Object> result = jdbcTemplate.queryForMap(sql,args);
		return result;
	}
	public int update(String sql, Object... args) {
		int update = -1;
		try {
			 update = jdbcTemplate.update(sql, args);
		} catch (Exception e) {
			log.error("update Exception :"  + sql +", args : "+ args + " exception is :" + e.toString());
		}
		
		return update;
	}

	public int update(String sql)  {
		int update = -1;
		try {
			 update = jdbcTemplate.update(sql);
		} catch (Exception e) {
			log.error( "update Exception ,sql :" + sql +", exception is :"+  e.toString());
		}
		return update;
	}

	// 定义一个结果处理函数
	private class MyRowCallbackHandler implements RowCallbackHandler {
		private String result = "没有相关记录";

		public void processRow(ResultSet resultSet) throws SQLException {
			String apiCode = resultSet.getString("api_code");
			result += "~~{" + apiCode + "},\r\n{";
			String accessBegin = resultSet.getString("access_begin");
			result += accessBegin + "},\r\n{";

			resultSet.getString("server_charset");
			byte[] server_input = resultSet.getBytes("server_input");
			String tmp = "";
			for (int i = 0; server_input != null && i < server_input.length; ++i) {
				tmp += server_input[i] + ",";
			}
			result += tmp + "},\r\n{";

			byte[] server_return = resultSet.getBytes("server_return");
			tmp = "";
			for (int i = 0; server_return != null && i < server_return.length; ++i) {
				tmp += server_return[i] + ",";
			}
			result += tmp + "}";
		}

		public String getResult() {
			return this.result;
		}
	}

	/***************************************************************/
	// 数据库操作
	/***************************************************************/
	/**
	 * 插入数据，如果大于0 表示成功，且为ID
	 * <p>
	 * 如果小于0 则失败
	 *
	 * @param access_ip
	 * @param access_params
	 * @return 大于0 表示成功，小于0表示失败。
	 */
	public int insertDbLog(String accessUser, String apiCode, String apiName,String access_ip, String access_params) {
		if (Utilitys.isBlank(accessUser))
			accessUser = "";

		if (Utilitys.isBlank(apiCode))
			apiCode = "";

		// 第一步：生成访问Id
		String access_uuid;// 年月日时分秒+UUID
		String uuidStr = UUID.randomUUID().toString();
		access_uuid = Utilitys.getCurTimeByFromt("yyyyMMddHHmmss") + "_" + uuidStr;

		// 第二步：插入数据
		String sqlString = "INSERT INTO https_access_log.access_log (api_code,api_name,access_uuid, access_ip, access_begin, access_params ,access_status,access_user ,access_remark) VALUES (? ,?, ?, ?, NOW(),?,0,?,?)";
		InsertPreparedStatementCreator insertPre = new InsertPreparedStatementCreator(sqlString, apiCode, apiName,access_uuid,
				access_ip, access_params, accessUser, "");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int rc = jdbcTemplate.update(insertPre, keyHolder);

		// System.out.println(keyHolder.getKey().intValue());
		if (rc > 0)
			return keyHolder.getKey().intValue();	
		else
			return -100;
	}

    /**
     * 更新中转服务器访问实际服务器的输入输出，这个给接口是http的用
     * @param accessId
     * @param success_trage
     * @param outputs 输出
     * @param inputs 输入
     * @return
     */
	public AccessLog updateServerParams(int accessId, boolean success_trage, String outputs, String inputs){
        AccessLog target = accessLogDao.findOne(accessId);
        if(success_trage){
            target.setSuccessTrage("1");
        }else {
            target.setSuccessTrage("0");
        }
        target.setAccessStatus(1);
        target.setAccessResult(outputs.getBytes());
        target.setServerInput(inputs.getBytes());

        return  accessLogDao.save(target);
	}
	/**
	 * 更新中转服务器访问实际服务器的输入输出
	 *
	 * @return
	 */
	public int updateServerParams(int accessId, boolean success_trage, String access_result, String response_code,
			String error_desc, byte[] inputs, byte[] returnHead, byte[] returnBody) {
		String charSet = "GBK";

		if (access_result == null)
			access_result = "";
		if (response_code == null)
			response_code = "";
		if (error_desc == null)
			error_desc = "";
		if (accessId <= 0)
			return accessId;
		if (inputs == null)
			inputs = new byte[0];
		if (returnHead == null)
			returnHead = new byte[0];
		if (returnBody == null)
			returnBody = new byte[0];
		if (error_desc.length() > 200)// 错误描述不要求太长
			error_desc = error_desc.substring(0, 199);

		byte[] returns = Bytes.concat(returnHead, returnBody);
		String sqlString = "UPDATE https_access_log.access_log set access_status='1',access_end = NOW(),";
		sqlString += " success_trage=?,access_result=?,response_code=?,error_desc=?,";// 成功状态，访问结果，响应码
		sqlString += " server_input=?,server_return=?,server_charset=?";
		sqlString += " WHERE accessid =?";
		List<Map<String, Object>> params = new ArrayList<Map<String, Object>>();
		String successTrage = "0"; // 是否成功标志
		if (success_trage)
			successTrage = "1";
		// 更新数据库日志
		params.add(CommonPreparedStatementCreator.getProduceMap(1, successTrage));
		params.add(CommonPreparedStatementCreator.getProduceMap(1, access_result.trim()));
		params.add(CommonPreparedStatementCreator.getProduceMap(1, response_code.trim()));
		params.add(CommonPreparedStatementCreator.getProduceMap(1, error_desc.trim()));

		params.add(CommonPreparedStatementCreator.getProduceMap(10, inputs));
		params.add(CommonPreparedStatementCreator.getProduceMap(10, returns));
		params.add(CommonPreparedStatementCreator.getProduceMap(1, charSet));
		params.add(CommonPreparedStatementCreator.getProduceMap(1, accessId));

		CommonPreparedStatementCreator comps = new CommonPreparedStatementCreator(sqlString, params);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		return jdbcTemplate.update(comps, keyHolder);
	}
	
	/***************************************************************/
	@Autowired
	public MessageService(@Value("${customer-service.host}") String customerServiceHost,
			@Value("${customer-service.port}") int customerServicePort, JdbcTemplate jdbcTemplate) {
		this.customerServiceHost = customerServiceHost;
		this.customerServicePort = customerServicePort;
		this.jdbcTemplate = jdbcTemplate;
	}

	public void transmit(int accessid, String parameter,String serviceHost,int servicePort ,DeferredResult<BaseRespMessage> deferredResult)
			throws Exception {
		checkNotNull(parameter);
		checkNotNull(deferredResult);

		String messageType = getMessageType(parameter);
		checkNotNull(messageType, "messageType字段不存在或者内容为空");

		Class<? extends BaseReqMessage> reqMessageClass = ReqMessageUtils.getReqMessageClass(messageType);
		checkNotNull(reqMessageClass, "非法请求报文类型");

		BaseReqMessage reqMessage = mapper.readValue(parameter, reqMessageClass);
		//转发到铭洪的socket接口，
		transmit(accessid, this, serviceHost, servicePort, reqMessage, deferredResult);
	}

	/**
	 * 从json中获取MessageType字段
	 *
	 * @param parameter
	 * @return
	 * @throws IOException
	 */
	public String getMessageType(String parameter) throws IOException {
		Map<String, String> params = mapper.readValue(parameter, new TypeReference<Map<String, String>>() {
		});
		return params.get("messageType");
	}

	// 郑川：2017-06-26新增。 将String 转换成Map 对象
	public Map<String, String> jsonString2Map(String strInput) throws IOException {
		if (strInput == null)
			throw new RuntimeException("非法輸入:输入参数不能为空！");
		Map<String, String> params = mapper.readValue(strInput, new TypeReference<Map<String, String>>() {
		});
		if (params == null)
			throw new RuntimeException("非法的输入:" + strInput);
		return params;
	}

	/*******************************************************************************************/
	private void transmit(final int accessid, final MessageService messageService, String host, int port,
			BaseReqMessage reqMessage, DeferredResult<BaseRespMessage> deferredResult) throws Exception {
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.AUTO_READ, true);
			// 创建接收对象 和 客服端处理对象
			RespMessageDecoder baseDecoder = new RespMessageDecoder();
			CustomerServiceClientHandler customerClient = new CustomerServiceClientHandler(accessid, messageService,
					reqMessage, deferredResult, baseDecoder);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new ByteArrayEncoder(), baseDecoder, customerClient);
				}
			});
			ChannelFuture f = b.connect(host, port).sync();
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}

	/**
	 * getAccessInfo专用
	 * @param sql
	 * @return
	 */
	private List<Map<String,String>> queryBlob(String sql){
		 final LobHandler lobHandler=new DefaultLobHandler();    
	     @SuppressWarnings("unchecked")  
	     List<Map<String,String>> list = jdbcTemplate.query(sql,new RowMapper(){  
	         public Object mapRow(ResultSet rs, int rowNum) throws SQLException   
	         {     Map<String,String> map = new HashMap<String,String>();  
	             //以二进制的数组方式获得Blob数据,第二个参数3是指blob字段在结果集的位置  
	             byte[] params = lobHandler.getBlobAsBytes(rs, 7);//access_params  
	             byte[] resultParams = lobHandler.getBlobAsBytes(rs, 8);//access_result
	             String access_result = new String(resultParams);
	             String access_params = new String(params);  
	             //非blo
	         	String accessId = rs.getString("accessid").toString();// ID
				String apiName = rs.getString("api_name").toString();// 接口名
				String apiCode = rs.getString("api_code").toString();// 接口代码
				String accessIp = rs.getString("access_ip").toString();// 请求IP
				String accessBegin = rs.getString("access_begin").toString();// 请求时间
				String accessUser = rs.getString("access_user").toString();// 访问用户
				
	             map.put("access_params", access_params);  
	             map.put("access_result", access_result);
	             map.put("accessid",accessId );
	             map.put("api_name", apiName);
	             map.put("api_code", apiCode);
	             map.put("access_ip", accessIp);
	             map.put("access_begin", accessBegin);
	             map.put("access_user", accessUser);
	             return map;  
	         }  
	     });  
		return list;
	}
	/**
	 * 查询辅助对账前，对可能为解析的数据进行解析，然后存到数据库
	 * @return
	 */
	public JSONObject getAccessInfo() {
		JSONObject jsonParams = new JSONObject();
		JSONObject reJson = new JSONObject();
		try {
			// 查询辅助对账中的数据
			String sqlAssist = "SELECT accessid FROM https_access_log.access_assist";
			List<Map<String, Object>> assistList = query(sqlAssist);
			// 获取对账表中所有记录的id
//			List assistIdList = new ArrayList<String>(1024 * 10);// 数据以后挺大的，给个大空间，最好动态的才好
			//hashmap效率高
			Map<String,String> assistIdMap = new HashMap<String, String>(assistList.size()/100);
			for (Map<String, Object> item : assistList) {
				String id =  item.get("accessid").toString();
//				assistIdList.add(id + "");
				assistIdMap.put(id, id);
			}

			// 查询access_log表，解析日志
			String sql = "SELECT t.accessid ,t.access_user,t.api_name,t.api_code,t.access_ip,t.access_begin ,t.access_params,t.access_result,t.response_code "
					+ "FROM https_access_log.access_log t WHERE (t.api_code='1003' OR t.api_code='5002') AND t.success_trage='1' AND t.access_status='1' AND (analysis = '0' OR analysis is NULL)";
			List<Map<String, String>> accessLogList = queryBlob(sql);
			// 解析日志
			int insertCount = 0;
			for (Map<String, String> map : accessLogList) {
				String accessId = map.get("accessid").toString();// ID
				String apiCode = map.get("api_code").toString();// 接口代码
				String apiName = map.get("api_name").toString();// 接口名
				if(apiName == null || apiName.contentEquals("")){
					apiName = AccessControll.getApiNameByMessageType(apiCode);
				}
				String accessIp = map.get("access_ip").toString();// 请求IP
				String accessBegin = map.get("access_begin").toString();// 请求时间
				String accessUser = map.get("access_user").toString();// 访问用户
				// blob	
				String params =  map.get("access_params");// 请求参数
				String accessResult = map.get("access_result");
//				log.info("access_result:" + accessResult);
				// 解析blob成字节数组
//				byte[] bytesParams;

//				bytesParams = blob2byte(accessParams);

//				String params = new String(bytesParams);
				if (StringUtils.isEmpty(params)) {
					// 如果空，拉到
					continue;
				}

				jsonParams = JSONObject.fromObject(params);

				// 获取辅助对账表参数
				String cardNo = getJsonValue(jsonParams, "cardNo"); // jsonParams.getString("cardNo");//
																	// 卡号
				String terminalNo = getJsonValue(jsonParams, "terminalNo"); // jsonParams.getString("terminalNo");//
																			// 终端编号
				String serviceSerial = "";
				String outletCode = getJsonValue(jsonParams, "outletCode"); // jsonParams.getString("outletCode");//
																			// 网点编号
				String rechargeAmount = getJsonValue(jsonParams, "rechargeAmount");// jsonParams.getString("rechargeAmount");//
																					// 充值金额
				Integer writeCardResult = 0;
				Integer rechargeManner = 0;
				if (apiCode.equals("1003")) {
					String res = getJsonValue(jsonParams, "writeCardResult"); // jsonParams.getString("writeCardResult");//
																				// 写卡结果
					if (res != null && !StringUtils.isEmpty(res))
						writeCardResult = Integer.valueOf(res);
				} else {
					String rech = getJsonValue(jsonParams, "rechargeManner"); // jsonParams.getString("rechargeManner");
					if (rech != null && !StringUtils.isEmpty(rech))
						rechargeManner = Integer.valueOf(rech);
				}

				Timestamp accessbegin = Timestamp.valueOf(accessBegin);// 时间不对

				long accessid = Long.valueOf(accessId);
				// 更新状态
				String updateSql = "update https_access_log.access_log set analysis	= 1 where accessid = ?";
				update(updateSql, accessid);
				// 解析完毕，插入到辅助对账表
				if (!(assistIdMap.containsKey(accessId))) {
					insertCount ++;
					String insertSql = "INSERT INTO  https_access_log.access_assist (accessid,accessUser,api_name,"
							+ "api_code,access_ip,access_begin,cardNo,terminalNo,serviceSerial,outletCode,"
							+ "rechargeAmount,writeCardResult,rechargeManner) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
					int insert = update(insertSql,accessId,accessUser,apiName,apiCode,accessIp,accessbegin,cardNo
							,terminalNo,serviceSerial,outletCode,rechargeAmount,writeCardResult,rechargeManner);
					// outletCode terminalNo serviceSerial outletCode outletCode
//					log.info("插入对账表结果：" +insert); 
				}
			}
			log.info("Unresolved logs count :" + accessLogList.size() +",insert times(contain fail) :" + insertCount);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("create JsonObject Exception：" + e.toString());
			log.error("get the json data Excetpion：" + jsonParams);
			reJson.put("status", 0);
		}
		reJson.put("status",1);
	
		return reJson;

	}

	// blob转字节数组
//	private byte[] blob2byte(Object obj) throws IOException {
//		byte[] bytes;
//		BufferedInputStream bis = null;
//		try {
////			SerializableBlob blob = (SerializableBlob) obj;
////			SerialBlob blob = (SerialBlob) obj;
////			new ObjectInputStream
//			
//			bis = new BufferedInputStream(blob.getBinaryStream());
//			bytes = new byte[(int) blob.length()];
//			int len = bytes.length;
//			int offest = 0;
//			int read = 0;
//			while (offest < len && (read = bis.read(bytes, offest, len - offest)) > 0) {
//				offest += read;
//			}
//		} catch (Exception e) {
//			bytes = null;
//			e.printStackTrace();
//		} finally {
//			if (bis != null) {
//				bis.close();
//				bis = null;
//			}
//		}
//		return bytes;
//	}
 
	private String getJsonValue(JSONObject json, String key) {
		String value = null;
		if (json.has(key)) {
			value = json.getString(key);
		}
		value = null == value ? "" : value;
		return value;
	}
}
