package com.tutelary.client.handler.netty;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.tutelary.client.ClientBootstrap;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

public class ClientLifeCycleListener extends ChannelDuplexHandler {

    private static final Log LOG = LogFactory.get();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOG.error("服务端断开连接");
        ClientBootstrap.connect();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }


}
