package com.tutelary.client.service;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.tutelary.client.ClientBootstrap;
import com.tutelary.message.ClientRegisterRequestMessage;

public class RegisterService implements ClientService, Runnable {

    private static final Log LOG = LogFactory.get();

    private volatile ScheduledExecutorService scheduledExecutorService;

    private volatile boolean registed = false;

    @Override
    public void start() {
        LOG.info("RegisterService start()");
        scheduledExecutorService = new ScheduledThreadPoolExecutor(1, ThreadFactoryBuilder.create().build());
        scheduledExecutorService.scheduleAtFixedRate(this, 0, 10L, TimeUnit.SECONDS);
    }

    @Override
    public void stop() {
        scheduledExecutorService.shutdown();
    }

    @Override
    public void run() {
        if (!registed) {
            ClientRegisterRequestMessage clientRegisterRequestMessage = new ClientRegisterRequestMessage();
            clientRegisterRequestMessage.setAppName(ClientBootstrap.TUTELARY_AGENT_PROPERTIES.getAppName());
            ClientBootstrap.channel.writeAndFlush(clientRegisterRequestMessage);
        }
    }
}
