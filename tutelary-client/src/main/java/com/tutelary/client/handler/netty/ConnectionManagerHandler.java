package com.tutelary.client.handler.netty;

import java.util.List;

import com.tutelary.client.service.ClientService;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@ChannelHandler.Sharable
public class ConnectionManagerHandler extends ChannelInboundHandlerAdapter {
    private static final Log LOG = LogFactory.get();

    private final List<ClientService> clientServices;

    public ConnectionManagerHandler(List<ClientService> clientServices) {
        this.clientServices = clientServices;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOG.info("Successfully connected to the server");
        clientServices.forEach(ClientService::start);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOG.warn("server channel inactive");
        clientServices.forEach(ClientService::stop);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOG.error("caught exception", cause);
        clientServices.forEach(ClientService::stop);
    }
}
