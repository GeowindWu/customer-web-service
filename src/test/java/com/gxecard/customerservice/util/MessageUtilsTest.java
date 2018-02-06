package com.gxecard.customerservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gxecard.customerservice.entity.BaseReqMessage;
import com.gxecard.customerservice.entity.req.*;


import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class MessageUtilsTest {

	private ObjectMapper mapper = new ObjectMapper();

	class T2 {
		private String returnCardReferenceNo;

		private String tmsg;

		public T2() {

		}

		public String getReturnCardReferenceNo() {
			return this.returnCardReferenceNo;
		}

		public void setReturnCardReferenceNo(String returnCardReferenceNo) {
			this.returnCardReferenceNo = returnCardReferenceNo;
		}

		public String getTmsg() {
			return this.tmsg;
		}

		public void setTmsg(String t2) {
			this.tmsg = t2;
		}
	}

	@Test

	public void test5096() throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		// JSONObject job =
		// JSONObject.fromObject("{\"terminalNo2terminalNo2terminalNo2\":\"222222\",
		// \t\"outletCode\": \"6310028831000001\", \t\"terminaTransNo\":
		// \"0000000000000151\", \t\"businessType\": \"76\",
		// \t\"returnCardType\": \"\", \t\"cardNo\": \"3104840897000001409\",
		// \t\"returnCardReferenceNo\": \"tt692242\", \t\"certType\": \"\",
		// \t\"cardIdNo\": \"\", \t\"operatorId\": \"xueqiu\", \t\"granterId\":
		// \"\", \t\"granterPassword\": \"\", \t\"proxyFlag\": \"1\",
		// \t\"grantedFlag\": \"0\", \t\"messageType\": \"5096\",
		// \t\"terminalNo\": \"6310413188000002\", \t\"accessUser\":
		// \"gxecard_snowball_1501809887321_aa55f7f3-11d4-4132-a0f7-a0fcf14b63b2\"
		// }");
		// ReceiveRemainMoneyReqMessage ob1 =(ReceiveRemainMoneyReqMessage)
		// JSONObject.toBean(job, ReceiveRemainMoneyReqMessage.class);
		ReceiveRemainMoneyReqMessage t2 = mapper.readValue(
				"{ \t\"outletCode\": \"6310028831000001\", \t\"terminaTransNo\": \"0000000000000151\", \t\"businessType\": \"76\", \t\"returnCardType\": \"\", \t\"cardNo\": \"3104840897000001409\", \t\"returnCardReferenceNo\": \"tt692242\", \t\"certType\": \"\", \t\"cardIdNo\": \"\", \t\"operatorId\": \"xueqiu\", \t\"granterId\": \"\", \t\"granterPassword\": \"\", \t\"proxyFlag\": \"1\", \t\"grantedFlag\": \"0\", \t\"messageType\": \"5096\", \t\"terminalNo\": \"6310413188000002\", \t\"accessUser\": \"gxecard_snowball_1501809887321_aa55f7f3-11d4-4132-a0f7-a0fcf14b63b2\" }",
				ReceiveRemainMoneyReqMessage.class);
		System.out.print(t2.getReturnCardReferenceNo());
	}

	@Test
	public void testcovert() throws Exception {
		RemainTransferReqMessage req = new RemainTransferReqMessage();
		req.setMessageType("5029");
		req.setTerminalNo("123456");

		// Map map= Utilitys.objectToMapByDepthOnce(req);
		// System.out.println(map.toString());

		// byte[] bytes = ReqMessageUtils.convertMessageToBytes(req );

		// System.out.println(Arrays.toString(bytes));
		// System.out.println("Size=" + bytes.length);
	}

	@Test
	public void convertMessageToBytes() throws Exception {
		ReturnCardConfirmReqMessage msg = new ReturnCardConfirmReqMessage();
		msg.setMessageType("0094");
		msg.setTerminalNo("123456");
		msg.setReferenceNo("654321");
		msg.setLockStatus("1");
		msg.setTerminalTransNo("111222");

		byte[] bytes = ReqMessageUtils.convertMessageToBytes(msg);

		System.out.println(Arrays.toString(bytes));
		System.out.println("Size=" + bytes.length);
	}

	@Test
	public void test1() throws Exception {
		Set<Map.Entry<String, Class<? extends BaseReqMessage>>> entrySet = ReqMessageUtils.typeMessageMap.entrySet();
		for (Map.Entry<String, Class<? extends BaseReqMessage>> entry : entrySet) {
			String messageType = entry.getKey();
			System.out.println(messageType);
			BaseReqMessage value = entry.getValue().newInstance();
			value.setMessageType(messageType);
			System.out.println(mapper.writeValueAsString(value));
		}

	}

