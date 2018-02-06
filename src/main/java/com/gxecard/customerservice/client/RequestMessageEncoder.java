package com.gxecard.customerservice.client;

import com.gxecard.customerservice.entity.BaseReqMessage;
import com.gxecard.customerservice.util.ReqMessageUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RequestMessageEncoder extends MessageToByteEncoder<BaseReqMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, BaseReqMessage msg, ByteBuf out) throws Exception {
        byte[] bytes = ReqMessageUtils.convertMessageToBytes(msg);
        out.writeBytes(bytes);
    }
}
