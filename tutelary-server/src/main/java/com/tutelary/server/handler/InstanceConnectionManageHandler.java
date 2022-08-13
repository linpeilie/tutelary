package com.tutelary.server.handler;

import com.tutelary.common.helper.ChannelHelper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InstanceConnectionManageHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            log.info("channel : {} is idle", ChannelHelper.getChannelIP(ctx.channel()));
            ChannelHelper.closeChannel(ctx);
        }
        super.userEventTriggered(ctx, evt);
    }
}
