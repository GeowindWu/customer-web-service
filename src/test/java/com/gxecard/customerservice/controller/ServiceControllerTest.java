package com.gxecard.customerservice.controller;

import com.gxecard.customerservice.service.AccessControll;
import com.gxecard.customerservice.util.RSACoder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sun.security.rsa.RSASignature;

import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class ServiceControllerTest {
    @Autowired
    private MockMvc mcv;
    @Autowired
    private AccessControll accessControll;
    private String accessUser = "system_user";
    private String parameter = "{\n" +
            "\t\"terminalTransNo\": \"0000000000001115\",\n" +
            "\t\"terminalDeviceId\": \"6310413188000003\",\n" +
            "\t\"outletCode\": \"6310028831000001\",\n" +
            "\t\"proxyFlag\": \"1\",\n" +
            "\t\"cardNo\": \"3104840897000015730\",\n" +
            "\t\"cardPhysicsNo\": \"000486739541A37B\",\n" +
            "\t\"cardMasterType\": \"11\",\n" +
            "\t\"cardSubType\": \"0101\",\n" +
            "\t\"lastTransTerminalNo\": \"413188000003\",\n" +
            "\t\"lastTransTime\": \"20180122171003\",\n" +
            "\t\"rechargeRandomNo\": \"EF30091D\",\n" +
            "\t\"cardOnlineTransCount\": \"0007\",\n" +
            "\t\"cardOfflineTransCount\": \"0000\",\n" +
            "\t\"businessType\": \"14\",\n" +
            "\t\"payManner\": \"02\",\n" +
            "\t\"rechargeAmount\": \"1\",\n" +
            "\t\"cardBalance\": \"7\",\n" +
            "\t\"mac1\": \"E6F55D6F\",\n" +
            "\t\"operatorId\": \"xueqiu\",\n" +
            "\t\"cityCode\": \"6310\",\n" +
            "\t\"rechargeManner\": \"0\",\n" +
            "\t\"overdraft\": \"00000000\",\n" +
            "\t\"messageType\": \"5002\",\n" +
            "\t\"terminalNo\": \"6310413188000003\",\n" +
            "\t\"accessUser\": \"system_user\",\n" +
            "\t\"orderTime\": \"20180129110101\",\n" +
            "\t\"userNo\": \"QA88882018012910001155\"\n" +
            "}";
    private String priKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANGl8w7rj7Zf2UxmLTJpuRN5bIY3CT6Ki5lTL94IeFmIDvefB6yhiMJCHPY5COGBGA9aPpRLJdnRERPEsh1Jd3LxDJYXjcmzSoyGRGFuCvlCx5PyJqybYsPqAfzl3pkixujNPpm1DSvtDZaRdnkPo0LUdDAOSglLl5Ns+PKsvGAbAgMBAAECgYBvvRVJFIAqYfe9YpAgWH0ORh9zgY3pyYoSFGZk3vrWLVBc5mP+vLHZZ8dr7IYxOs8KD71e6Jap+2YwtRuBxJBmuMtkwWkF+IkCiGX5Icgmhc14h+cYUb7poXRQBvliLNK6nZumzliVybPSPRdDckP4Piqhw09LiKF8ss+0DBbY4QJBAP6FEcyZsP0RaRkaayJD8NYucyUIMdfGVNiilOPIkHrqWqdNLUXNGA7sVDw9lvGG403NSTMAIOuqpP2+DNKdx7kCQQDS3hMsBug6DI5Q6hnjHFqvQyR8JtF5WF4A3knrtTIEzPLf715RrNaQ+UIXeOAr7D9hV1qcQIU+dD5GGNsfvOhzAkANCYnw3gqH3AVzKLxGJA7JGdYCFppfACTGeAWCaXqlIS4Fldl00t2hng9uioUZ2Bv6l0Jhn2cQS0xqRvrnKvfZAkEAwc+zt2ex0ojv1ZcVrBTHlQcVZbcYGXwLCqlFMAweqSLmqMSN4GwH2r/+6ywCftxpdMKhWbRDw598DVmTLZyn2QJBAOTzGmNRpDSXmxmTRBk+x83xMNuxwKbBLPjsKKoXvG1GCSDYbg0o95f1vOs/eeuykKpK91WTX8o2W3Euv1TejkM=";
    private String ecardSign = "";

    private String comfirmParameter = "{\n" +
            "\t\"accessUser\": \"system_user\",\n" +
            "\t\"businessType\": \"14\",\n" +
            "\t\"cardBalance\": \"15700\",\n" +
            "\t\"cardMasterType\": \"11\",\n" +
            "\t\"cardNo\": \"3104840896000004838\",\n" +
            "\t\"cardOfflineTransCount\": \"0000\",\n" +
            "\t\"cardOnlineTransCount\": \"006C\",\n" +
            "\t\"cardSubType\": \"0101\",\n" +
            "\t\"messageType\": \"1003\",\n" +
            "\t\"operatorId\": \"wfckf\",\n" +
            "\t\"outletCode\": \"6310029031000001\",\n" +
            "\t\"payManner\": \"01\",\n" +
            "\t\"proxyFlag\": \"1\",\n" +
            "\t\"rechargeAmount\": \"100\",\n" +
            "\t\"referenceNo\": \"582246\",\n" +
            "\t\"tac\": \"71D486E3\",\n" +
            "\t\"terminalNo\": \"6310413190000001\",\n" +
            "\t\"terminalTransNo\": \"1000000000001109\",\n" +
            "\t\"transTime\": \"20180112104619\",\n" +
            "\t\"writeCardResult\": \"1\"\n" +
            "}";
    // 单独的扣费接口
    private String kfParam = "{\n" +
            "\t\"accessUser\": \"system_user\",\n" +
            "\t\"messageType\": \"10001\",\n" +
            "\t\"orderDesc\": \"wfcRecharege\",\n" +
            "\t\"orderTime\": \"20180129110101\",\n" +
            "\t\"outTradeNo\": \"0000000000001125\",\n" +
            "\t\"randomStr\": \"6673564482985892\",\n" +
            "\t\"totalFee\": \"1\",\n" +
            "\t\"userNo\": \"QA88882018012910001155\"\n" +
            "}";

    private String refundParam = "{\n" +
            "\t\"accessUser\": \"system_user\",\n" +
            "\t\"messageType\": \"10002\",\n" +
            "\t\"timestamp\": \"2018-01-29 11:01:01\",\n" +
            "\t\"outTradeNo\": \"0000000000001114\",\n" +
            "\t\"randomStr\": \"6673564482985892\"\n" +
            "}";
    @Before
    public void init(){
        // 生成公私钥
        try {
//            Map<String, Object> keyMap = RSACoder.initKey();
//            byte[] publicKey = RSACoder.getPublicKey(keyMap);
//            byte[] privateKey = RSACoder.getPrivateKey(keyMap);
//            priKey = Base64.encodeBase64String(privateKey);
//            String pubKey = Base64.encodeBase64String(publicKey);

//            // 加载用户数据
            accessControll.initList(true);
//            AccessControll.AccessContUnit unit = accessControll.loadAccessCont(accessUser);
//            log.info("公钥：{}",unit.getUserPubkey());
              // 签名
            ecardSign  = RSACoder.sign(refundParam.getBytes(), priKey);
            log.info("参数：{}",refundParam);
            log.info("得到签名：{}",ecardSign);
            log.info("私钥：{}",priKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void serve() {
        try {

//        mcv.perform(MockMvcRequestBuilders.post("/service")
//                .param("parameter",refundParam).param("ecardSign", ecardSign))
//                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());

//                .andExpect(MockMvcResultMatchers.content().string("aaa"));
//                .andReturn();

//        String pubKey = RSACoder.getPublicKeyFromConfig(accessControll, accessUser);
//        log.info("公钥：{}",pubKey);
////			// 验证签名
//
//
//
//            log.info("验签前打印签名：{}",ecardSign);
//            boolean verify = RSACoder.verify(parameter.getBytes(), pubKey, ecardSign);
//            log.info("验签结果：{}",verify);
//            assertTrue(verify);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}