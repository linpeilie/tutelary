package com.tutelary.handler;

import com.tutelary.common.BaseMessage;
import com.tutelary.processor.MessageProcessor;
import com.tutelary.processor.MessageProcessorManager;
import com.tutelary.message.ErrorMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.List;

@ChannelHandler.Sharable
public class CmdMessageHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private final MessageProcessorManager messageProcessorManager;

    public CmdMessageHandler(MessageProcessorManager messageProcessorManager) {
        this.messageProcessorManager = messageProcessorManager;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame webSocketFrame) throws Exception {
        // decoder
        ByteBuf byteBuf = webSocketFrame.content();
        byte cmd = byteBuf.readByte();
        int length = byteBuf.readInt();
        MessageProcessor<? extends BaseMessage> handler = messageProcessorManager.getHandler(cmd);
        if (handler == null) {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setLastCmd(String.valueOf(cmd));
            errorMessage.setMessage("Unknown command " + cmd);
            ctx.writeAndFlush(errorMessage);
            return;
        }
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        // handler
        handler.process(ctx, bytes);
    }

}
