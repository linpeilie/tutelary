package com.tutelary.server.handler;

import com.tutelary.session.Session;
import com.tutelary.store.SessionStore;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectionManagerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug("channel断开连接 : {}", ctx.channel());
        SessionStore.removeSession(ctx.channel().id().asShortText());
        super.channelInactive(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("新加入channel : {}", ctx.channel());
        Session session = new Session(ctx.channel().id().asShortText(), ctx.channel());
        SessionStore.addSession(session);
        super.channelActive(ctx);
    }
}
