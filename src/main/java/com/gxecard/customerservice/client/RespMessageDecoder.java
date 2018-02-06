package com.gxecard.customerservice.client;

import com.gxecard.customerservice.entity.BaseRespMessage;
import com.gxecard.customerservice.entity.MessageHead;
import com.gxecard.customerservice.util.MessageUtil;
import com.gxecard.customerservice.util.RespMessageUtils;
import com.gxecard.customerservice.util.Utilitys;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class RespMessageDecoder extends ByteToMessageDecoder {
    private enum State {
        HEAD, BODY
    }

    private State state = State.HEAD;
    private MessageHead messageHead;

    // 消息头和消息身体
    private byte[] msgHeadBytes ,msgBodyBytes;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (state) {
            case HEAD:
                if (in.readableBytes() < MessageHead.MESSAGE_HEAD_SIZE) {
                    return;
                }
                byte[] headBytes = new byte[MessageHead.MESSAGE_HEAD_SIZE];
                in.readBytes(headBytes);
                // 保存消息头
                this.msgHeadBytes = (byte[]) Utilitys.copyObject(headBytes);
                messageHead = MessageUtil.createMessageHead(headBytes);
                state = State.BODY;
                break;
            case BODY:
                if(in.readableBytes() < messageHead.getBodyLength()) {
                    return;
                }
                byte[] bodyBytes = new byte[messageHead.getBodyLength()];
                in.readBytes(bodyBytes);
                // 保存消息体
                this.msgBodyBytes = (byte[]) Utilitys.copyObject( bodyBytes);
                BaseRespMessage respMessage = RespMessageUtils.parseRespMessage(messageHead, bodyBytes);
                out.add(respMessage);

                break;
        }
    }

    /**********************************************************************************/
    public byte[] getMsgHeadBytes(){
        return this.msgHeadBytes;
    }

    public byte[] getMsgBodyBytes(){
        return this.msgBodyBytes;
    }
}
