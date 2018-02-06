package com.gxecard.customerservice.entity.resp;

import com.gxecard.customerservice.annotation.SocketRespParam;
import com.gxecard.customerservice.entity.BaseRespMessage;

/**
 * 发票提取码接口 响应对象 2064
 * @author lenovoe
 *
 */
public class InvoiceExtractionCodeRespMessage extends BaseRespMessage{

	// 发票提取码
	private String authCode = "";
	// 卡号
	private String cardNo = "";
	// 交易类型
	private String transTime = "";
	// 提取码有效日期
	private String validDate = "";
	
	
	public String getAuthCode() {
		return authCode;
	}
	@SocketRespParam(order = 2,length = 8)
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getCardNo() {
		return cardNo;
	}
	@SocketRespParam(order = 3,length = 19)
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getTransTime() {
		return transTime;
	}
	@SocketRespParam(order = 4,length = 8)
	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}
	public String getValidDate() {
		return validDate;
	}
	@SocketRespParam(order = 5,length = 8)
	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}
	
	
}
