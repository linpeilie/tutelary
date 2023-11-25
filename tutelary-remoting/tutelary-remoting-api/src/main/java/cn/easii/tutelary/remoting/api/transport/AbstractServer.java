package cn.easii.tutelary.remoting.api.transport;

import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.easii.tutelary.common.utils.ThrowableUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;
import cn.easii.tutelary.remoting.api.Channel;
import cn.easii.tutelary.remoting.api.ChannelHandler;
import cn.easii.tutelary.remoting.api.Codec;
import cn.easii.tutelary.remoting.api.EndpointContext;
import cn.easii.tutelary.remoting.api.RemotingServer;
import cn.easii.tutelary.remoting.api.bean.SpecifiedClientMessage;
import cn.easii.tutelary.remoting.api.constants.RemotingResponseCode;
import cn.easii.tutelary.remoting.api.exception.RemotingException;
import java.net.InetSocketAddress;

public abstract class AbstractServer extends AbstractEndpoint implements RemotingServer {

    private static final Log LOG = LogFactory.get();

    private final InetSocketAddress bindAddress;

    private InetSocketAddress localAddress;

    public AbstractServer(EndpointContext endpointContext, ChannelHandler channelHandler, Codec codec) {
        super(endpointContext, codec, channelHandler);
        localAddress = new InetSocketAddress(endpointContext.getHost(), endpointContext.getPort());
        // bind ip and port
        bindAddress = new InetSocketAddress(endpointContext.getHost(), endpointContext.getPort());
        try {
            doOpen();
            LOG.info("Start " + getClass().getSimpleName() + " bind " + getBindAddress());
        } catch (Throwable t) {
            throw new RemotingException(t, RemotingResponseCode.SERVER_OPEN_UNCAUGHT_EXCEPTION,
                getClass().getSimpleName(), getBindAddress(), t.getMessage());
        }
    }

    public InetSocketAddress getBindAddress() {
        return this.bindAddress;
    }

    @Override
    public void send(Object message) {
        String clientAddress = null;
        int clientPort = -1;
        if (message instanceof SpecifiedClientMessage) {
            clientAddress = ((SpecifiedClientMessage) message).getClientAddress();
            clientPort = ((SpecifiedClientMessage) message).getClientPort();
        }
        if (StrUtil.isBlank(clientAddress) || clientPort < 0) {
            throw new IllegalArgumentException("RemotingServer Cannot send message, cause: client address is empty");
        }
        Channel channel = getChannel(NetUtil.createAddress(clientAddress, clientPort));
        channel.send(message);
    }

    @Override
    public void close() {
        LOG.info("Close " + getClass().getSimpleName() + " bind " + getBindAddress());
        ThrowableUtil.safeExec(super::close);
        ThrowableUtil.safeExec(this::doClose);
    }

    @Override
    public void connected(Channel channel) throws RemotingException {
        if (this.isClosed()) {
            LOG.warn("Close new channel " + channel + ", cause: server is closed.");
            channel.close();
            return;
        }
        super.connected(channel);
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return localAddress;
    }

    protected abstract void doOpen() throws Throwable;

    protected abstract void doClose() throws Throwable;

}
