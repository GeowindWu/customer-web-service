package com.gxecard.customerservice.entity.req;

import com.gxecard.customerservice.annotation.SocketReqParam;
import com.gxecard.customerservice.entity.BaseReqMessage;

/**
 * 发票提取码接口 20180123
 * @author 吴洪全
 *
 */
public class InvoiceExtractionCodeReqMessage extends BaseReqMessage{

	// 网点编码
	private String outletCode = "";
	//操作员
	private String operatorId = "";
	// 交易类型
	private String transType = "";
	// 客服流水号
	private String serialNo = "";
	
	@SocketReqParam(order=2,length=16)
	public String getOutletCode() {
		return outletCode;
	}
	public void setOutletCode(String outletCode) {
		this.outletCode = outletCode;
	}
	@SocketReqParam(order = 3, length = 8)
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	 @SocketReqParam(order = 4, length = 4)
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	 @SocketReqParam(order = 5, length = 16)
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	
}
