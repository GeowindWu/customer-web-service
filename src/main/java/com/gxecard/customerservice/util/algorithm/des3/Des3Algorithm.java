package com.gxecard.customerservice.util.algorithm.des3;

import java.security.Key;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.tomcat.util.codec.binary.Base64;


/**
 * 
 * 3des 密钥是24字节，192位。
 * 
 * JDK 1.7 以上
 * 
 * @author 郑川
 * 
 */
public class Des3Algorithm {
	public static void main(String[] args) throws Exception {

		// byte[] key = new BASE64Decoder()
		// .decodeBuffer("YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4");
		// byte[] keyiv = createKeyIv();
		//
		// String sercetInfo =
		// "{\"status\":\"0\",\"AccessToken\":\"qtFCfXbwR8e3CjV-bXd4Bu2lY80ASh5YOMaIKOcOSqfmxH0qU4BggwzfSsYIzaLwhT1Kbfu9D6oNzq-f8kiI0E3VWwWaljuIBbO8pqyPn0Po6xZHnt0c0tiesFbvXvckVHMbAEAAMR\",\"effectiveLastTime\":\"1500261833643\"}";
		// byte[] data = sercetInfo.getBytes("UTF-8");
		//
		// System.out.println("ECB加密解密");
		// byte[] str3 = des3EncodeECB(key, data);
		// byte[] str4 = ees3DecodeECB(key, str3);
		// System.out.println(new BASE64Encoder().encode(str3));
		// System.out.println(new String(str4, "UTF-8"));
		// System.out.println();
		// System.out.println("CBC加密解密");
		// byte[] str5 = des3EncodeCBC(key, keyiv, data);
		// byte[] str6 = des3DecodeCBC(key, keyiv, str5);
		// System.out.println(new BASE64Encoder().encode(str5));
		// System.out.println(new String(str6, "UTF-8"));
		//
		// // byte[] ivkey = createKeyIv();
		// // System.out.println(ivkey[1]);
		//

		// 密钥24字节 192 。
		String key_3des = "log4j:WARN No such propo";
		String content = "1234567890-=122";
		byte[] key3desByte = key_3des.getBytes("UTF-8");
		byte[] contentByte = content.getBytes("UTF-8");

		// 开始加密
		// 第一步：先加密，在编码
		System.out.println("加密过程");
		byte[] secretContentByte = des3EncodeCBC(key3desByte, contentByte);
		System.out.println("加密后的密文：" + new String(secretContentByte));
		String base64SecretContent = Base64.encodeBase64String(secretContentByte);
		System.out.println("base64 加密的密文：" + base64SecretContent);

		// 第二步：先解密，在解密
		System.out.println();
		System.out.println("解密过程");
		byte[] de_secretContentByte = Base64.decodeBase64(base64SecretContent);
		byte[] de_contentByte = des3DecodeCBC(key_3des.getBytes(), de_secretContentByte);
		System.out.println(new String(de_contentByte, "UTF-8"));
	}

	/*********************************************************************************/
	/*********************************************************************************/
	public static byte[] des3EncodeCBC(byte[] key, byte[] data) throws Exception {
		byte[] ivkey = createKeyIv();
		return des3EncodeCBC(key, ivkey, data);
	}

	public static byte[] des3DecodeCBC(byte[] key, byte[] data) throws Exception {
		byte[] ivkey = createKeyIv();
		return des3DecodeCBC(key, ivkey, data);
	}

	// 获取keyIv
	private static byte[] createKeyIv() {
		byte[] keyIv = new byte[8];
		Calendar c = Calendar.getInstance();// 可以对每个时间域单独修改

		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);

		// int hour = c.get(Calendar.HOUR_OF_DAY);
		// int minute = c.get(Calendar.MINUTE);
		// int second = c.get(Calendar.SECOND);
		// 月份
		keyIv[0] = (byte) (month % 100 / 10);
		keyIv[1] = (byte) (month % 10 / 1);

		// 年
		keyIv[2] = (byte) (year % 10000 / 1000);
		keyIv[3] = (byte) (year % 1000 / 100);
		keyIv[4] = (byte) (year % 100 / 10);
		keyIv[5] = (byte) (year % 10 / 1);

		// 日
		keyIv[6] = (byte) (date % 100 / 10);
		keyIv[7] = (byte) (date % 10 / 1);

		return keyIv;
	}

	/**
	 * CBC加密
	 * 
	 * @param key
	 *            密钥
	 * @param keyiv
	 *            IV
	 * @param data
	 *            明文
	 * @return Base64编码的密文
	 * @throws Exception
	 */
	private static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data) throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(keyiv);
		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}

	/**
	 * CBC解密
	 * 
	 * @param key
	 *            密钥
	 * @param keyiv
	 *            IV
	 * @param data
	 *            Base64编码的密文
	 * @return 明文
	 * @throws Exception
	 */
	private static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data) throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(keyiv);
		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}

	/*********************************************************************/
	// 下面的加密模式一般情况下不使用
	// 下面的加密模式一般情况下不使用
	/*********************************************************************/
	/*********************************************************************/
	/*********************************************************************/
	/*********************************************************************/
	/*********************************************************************/
	/*********************************************************************/
	/*********************************************************************/
	/*********************************************************************/

	/**
	 * ECB加密,不要IV
	 * 
	 * @param key
	 *            密钥
	 * @param data
	 *            明文
	 * @return Base64编码的密文
	 * @throws Exception
	 */
	public static byte[] des3EncodeECB(byte[] key, byte[] data) throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}

	/**
	 * ECB解密,不要IV
	 * 
	 * @param key
	 *            密钥
	 * @param data
	 *            Base64编码的密文
	 * @return 明文
	 * @throws Exception
	 */
	public static byte[] ees3DecodeECB(byte[] key, byte[] data) throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, deskey);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}
}