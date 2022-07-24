package com.tutelary.client;

import java.lang.instrument.Instrumentation;
import java.net.URISyntaxException;
import java.util.List;

import com.tutelary.client.service.ClientService;
import com.tutelary.common.config.TutelaryAgentProperties;

import cn.hutool.core.util.ServiceLoaderUtil;
import com.tutelary.processor.MessageProcessor;
import com.tutelary.processor.MessageProcessorManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

public class ClientBootstrap {

    public static Instrumentation INSTRUMENTATION;

    public static TutelaryAgentProperties TUTELARY_AGENT_PROPERTIES;

    private static List<ClientService> services;

    public static TutelaryClient client;

    public volatile static String instanceId;

    public static Channel channel;
    public static boolean registered = false;

    public static void start(Instrumentation instrumentation, TutelaryAgentProperties tutelaryAgentProperties)
            throws URISyntaxException, InterruptedException {
        INSTRUMENTATION = instrumentation;
        TUTELARY_AGENT_PROPERTIES = tutelaryAgentProperties;

        loadService();

        loadMessageProcessor();

        loadCommandHandler();

        startClient();
    }

    private static void loadMessageProcessor() {
        List<MessageProcessor> messageProcessors = ServiceLoaderUtil.loadList(MessageProcessor.class);
        messageProcessors.forEach(MessageProcessorManager::register);
    }

    private static void loadCommandHandler() {
    }

    private static void startClient() throws URISyntaxException, InterruptedException {
        client = new TutelaryClient();
        channel = client.start();
    }

    private static void loadService() {
        services = ServiceLoaderUtil.loadList(ClientService.class);
    }

}
