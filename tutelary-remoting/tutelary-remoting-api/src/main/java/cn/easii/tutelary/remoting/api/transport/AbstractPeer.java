package cn.easii.tutelary.remoting.api.transport;

import cn.easii.tutelary.common.utils.Asserts;
import cn.easii.tutelary.remoting.api.Channel;
import cn.easii.tutelary.remoting.api.ChannelHandler;
import cn.easii.tutelary.remoting.api.Endpoint;
import cn.easii.tutelary.remoting.api.EndpointContext;
import cn.easii.tutelary.remoting.api.exception.RemotingException;

public abstract class AbstractPeer implements Endpoint, ChannelHandler {

    private final EndpointContext endpointContext;

    private final ChannelHandler handler;

    private volatile boolean closed;

    public AbstractPeer(EndpointContext endpointContext, ChannelHandler channelHandler) {
        Asserts.notNull(channelHandler, "handler == null");
        Asserts.notNull(endpointContext, "endpointContext == null");
        this.handler = channelHandler;
        this.endpointContext = endpointContext;
    }

    @Override
    public void connected(Channel channel) throws RemotingException {
        if (closed) {
            return;
        }
        handler.connected(channel);
    }

    @Override
    public void disconnected(Channel channel) throws RemotingException {
        handler.disconnected(channel);
    }

    @Override
    public void send(Channel channel, Object message) throws RemotingException {
        if (closed) {
            return;
        }
        handler.send(channel, message);
    }

    @Override
    public void received(Channel channel, Object message) throws RemotingException {
        if (closed) {
            return;
        }
        handler.received(channel, message);
    }

    @Override
    public void caught(Channel channel, Throwable throwable) throws RemotingException {
        handler.caught(channel, throwable);
    }

    @Override
    public void close() {
        closed = true;
    }

    @Override
    public boolean isClosed() {
        return closed;
    }

    @Override
    public EndpointContext getEndpointContext() {
        return endpointContext;
    }

}
