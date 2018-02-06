package com.gxecard.customerservice.entity.resp;

import com.gxecard.customerservice.annotation.SocketRespParam;
import com.gxecard.customerservice.entity.BaseRespMessage;

/**
 * 2074 查询圈存记录状态 
 * @author 吴洪全 20180119
 *
 */
public class QueryRechargeRecordStatusRespMessage extends BaseRespMessage {

	// 卡号
	private String cardNo = "";

	// 卡子类型
	private String cardSubType = "";

	// 交易金额
	private String rechargeAmount = "";

	// 交易状态
	private String rechargeStatus = "";

	// 交易时间
	private String rechargeTime = "";

	// 确认时间
	private String comfirmTime = "";

	public String getCardNo() {
		return cardNo;
	}

	@SocketRespParam(order = 2, length = 19)
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardSubType() {
		return cardSubType;
	}

	@SocketRespParam(order = 3, length = 4)
	public void setCardSubType(String cardSubType) {
		this.cardSubType = cardSubType;
	}

	public String getRechargeAmount() {
		return rechargeAmount;
	}

	@SocketRespParam(order = 4, length = 8)
	public void setRechargeAmount(String rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public String getRechargeStatus() {
		return rechargeStatus;
	}

	@SocketRespParam(order = 5, length = 1)
	public void setRechargeStatus(String rechargeStatus) {
		this.rechargeStatus = rechargeStatus;
	}

	public String getRechargeTime() {
		return rechargeTime;
	}

	@SocketRespParam(order = 6, length = 14)
	public void setRechargeTime(String rechargeTime) {
		this.rechargeTime = rechargeTime;
	}

	public String getComfirmTime() {
		return comfirmTime;
	}

	@SocketRespParam(order = 7, length = 14)
	public void setComfirmTime(String comfirmTime) {
		this.comfirmTime = comfirmTime;
	}

}
