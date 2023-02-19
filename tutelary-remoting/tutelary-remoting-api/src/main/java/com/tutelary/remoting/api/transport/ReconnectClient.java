package com.tutelary.remoting.api.transport;

import cn.hutool.core.net.NetUtil;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.common.thread.NamedThreadFactory;
import com.tutelary.remoting.api.Channel;
import com.tutelary.remoting.api.ChannelHandler;
import com.tutelary.remoting.api.Codec;
import com.tutelary.remoting.api.EndpointContext;
import com.tutelary.remoting.api.exception.RemotingException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class ReconnectClient extends AbstractClient {

    private static final Log LOG = LogFactory.get();

    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE =
        new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("client-reconnect-"));

    public ReconnectClient(final EndpointContext endpointContext,
                           final ChannelHandler channelHandler,
                           final Codec codec) {
        super(endpointContext, channelHandler, codec);
    }

    @Override
    protected void connect() throws RemotingException {
        try {
            super.connect();
        } catch (Exception e) {
            LOG.error("Failed to start {} {} connect to the server {}, cause {}", getClass().getSimpleName(),
                NetUtil.getLocalhost(), getRemoteAddress(), e.getMessage());
            SCHEDULED_EXECUTOR_SERVICE.schedule(this::connect, 10, TimeUnit.SECONDS);
        }
    }

    @Override
    public void disconnected(final Channel channel) throws RemotingException {
        LOG.warn("server disconnected, ready to reconnect");
        SCHEDULED_EXECUTOR_SERVICE.schedule(this::connect, 10, TimeUnit.SECONDS);
        super.disconnected(channel);
    }
}
