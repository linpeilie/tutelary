package cn.easii.tutelary.remoting.netty;

import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.hutool.core.thread.ThreadUtil;
import cn.easii.tutelary.remoting.ClientSendDataHandler;
import cn.easii.tutelary.remoting.LoggingHandler;
import cn.easii.tutelary.remoting.api.Client;
import cn.easii.tutelary.remoting.api.EndpointContext;
import cn.easii.tutelary.remoting.api.Transporter;
import cn.easii.tutelary.remoting.api.Transporters;
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
