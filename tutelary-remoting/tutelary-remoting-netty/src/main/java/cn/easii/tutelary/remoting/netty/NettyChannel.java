package cn.easii.tutelary.remoting.netty;

import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.easii.tutelary.common.utils.Asserts;
import cn.easii.tutelary.common.utils.ThrowableUtil;
import cn.easii.tutelary.remoting.api.ChannelHandler;
import cn.easii.tutelary.remoting.api.EndpointContext;
import cn.easii.tutelary.remoting.api.constants.RemotingResponseCode;
import cn.easii.tutelary.remoting.api.exception.RemotingException;
import cn.easii.tutelary.remoting.api.transport.AbstractChannel;
import io.netty.channel.Channel;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

final class NettyChannel extends AbstractChannel {

    private static final Log LOG = LogFactory.get(NettyChannel.class);

    private static final ConcurrentMap<Channel, NettyChannel> CHANNEL_MAP = new ConcurrentHashMap<>();

    private final Channel channel;

    private final AtomicBoolean active = new AtomicBoolean(false);

    private final Map<String, Object> attributes = new ConcurrentHashMap<>();

    private NettyChannel(EndpointContext endpointContext, Channel channel, ChannelHandler channelHandler) {
        super(endpointContext, channelHandler);
        Asserts.notNull(channel, "netty channel is null");
        this.channel = channel;
    }

    static NettyChannel getOrAddChannel(EndpointContext endpointContext, Channel ch, ChannelHandler handler) {
        NettyChannel ret = CHANNEL_MAP.get(ch);
        if (ret == null) {
            NettyChannel nettyChannel = new NettyChannel(endpointContext, ch, handler);
            if (ch.isActive()) {
                nettyChannel.markActive(true);
                ret = CHANNEL_MAP.putIfAbsent(ch, nettyChannel);
            }
            if (ret == null) {
                ret = nettyChannel;
            }
        }
        return ret;
    }

    static void removeChannelIfDisconected(Channel ch) {
        if (ch != null && !ch.isActive()) {
            NettyChannel nettyChannel = CHANNEL_MAP.remove(ch);
            if (nettyChannel != null) {
                nettyChannel.markActive(false);
            }
        }
    }

    static void removeChannel(Channel ch) {
        if (ch != null) {
            NettyChannel nettyChannel = CHANNEL_MAP.remove(ch);
            if (nettyChannel != null) {
                nettyChannel.markActive(false);
            }
        }
    }

    @Override
    public boolean isConnected() {
        return !isClosed() && active.get();
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return (InetSocketAddress) channel.remoteAddress();
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return (InetSocketAddress) channel.localAddress();
    }

    @Override
    public void send(Object message) throws RemotingException {
        super.send(message);

        try {
            channel.writeAndFlush(message);
        } catch (Throwable e) {
            removeChannelIfDisconected(channel);
            throw new RemotingException(
                e, RemotingResponseCode.SEND_MESSAGE_UNCAUGHT_EXCEPTION, getRemoteAddress(), e.getMessage()
            );
        }
    }

    @Override
    public void close() {
        ThrowableUtil.safeExec(super::close);
        ThrowableUtil.safeExec(() -> removeChannelIfDisconected(channel));
        LOG.info("Close netty channel " + channel);
        ThrowableUtil.safeExec(channel::close);
    }

    private void markActive(boolean isActive) {
        active.set(isActive);
    }

    @Override
    public String toString() {
        return "NettyChannel [channel=" + channel + "]";
    }

    @Override
    public boolean hasAttribute(final String key) {
        return attributes.containsKey(key);
    }

    @Override
    public Object getAttribute(final String key) {
        return attributes.get(key);
    }

    @Override
    public void setAttribute(final String key, final Object value) {
        if (value == null) {
            removeAttribute(key);
        } else {
            attributes.put(key, value);
        }
    }

    @Override
    public void removeAttribute(final String key) {
        attributes.remove(key);
    }
}
