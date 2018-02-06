package com.gxecard.customerservice.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

public class AccountConfig {
        public static final String priKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIce04zHznF9Xk99KR2jd3OQILeSffBcBj6gNGxs5cqopdLYE6vyYYxhX52BtJZfXsWVetLawepWHC5ZDw3QlzOR3resBOBEiZZmEXzO9xtFD+Eo6zxoCPmIt2zseTOPKMN6h/ocPiP0BaNfw4MGhqURTZX+7yzhcVNqu7tcRt0DAgMBAAECgYAaKEaN11RIthteyfz+ptmF/p3Gy84jjL0MvJjJcJ9fhxxq37eXLtnhgiwvYp+iCJ5hI5neXcjyY28bLdRzOTE72U4QtCawKtaCfPDgh1npmxsi35rCVXleUCy21g1K2qAIpJzwR1OAXjZQ3upmmeo8hVG/SfsSaIdZKeeNWL7SGQJBAO215ffPDPaEVHSfnpVIHGQuaQz1XYIB9dewT0l71jryiYVxr4RYaM6SQ9kMiczGQTxBimhJsfPcrbZuCIuFha8CQQCRhD8BKWjQawy5bETta+d4r6EtTQV0SgzsEYcII93I3SeaNpQd/Eveob2nDgghgCyiQNbUADaiFDg7mogh+AbtAkByWE5v/twodQ0/ME0TWCW4Fw8XWawKTrEYeMoa+gPaRTLbywz356M6wub6MGrU4wUcXBadBd4hBSVWnOVlX+wnAkBeSKCGAS2fdOz+q08RwkttH+a/slrpWR18S3MBbDPEdL63Jcvcjd8etg9q0SWlhyXkTzyiiizb4Vv6CotP7yOVAkAxFU5A0c7Ih8yb0rSlB7n1tIzsx+iARIO95QP3yy7OhI11fOKIxKRCepAIeizdjmHjSysGPi5uXcqHxUTayJvU";//私钥

        public  static final String refundUrl = "http://117.141.142.7:9493/uaps-web-gateway2/reversal/reversalOrder"; //充正订单url

        public  static final String rechargeUrl = "http://117.141.142.7:9493/uaps-web-gateway2/WFCauthCodePay/initPay"; // 充值url

        /**
         * 统一账户分配的appid
         */
        public static final String APPID = "90003";


}
