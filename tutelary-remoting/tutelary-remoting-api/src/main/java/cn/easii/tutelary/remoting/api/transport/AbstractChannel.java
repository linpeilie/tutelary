package cn.easii.tutelary.remoting.api.transport;

import cn.easii.tutelary.remoting.api.Channel;
import cn.easii.tutelary.remoting.api.ChannelHandler;
import cn.easii.tutelary.remoting.api.EndpointContext;
import cn.easii.tutelary.remoting.api.constants.RemotingResponseCode;
import cn.easii.tutelary.remoting.api.exception.RemotingException;

public abstract class AbstractChannel extends AbstractPeer implements Channel {

    public AbstractChannel(EndpointContext endpointContext, ChannelHandler channelHandler) {
        super(endpointContext, channelHandler);
    }

    @Override
    public void send(Object message) throws RemotingException {
        if (isClosed()) {
            throw new RemotingException(RemotingResponseCode.SEND_FAILURE_DUE_TO_CHANNEL_CLOSED,
                getLocalAddress(), getRemoteAddress());
        }
    }

    @Override
    public String toString() {
        return getLocalAddress() + " -> " + getRemoteAddress();
    }
}
