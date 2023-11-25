package cn.easii.tutelary.remoting;

import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.easii.tutelary.remoting.api.Channel;
import cn.easii.tutelary.remoting.api.ChannelHandler;
import cn.easii.tutelary.remoting.api.exception.RemotingException;

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
