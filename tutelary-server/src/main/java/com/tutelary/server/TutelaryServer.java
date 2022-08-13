package com.tutelary.server;

import com.tutelary.common.BaseMessage;
import com.tutelary.processor.MessageProcessor;
import com.tutelary.processor.MessageProcessorManager;
import com.tutelary.server.properties.ServerEndpointConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TutelaryServer extends AbstractServer {

    public TutelaryServer(ServerEndpointConfig config, MessageProcessorManager messageProcessorManager) {
        super(config, new TutelaryServerChannelInitializer(config.getPath(), messageProcessorManager));
    }

    @Override
    protected int getBossLoopGroupThreads() {
        return 0;
    }

    @Override
    protected int getWorkerLoopGroupThreads() {
        return 0;
    }

    @Override
    protected int getPort() {
        return 0;
    }
}