//	@Test
//	public void testRsa() throws Exception {
//		RsaSignUtil util = new RsaSignUtil(
//				"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCswRlimulVi5IXlQ2DhLgMaIM2ShARoB11XIs8JE5joF4SN+q+acGU8q4n3tDUYQ3RSlZ9xZhcRvukMPydjNk5YYhuUFa34c1VUPGqUsmEh/Hjr/jT894papn3eOaNTmkAp88UIU4uQ6uElz3TBW8WPU1uNE6RCgQH0bbY39WzMQIDAQAB",
//				"MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKzBGWKa6VWLkheVDYOEuAxogzZKEBGgHXVcizwkTmOgXhI36r5pwZTyrife0NRhDdFKVn3FmFxG+6Qw/J2M2TlhiG5QVrfhzVVQ8apSyYSH8eOv+NPz3ilqmfd45o1OaQCnzxQhTi5Dq4SXPdMFbxY9TW40TpEKBAfRttjf1bMxAgMBAAECgYAhK9LcBDLZojsjHBPENS/B8i9/tSjwuXXumx6BJdeHebuWc/Y3Kcdp+k9GiJpArYIhtcx1ygM/dMHM/TsEZY8wC+pqNEX3CZ050oWgzggUWpfHBTC0HcZu7Jvz4EYbq0wfDvOR+CHwwqLf8u+2KtWtCQMyPVY2xgU5bepktQI5oQJBAPFm4CRrZDubbfm91C5mr0NNa364tExD45MpoMWGVqyl1yu2H0TN0XmNz9F5T+uWFgOpOD2CLSNeGYMcqfahKicCQQC3M37mbFE3cNEMrQPMpdYHClDelLmkyLkBCxm/YPJpnn8G3dxfwhgEd9CljP4/htEOLvefEaL13XBHCcYdTkbnAkBWlFAVn9KuMjvKU5QTJE79s3m3VGWN6NdpQ4fe8CSL/Vrj2Yjzc2IK15rOhVTtqMGyhHGgVdz8j1ZVGOW4h+sHAkBUsocZyrwlsI/Fl3upMoZnzNokfYfyaiY2GEa4Fv8b234I06udzeNCtY4N68hj4FVohEhRD1tS5iSRgzBHvjfDAkARhVrCnyWT1HKQHBjUfBFSeH7AT+7a3dlM2kkIX3aLlEzh3f1KXxcth2xc6MvZJS2brAEGWoQALdyMNBMau0bw",
//				"gkqhkiG9w0BAQEFAASCAl8wggJbAgE", "BgQCswRlimulVi5IXlQ2DhLgMaIM2", "");
//		long time = new Date().getTime();
//		System.out.println(time);
//		// util.addOrReplaceParams("ApiParams_operTime", "" + time);
//
//		util.addOrReplaceParams("parameter",
//				"{\"messageType\":\"4088\",\"accessUser\":\"gxecard_test_user_zhengchuan\",\"terminalNo\":\"\",\"terminalTransNo\":\"\",\"terminalDeviceNo\":\"\",\"outletNo\":\"\",\"proxyFlag\":\"\",\"cardNo\":\"\",\"cardPhysicsNo\":\"\",\"cardMasterType\":\"\",\"cardSubType\":\"\",\"deposit\":\"\",\"denomination\":\"\",\"firstRecharge\":\"\",\"cityCode\":\"\",\"randomNo\":\"\",\"operationType\":\"\",\"oldCardNo\":\"\",\"payManner\":\"\",\"idNo\":\"\",\"operatorId\":\"\",\"activationDate\":\"\",\"studentGrade\":\"\",\"beihaiCardChangeCompany\":\"\",\"beihaiCardChangeType\":\"\",\"beihaiCardChangeRemark\":\"\",\"beihaiCardChangeBalanceBefore\":\"\"}");
//		String ecardSign = util.rsaSign("UTF-8");
//		System.out.println(ecardSign);
//
//		String info = RsaSignUtil.sortParams(util.getParamsList()).toString();
//		boolean trage = util.rsaSignCheck(info, ecardSign, "UTF-8");
//		System.out.println(info);
//		System.out.println(trage);
//
//		byte[] signed = { 12, 13, 14, 15, 16, 17, 18, 19, 20 };
//		String info11 = Base64.encodeBase64String(signed);
//		System.out.println(info11);
//	}

}