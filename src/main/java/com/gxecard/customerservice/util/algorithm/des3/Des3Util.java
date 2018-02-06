package com.gxecard.customerservice.util.algorithm.des3;

import java.util.Date;
import java.util.Random;

import org.apache.tomcat.util.codec.binary.Base64;

public class Des3Util {

	public static void main(String[] args) throws Exception {
		Random rand = new Random(new Date().getTime());
		int index1 = rand.nextInt(500), index2 = rand.nextInt(500);
		if (index1 < 32)
			index1 = index1 + 32;
		if (index2 < 32)
			index2 = index2 + 32;

		String key_3des = "sldjlIs;,>+/log4j:WARN No such propojks_ca_2017_@$^_trust_jks_pwsldjlIs;,>+/log4j:WARN No such propojks_ca_2017_@$^_trust_jks_pwsldjlIs;,>+/log4j:WARN No such propojks_ca_2017_@$^_trust_jks_pwsldjlIs;,>+/log4j:WARN No such propojks_ca_2017_@$^_trust_jks_pwsldjlIs;,>+/log4j:WARN No such propojks_ca_2017_@$^_trust_jks_pwsldjlIs;,>+/log4j:WARN No such propojks_ca_2017_@$^_trust_jks_pwsldjlIs;,>+/log4j:WARN No such propojks_ca_2017_@$^_trust_jks_pwsldjlIs;,>+/log4j:WARN No such propojks_ca_2017_@$^_trust_jks_pw";
		String content = "jks_ca_2017_@$^_trust_jks_pw你好吗你好离开独领风骚绝对路径水电费索拉卡的分角色的咖啡机";
		byte[] contentByte = content.getBytes("UTF-8");

		String str1 = EnCoderDes3Func(key_3des, index1, index2, contentByte);
		System.out.println(str1);

		byte[] str2 = DeCoderDes3Func(key_3des, index1, index2, str1);
		System.out.println(new String(str2, "UTF-8"));
	}

	/**
	 * des3 加密
	 * 
	 * @param secretKeysAll
	 * @param first
	 * @param next
	 * @param contentBytes
	 * @return
	 * @throws Exception
	 */
	public static String EnCoderDes3Func(String secretKeysAll, int first, int next, byte[] contentBytes)
			throws Exception {
		if (secretKeysAll == null || secretKeysAll.trim().length() != 512)
			throw new RuntimeException("error code : 0001");
		if (first < 32 || first > 500 || next < 32 || next > 500)
			throw new RuntimeException("error code : 0002");
		if (contentBytes == null || contentBytes.length <= 0)
			throw new RuntimeException("error code : 0003");

		// 第一步：获取实际使用的24字节密钥：
		String firstKey = secretKeysAll.substring(first, first + 12);
		String nextKey = secretKeysAll.substring(next, next + 12);
		String sercetKey = firstKey + nextKey;

		// 第二步：3des加密
		byte[] secretContentByte = Des3Algorithm.des3EncodeCBC(sercetKey.getBytes(), contentBytes);

		// 第三步： base64编码
		String base64SecretContent = Base64.encodeBase64String(secretContentByte);

		return base64SecretContent;
	}

	/**
	 * 
	 * 3des 解密
	 * 
	 * @param secretKeysAll
	 * @param first
	 * @param next
	 * @param base64SecretContent
	 * @return
	 * @throws Exception
	 */
	public static byte[] DeCoderDes3Func(String secretKeysAll, int first, int next, String base64SecretContent)
			throws Exception {
		if (secretKeysAll == null || secretKeysAll.trim().length() != 512)
			throw new RuntimeException("error code : 0001");
		if (first < 32 || first > 500 || next < 32 || next > 500)
			throw new RuntimeException("error code : 0002");
		if (base64SecretContent == null || base64SecretContent.length() == 0)
			throw new RuntimeException("error code : 0003");

		// 第一步：获取实际使用的24字节密钥：
		String firstKey = secretKeysAll.substring(first, first + 12);
		String nextKey = secretKeysAll.substring(next, next + 12);
		String sercetKey = firstKey + nextKey;

		// 第二步：解密
		byte[] de_secretContentByte = Base64.decodeBase64(base64SecretContent);

		// 第三步：解密
		byte[] de_contentByte = Des3Algorithm.des3DecodeCBC(sercetKey.getBytes(), de_secretContentByte);

		return de_contentByte;
	}

}
