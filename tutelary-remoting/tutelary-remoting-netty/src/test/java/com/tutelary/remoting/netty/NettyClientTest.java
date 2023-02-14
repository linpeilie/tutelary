package com.tutelary.remoting.netty;

import cn.hutool.core.thread.ThreadUtil;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.remoting.ClientSendDataHandler;
import com.tutelary.remoting.LoggingHandler;
import com.tutelary.remoting.api.Client;
import com.tutelary.remoting.api.EndpointContext;
import com.tutelary.remoting.api.Transporter;
import com.tutelary.remoting.api.Transporters;
import java.net.MalformedURLException;
import org.junit.jupiter.api.Test;

public class NettyClientTest {

    private static final Log LOG = LogFactory.get(NettyClientTest.class);

    @Test
    public void testNettyClient() throws MalformedURLException {
        Transporter transporter = new NettyTransporter();
        Transporters transporters = new Transporters(transporter);
        EndpointContext endpointContext = EndpointContext.builder().host("127.0.0.1").port(8099).build();

        Client client = transporters.connect(endpointContext, new LoggingHandler(), new ClientSendDataHandler());

        ThreadUtil.waitForDie();
    }

}
