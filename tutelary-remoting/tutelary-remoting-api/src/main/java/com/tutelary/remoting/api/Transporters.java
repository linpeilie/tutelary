package com.tutelary.remoting.api;

import com.tutelary.common.utils.Asserts;
import com.tutelary.remoting.api.exception.RemotingException;
import com.tutelary.remoting.api.transport.ChannelHandlerDispatcher;

public class Transporters {

    private final Transporter transporter;

    public Transporters(Transporter transporter) {
        this.transporter = transporter;
    }

    public RemotingServer bind(EndpointContext endpointContext, ChannelHandler... handlers) throws RemotingException {
        Asserts.notEmpty(handlers, "handlers is empty");
        ChannelHandler handler = handlers.length == 1 ? handlers[0] : new ChannelHandlerDispatcher(handlers);
        return transporter.bind(endpointContext, handler);
    }

    public Client connect(EndpointContext endpointContext, ChannelHandler... handlers) throws RemotingException {
        Asserts.notNull(endpointContext, "endpointContext is null");
        Asserts.notEmpty(handlers, "handlers is empty");
        ChannelHandler handler = handlers.length == 1 ? handlers[0] : new ChannelHandlerDispatcher(handlers);
        return transporter.connect(endpointContext, handler);
    }

}
