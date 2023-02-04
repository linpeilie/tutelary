package com.tutelary.remoting.api.transport;

import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.remoting.api.ChannelHandler;
import com.tutelary.remoting.api.Codec;
import com.tutelary.remoting.api.EndpointContext;

public abstract class AbstractEndpoint extends AbstractPeer {

    private static final Log LOG = LogFactory.get();

    private final Codec codec;

    public AbstractEndpoint(EndpointContext endpointContext, Codec codec, ChannelHandler channelHandler) {
        super(endpointContext, channelHandler);
        this.codec = codec;
    }

    public Codec getCodec() {
        return codec;
    }
}
