package com.gxecard.customerservice.util;

import com.gxecard.customerservice.service.AccessControll;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;


import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSACoder {

	/**
	 * 数字签名
	 * 密钥算法
	 */
	public static final String KEY_ALGORITHM = "RSA";
	/**
	 * 数字签名
	 * 签名/验证算法
	 */
	public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
	// 公钥
	private static final String PUBLIC_KEY = "RSAPublicKey";
	//私钥
	private static final String PRIVATE_KEY = "RSAPrivateKey";
	
	private static final int KEY_SIZE = 1024;
	/**
	 * 私钥签名
	 * @param data 待签名数据
	 * @param privateKey 私钥
	 * @return 数字签名
	 * @throws Exception
	 */
	public static byte[] sign(byte[] data,byte[] privateKey) throws Exception{
		// 转换私钥材料
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
		// 实例化密钥工厂
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 取得私钥对象
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 实例化Signature
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		// 初始化Signature，导入私钥
		signature.initSign(priKey);
		// 更新，把待签名的内容放进去
		signature.update(data);
		// 签名
		return signature.sign();
	}

	/**
	 * 私钥签名
	 * @param data 待签名数据
	 * @param privateKey 私钥
	 * @return Base64编码签名字符串
	 * @throws Exception
	 */
	public static String sign(byte[] data,String privateKey) throws Exception{
		byte[] sign = sign(data,Base64.decodeBase64(privateKey));

		return Base64.encodeBase64String(sign);
	}
	public static boolean verify(byte[] data,byte[] publicKey,byte[] sign) throws Exception{
		// 转换公钥材料
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
		// 实例化密钥工厂
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 生成公钥
		PublicKey pubKey = keyFactory.generatePublic(keySpec);
		// 实例化Signature
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		// 初始化signature,导入公钥
		signature.initVerify(pubKey);
		// 更新，放入数据
		signature.update(data);
		// 验证
		return signature.verify(sign);
	}

	public static boolean verify(byte[] data,String publicKey,String sign) throws Exception{
		return verify(data,Base64.decodeBase64(publicKey),Base64.decodeBase64(sign));
	}
	
	/**
	 * 获取私钥
	 * @param keyMap 密钥Map
	 * @return byte[] 私钥
	 * @throws Exception
	 */
	public static byte[] getPrivateKey(Map<String,Object> keyMap) throws Exception{
		Key key = (Key)keyMap.get(PRIVATE_KEY);
	
		return 	key.getEncoded();
	}
	
	public static byte[] getPublicKey(Map<String,Object> keyMap) throws Exception{
		Key key = (Key)keyMap.get(PUBLIC_KEY);
		return key.getEncoded();
	}
	
	/**
	 * 初始化密钥,生成密钥对
	 * @return 密钥Map
	 * @throws Exception
	 */
	public static Map<String,Object> initKey() throws Exception{
		// 实例化密钥对生成器
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		// 初始化密钥对生成器
		keyPairGen.initialize(KEY_SIZE);
		// 生成密钥对
		KeyPair keyPair = keyPairGen.generateKeyPair();
		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		// 封装密钥
		HashMap<String, Object> keyMap = new HashMap<String,Object>(2);
		keyMap.put(PRIVATE_KEY, privateKey);
		keyMap.put(PUBLIC_KEY, publicKey);
		return keyMap;
	}

	/**
	 * 从用户配置中获取公钥
	 * @param aCont 控制数据集合
	 * @param accessUser 用户名
	 * @return
	 */
	public static String getPublicKeyFromConfig(AccessControll aCont, String accessUser){
		AccessControll.AccessContUnit unit = aCont.loadAccessCont(accessUser);
		if (unit == null) {
			throw new RuntimeException("not find user by userName:" + accessUser);
		}
		String userPubkey = unit.getUserPubkey();
		if(userPubkey == null || StringUtils.isEmpty(userPubkey)){
			throw new RuntimeException("The public key is not configured ");
		}

		return userPubkey;
	}
	
}
