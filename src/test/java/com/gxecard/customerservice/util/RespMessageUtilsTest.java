package com.gxecard.customerservice.util;

import com.gxecard.customerservice.entity.BaseRespMessage;
import com.gxecard.customerservice.entity.MessageHead;
import org.junit.Test;

import java.util.Arrays;

public class RespMessageUtilsTest {
	@Test
	public void parseRespMessage() throws Exception {
		// 所有的响应结果可以放在这里进行解析
		byte[] resp = { 0, -119, 0, 0, 0, 0, 82, 48, 48, 56, 57, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32,
				32, 48, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32,
				32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 48, 48, 48, 49, 51, 48, 49, 49, 51, 48, 48, 50,
				48, 48, 48, 49, 49 };
		byte[] headBytes = Arrays.copyOf(resp, MessageHead.MESSAGE_HEAD_SIZE);
		byte[] bodyBytes = new byte[resp.length - MessageHead.MESSAGE_HEAD_SIZE];
		System.arraycopy(resp, MessageHead.MESSAGE_HEAD_SIZE, bodyBytes, 0, bodyBytes.length);

		MessageHead messageHead = MessageUtil.createMessageHead(headBytes);
		BaseRespMessage respMessage = RespMessageUtils.parseRespMessage(messageHead, bodyBytes);

		System.out.print(Utilitys.objectToMapByDepthOnce(respMessage));
		return;
	}

}