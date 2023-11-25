package cn.easii.tutelary.remoting.api.transport;

import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.easii.tutelary.common.utils.ThrowableUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.easii.tutelary.remoting.api.Channel;
import cn.easii.tutelary.remoting.api.ChannelHandler;
import cn.easii.tutelary.remoting.api.exception.RemotingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChannelHandlerDispatcher implements ChannelHandler {

    private static final Log LOG = LogFactory.get(ChannelHandlerDispatcher.class);

    private final Collection<ChannelHandler> channelHandlers = new CopyOnWriteArrayList<>();

    public ChannelHandlerDispatcher() {
    }

    public ChannelHandlerDispatcher(ChannelHandler... handlers) {
        this(handlers == null ? null : Arrays.asList(handlers));
    }

    public ChannelHandlerDispatcher(Collection<ChannelHandler> handlers) {
        if (CollectionUtil.isNotEmpty(handlers)) {
            this.channelHandlers.addAll(handlers);
        }
    }

    @Override
    public void connected(Channel channel) throws RemotingException {
        for (ChannelHandler channelHandler : channelHandlers) {
            ThrowableUtil.safeExec(() -> channelHandler.connected(channel));
        }
    }

    @Override
    public void disconnected(Channel channel) throws RemotingException {
        for (ChannelHandler channelHandler : channelHandlers) {
            ThrowableUtil.safeExec(() -> channelHandler.disconnected(channel));
        }
    }

    @Override
    public void send(Channel channel, Object message) throws RemotingException {
        for (ChannelHandler channelHandler : channelHandlers) {
            ThrowableUtil.safeExec(() -> channelHandler.send(channel, message));
        }
    }

    @Override
    public void received(Channel channel, Object message) throws RemotingException {
        for (ChannelHandler channelHandler : channelHandlers) {
            ThrowableUtil.safeExec(() -> channelHandler.received(channel, message));
        }
    }

    @Override
    public void caught(Channel channel, Throwable throwable) throws RemotingException {
        for (ChannelHandler channelHandler : channelHandlers) {
            ThrowableUtil.safeExec(() -> channelHandler.caught(channel, throwable));
        }
    }
}
