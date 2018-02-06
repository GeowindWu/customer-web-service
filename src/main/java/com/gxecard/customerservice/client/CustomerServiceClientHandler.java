package com.gxecard.customerservice.client;

import com.google.gson.Gson;
import com.gxecard.customerservice.entity.BaseReqMessage;
import com.gxecard.customerservice.entity.BaseRespMessage;
import com.gxecard.customerservice.service.MessageService;
import com.gxecard.customerservice.util.ReqMessageUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.async.DeferredResult;

/***
 * 如果 channelRead 函数异常，就会进入exceptionCaught函数中去。
 *
 */
@Slf4j
public class CustomerServiceClientHandler extends ChannelInboundHandlerAdapter {
	// 数据对象
	private int accessid;
	private MessageService messageService;
	private byte[] msgReqBytes;

	// 业务功能相关的对象
	private final BaseReqMessage reqMessage;
	private final DeferredResult<BaseRespMessage> deferredResult;
	private final RespMessageDecoder baseDecoder;

	public CustomerServiceClientHandler(final int accessid, final MessageService messageService,
			BaseReqMessage reqMessage, DeferredResult<BaseRespMessage> deferredResult, RespMessageDecoder baseDecoder) {
		this.reqMessage = reqMessage;
		this.deferredResult = deferredResult;
		this.messageService = messageService;
		this.accessid = accessid;
		this.baseDecoder = baseDecoder;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		byte[] bytes = ReqMessageUtils.convertMessageToBytes(reqMessage);
		this.msgReqBytes = bytes; // 记录请求的参数
		ctx.writeAndFlush(bytes);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		BaseRespMessage resultMsg = (BaseRespMessage) msg;
		resultMsg.setLogIndexId(this.accessid + "");
		// 第一步：返回结果，即先返回前段。调用setResult就会返回前段
		deferredResult.setResult(resultMsg);
		ctx.close();

		// 第二步：因为他程序有问题，如果先记录日志的话，如果报错了 就不会返回前段了。
		try {
			byte[] bt2 = baseDecoder.getMsgBodyBytes(), bt1 = baseDecoder.getMsgHeadBytes();
			// messageService.updateDbLog(accessid, true,
			// Utilitys.objectToMapByDepthOnce(msg).toString(),
			// resultMsg.getResponseCode(), resultMsg.getErrorDescription());
			messageService.updateServerParams(accessid, true, new Gson().toJson(msg), resultMsg.getResponseCode(),
					resultMsg.getErrorDescription(), this.msgReqBytes, bt1, bt2);
		} catch (Exception e) {
			log.error("channelRead log update Exception ！" + e.getMessage());
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("Socket Communication eception ", cause);
		try {
			messageService.updateServerParams(accessid, false, cause.getMessage(), "", "", this.msgReqBytes,
					baseDecoder.getMsgHeadBytes(), baseDecoder.getMsgBodyBytes());
		} catch (Exception e) {
			log.error("exceptionCaught log update excption ：", e.getMessage());
		} finally { // 无论如何都要返回前段
			deferredResult.setErrorResult(cause);
			ctx.close();
		}
	}
}
