package com.gxecard.customerservice.entity.req;

import com.gxecard.customerservice.annotation.SocketReqParam;
import com.gxecard.customerservice.entity.BaseReqMessage;

/**
 * 查询卡片圈存记录状态 2074
 * 
 * @author 吴洪全 20180119
 *
 */
public class QueryRechargeRecordStatusReqMessage extends BaseReqMessage {

	// 网点编号
	private String outletNo = "";

	// 终端编号
	private String terminalNo = "";

	// 操作员编码
	private String operatorId = "";

	// 逻辑卡号
	private String cardNo = "";

	// 业务类型
	private String businessType = "";

	// 业务流水号
	private String businessNo = "";

	@SocketReqParam(order = 2, length = 16)
	public String getOutletNo() {
		return outletNo;
	}

	public void setOutletNo(String outletNo) {
		this.outletNo = outletNo;
	}

	@SocketReqParam(order = 3, length = 16)
	public String getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	@SocketReqParam(order = 4, length = 8)
	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	@SocketReqParam(order = 5, length = 19)
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	@SocketReqParam(order = 6, length = 4)
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
	@SocketReqParam(order = 7, length = 16)
	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	};

}
