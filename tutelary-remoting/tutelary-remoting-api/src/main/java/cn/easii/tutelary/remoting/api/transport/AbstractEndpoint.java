package cn.easii.tutelary.remoting.api.transport;

import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.easii.tutelary.remoting.api.ChannelHandler;
import cn.easii.tutelary.remoting.api.Codec;
import cn.easii.tutelary.remoting.api.EndpointContext;

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
