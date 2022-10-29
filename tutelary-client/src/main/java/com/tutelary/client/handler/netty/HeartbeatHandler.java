package com.tutelary.client.handler.netty;

import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author linpl
 */
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {

    private static final Log LOGGER = LogFactory.get(HeartbeatHandler.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            ctx.writeAndFlush(new PongWebSocketFrame());
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

}
