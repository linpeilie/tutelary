package com.tutelary.remoting.netty;

import cn.hutool.core.thread.ThreadUtil;
import com.tutelary.remoting.LoggingHandler;
import com.tutelary.remoting.api.EndpointContext;
import com.tutelary.remoting.api.RemotingServer;
import com.tutelary.remoting.api.Transporter;
import com.tutelary.remoting.api.Transporters;
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
