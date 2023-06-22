package com.tutelary.remoting.api.transport;

import com.tutelary.remoting.api.Channel;
import com.tutelary.remoting.api.ChannelHandler;
import com.tutelary.remoting.api.EndpointContext;
import com.tutelary.remoting.api.constants.RemotingResponseCode;
import com.tutelary.remoting.api.exception.RemotingException;

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
