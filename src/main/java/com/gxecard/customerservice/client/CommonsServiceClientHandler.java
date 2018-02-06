package com.gxecard.customerservice.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 新的，通用的socket处理类，以前铭洪的用CustomerServiceClientHandler
 * 新接入的socket接口通通这个
 * @author 吴洪全 TODO
 *
 */
public class CommonsServiceClientHandler extends SimpleChannelInboundHandler<String>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		
	}

	
}
