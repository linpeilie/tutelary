package cn.easii.tutelary.remoting.api.transport;

import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.easii.tutelary.common.utils.ThrowableUtil;
import cn.hutool.core.net.NetUtil;
import cn.easii.tutelary.remoting.api.Channel;
import cn.easii.tutelary.remoting.api.ChannelHandler;
import cn.easii.tutelary.remoting.api.Client;
import cn.easii.tutelary.remoting.api.Codec;
import cn.easii.tutelary.remoting.api.EndpointContext;
import cn.easii.tutelary.remoting.api.constants.RemotingResponseCode;
import cn.easii.tutelary.remoting.api.exception.RemotingException;
import java.net.InetSocketAddress;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractClient extends AbstractEndpoint implements Client {

    private static final Log LOG = LogFactory.get();

    private final Lock connectLock = new ReentrantLock();

    public AbstractClient(EndpointContext endpointContext, ChannelHandler channelHandler, Codec codec) {
        super(endpointContext, codec, channelHandler);

        try {
            doOpen();
        } catch (Throwable t) {
            close();
            throw new RemotingException(t, RemotingResponseCode.OPEN_CLIENT_FAILURE,
                getClass().getSimpleName(), NetUtil.getLocalhost(), getRemoteAddress(), t.getMessage());
        }

        try {
            connect();
            LOG.info(
                "Start " + getClass().getSimpleName() + " " + NetUtil.getLocalhostStr() + " connect to the server " +
                getRemoteAddress());
        } catch (Throwable t) {
            close();
            throw new RemotingException(t, RemotingResponseCode.CONNECT_SERVER_FAILURE,
                getClass().getSimpleName(), NetUtil.getLocalhost(), getRemoteAddress(), t.getMessage()
            );
        }
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        Channel channel = getChannel();
        if (channel == null) {
            return new InetSocketAddress(getEndpointContext().getHost(), getEndpointContext().getPort());
        }
        return channel.getRemoteAddress();
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        Channel channel = getChannel();
        if (channel == null) {
            return InetSocketAddress.createUnresolved(NetUtil.getLocalhostStr(), 0);
        }
        return channel.getLocalAddress();
    }

    @Override
    public boolean isConnected() {
        Channel channel = getChannel();
        if (channel == null) {
            return false;
        }
        return channel.isConnected();
    }

    @Override
    public void send(Object message) {
        Channel channel = getChannel();
        if (channel == null || !channel.isConnected()) {
            throw new RemotingException(RemotingResponseCode.CLIENT_SEND_MESSAGE_FAILURE_DUE_TO_CHANNEL_CLOSED,
                getEndpointContext());
        }
        channel.send(message);
    }

    protected void connect() throws RemotingException {
        connectLock.lock();
        try {
            if (isConnected()) {
                return;
            }
            if (isClosed()) {
                LOG.warn(
                    "No need to connect to server " + getRemoteAddress() + " from " + getClass().getSimpleName() + " " +
                    NetUtil.getLocalhostStr() + ", cause: client status is closed");
                return;
            }

            doConnect();

            if (!isConnected()) {
                throw new RemotingException(RemotingResponseCode.RECONNECT_SERVER_FAILURE,
                    getRemoteAddress(), getClass().getSimpleName(), NetUtil.getLocalhostStr(),
                    getEndpointContext().getConnectionTimeout());
            } else {
                LOG.info(
                    "Successed connect to server " + getRemoteAddress() + " from " + getClass().getSimpleName() + " " +
                    NetUtil.getLocalhostStr() + ", channel is " + this.getChannel());
            }
        } catch (RemotingException e) {
            throw e;
        } catch (Throwable e) {
            throw new RemotingException(e, RemotingResponseCode.CONNECT_SERVER_UNCAUGHT_EXCEPTION,
                getRemoteAddress(), getClass().getSimpleName(), e.getMessage());
        } finally {
            connectLock.unlock();
        }
    }

    public void disconnect() {
        connectLock.lock();
        try {
            ThrowableUtil.safeExec(() -> {
                Channel channel = getChannel();
                if (channel != null) {
                    channel.close();
                }
            });
            ThrowableUtil.safeExec(this::doDisConnect);
        } finally {
            connectLock.unlock();
        }
    }

    @Override
    public void reconnect() throws RemotingException {
        connectLock.lock();
        try {
            disconnect();
            connect();
        } finally {
            connectLock.unlock();
        }
    }

    @Override
    public void close() {
        if (isClosed()) {
            LOG.warn(
                "No need to close connection to server " + getRemoteAddress() + " from " + getClass().getSimpleName() +
                NetUtil.getLocalhostStr() + ", cause: the client status is closed.");
            return;
        }
        connectLock.lock();
        try {
            if (isClosed()) {
                LOG.warn("No need to close connection to server " + getRemoteAddress() + " from " +
                         getClass().getSimpleName() + NetUtil.getLocalhostStr() +
                         ", cause: the client status is closed.");
                return;
            }
            ThrowableUtil.safeExec(super::close);
            ThrowableUtil.safeExec(this::disconnect);
            ThrowableUtil.safeExec(this::doClose);
        } finally {
            connectLock.lock();
        }

    }

    @Override
    public String toString() {
        return getClass().getName() + " [" + getLocalAddress() + " -> " + getRemoteAddress() + "]";
    }

    @Override
    public boolean hasAttribute(final String key) {
        Channel channel = getChannel();
        return channel != null && channel.hasAttribute(key);
    }

    @Override
    public Object getAttribute(final String key) {
        Channel channel = getChannel();
        if (channel == null) {
            return null;
        }
        return channel.getAttribute(key);
    }

    @Override
    public void setAttribute(final String key, final Object value) {
        Channel channel = getChannel();
        if (channel == null) {
            return;
        }
        channel.setAttribute(key, value);
    }

    @Override
    public void removeAttribute(final String key) {
        Channel channel = getChannel();
        if (channel == null) {
            return;
        }
        channel.removeAttribute(key);
    }

    protected abstract void doOpen() throws Throwable;

    protected abstract void doConnect() throws Throwable;

    protected abstract void doDisConnect() throws Throwable;

    protected abstract void doClose() throws Throwable;

    protected abstract Channel getChannel();

}
