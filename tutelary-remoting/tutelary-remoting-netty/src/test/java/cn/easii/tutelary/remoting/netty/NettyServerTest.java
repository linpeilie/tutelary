package cn.easii.tutelary.remoting.netty;

import cn.hutool.core.thread.ThreadUtil;
import cn.easii.tutelary.remoting.LoggingHandler;
import cn.easii.tutelary.remoting.api.EndpointContext;
import cn.easii.tutelary.remoting.api.RemotingServer;
import cn.easii.tutelary.remoting.api.Transporter;
import cn.easii.tutelary.remoting.api.Transporters;
import org.junit.jupiter.api.Test;

public class NettyServerTest {

    @Test
    public void testNettyServerOpen() {
        Transporter transporter = new NettyTransporter();
        Transporters transporters = new Transporters(transporter);
        EndpointContext endpointContext = EndpointContext.builder().host("0.0.0.0").port(8099).build();
        RemotingServer server = transporters.bind(endpointContext, new ReplyServerHandler(), new LoggingHandler());

        ThreadUtil.waitForDie();
    }

}
