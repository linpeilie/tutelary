package com.tutelary.server;

import com.tutelary.processor.MessageProcessorManager;
import com.tutelary.server.properties.ServerEndpointConfig;

public class TutelaryWebServer extends AbstractServer {

    public TutelaryWebServer(ServerEndpointConfig config, MessageProcessorManager messageProcessorManager) {
        this(config, new TutelaryWebServerChannelInitializer(config.getWebPath(), messageProcessorManager));
    }

    public TutelaryWebServer(ServerEndpointConfig config, TutelaryWebServerChannelInitializer channelInitializer) {
        super(config, channelInitializer);
    }


    @Override
    protected int getBossLoopGroupThreads() {
        return config.getWebBossLoopGroupThreads();
    }

    @Override
    protected int getWorkerLoopGroupThreads() {
        return config.getWebWorkerLoopGroupThreads();
    }

    @Override
    protected int getPort() {
        return config.getWebPort();
    }
}
