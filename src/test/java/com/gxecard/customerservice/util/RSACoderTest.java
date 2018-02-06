package com.gxecard.customerservice.util;

import static org.junit.Assert.assertTrue;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RSACoderTest {

	;
	private  String pubKey;
	private  String priKey;
	
	@Before
	public void initKey() throws Exception{
		Map<String, Object> keyMap = RSACoder.initKey();
		priKey = Hex.encodeHexString(RSACoder.getPrivateKey(keyMap));
		pubKey = Hex.encodeHexString(RSACoder.getPublicKey(keyMap));
		log.info("公钥：{}", pubKey);
		log.info("私钥：{}",priKey);
		
	}
	
	@Test
	public void singTest() throws Exception{
		// 待签名数据
		String inputStr = "传来的数据";
		byte[] data = inputStr.getBytes();
		// 生成签名
		String sign = RSACoder.sign(data,priKey);
		log.info("签名后：{}",sign);
		// 验证签名
		boolean verify = RSACoder.verify(data, pubKey, sign);
		log.info("验签结果：{}",verify);
		// 断言
		assertTrue(verify);
		
	}
}
