package com.gxecard.customerservice.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 * https 证书的配置信息类
 * @author lenovoe
 *
 */
@Component
public  class HtppsConfig {
	/**
	 * 密钥库文件路径
	 */
	@Value("${system.keypath.keystore}")
	public String keyStorePath;
	/**
	 * 信任库文件路径
	 */
	@Value("${system.keypath.truststore}")
	public String trustStorePath;
	/**
	 * 密钥库密码
	 */
	@Value("${system.keypw.keystorepw}")
	public String keyStorePw;
	/**
	 * 信任库密码
	 */
	@Value("${system.keypw.truststorepw}")
	public String trustStorePw;
	
	
	
	
	public HtppsConfig(String keyStorePath, String trustStorePath, String keyStorePw, String trustStorePw,
			String keyPw) {
		super();
		this.keyStorePath = keyStorePath;
		this.trustStorePath = trustStorePath;
		this.keyStorePw = keyStorePw;
		this.trustStorePw = trustStorePw;
	}
	/**
	 * 采用配置文件的默认值
	 */
	public HtppsConfig() {
		super();
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
	
}
