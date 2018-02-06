package com.gxecard.customerservice.entity;

import com.gxecard.customerservice.annotation.SocketReqParam;

public abstract class BaseReqMessage {
	private String messageType = "";

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	@SocketReqParam(order = Integer.MIN_VALUE, length = 4)
	public String getMessageType() {
		return messageType;
	}

	/*
	 * 郑川新增20170626。解决问题，刘贵宁设计的输入参数里面没有用户信息，即不能区分是微付充调用 还是空发调用的，后续难以维护。
	 * 
	 * 经过代码分析，在输入参数里面只有包含注解的函数才会发送给铭鸿系统，所以这里添加一个无注解的参数。
	 * 即能够满足输入用户，区分是谁调用的接口，又能够满足不影响铭鸿现有的系统。
	 * 
	 * 后续的工中再将accessUser存入数据库中。
	 */

	private String accessUser = "";

	public void setAccessUser(String accessUser) {
		this.accessUser = accessUser;
	}

	public String getAccessUser() {
		return accessUser;
	}
}
