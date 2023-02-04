package com.tutelary.remoting;

import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.remoting.api.Channel;
import com.tutelary.remoting.api.ChannelHandler;
import com.tutelary.remoting.api.exception.RemotingException;

public class LoggingHandler implements ChannelHandler {

    private static final Log LOG = LogFactory.get(LoggingHandler.class);

    @Override
    public void connected(Channel channel) throws RemotingException {
        LOG.info("The channel {} connected", channel);
    }

    @Override
    public void disconnected(Channel channel) throws RemotingException {
        LOG.info("The channel {} disconnected", channel);
    }

    @Override
    public void send(Channel channel, Object message) throws RemotingException {
        LOG.info("The channel {} send message : {}", channel, message);
    }

    @Override
    public void received(Channel channel, Object message) throws RemotingException {
        LOG.info("The channel {} received message : {}", channel, message);
    }

    @Override
    public void caught(Channel channel, Throwable throwable) throws RemotingException {
        LOG.error("The channel {} caught exception : {}", channel, throwable.getMessage());
    }
}
