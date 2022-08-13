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

    public static boolean registered = false;

    private static MessageProcessorManager MESSAGE_PROCESSOR_MANAGER = new MessageProcessorManager();

    public static void start(Instrumentation instrumentation, TutelaryAgentProperties tutelaryAgentProperties)
            throws URISyntaxException, InterruptedException {
        INSTRUMENTATION = instrumentation;
        TUTELARY_AGENT_PROPERTIES = tutelaryAgentProperties;

        loadService();

        loadMessageProcessor();

        loadCommandHandler();

        startClient();

        connect();

        Runtime.getRuntime().addShutdownHook(new Thread(ClientBootstrap::destroy));
    }

    private static void loadMessageProcessor() {
        List<MessageProcessor> messageProcessors = ServiceLoaderUtil.loadList(MessageProcessor.class);
        messageProcessors.forEach(MESSAGE_PROCESSOR_MANAGER::register);
    }

    private static void loadCommandHandler() {
    }

    private static void startClient() {
        client = new TutelaryClient(MESSAGE_PROCESSOR_MANAGER);
    }

    private static void loadService() {
        services = ServiceLoaderUtil.loadList(ClientService.class);
    }

    public static void connect() {
        client.connect();
    }

    public static void destroy() {
        client.destroy();
    }

}
