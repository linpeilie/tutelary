package com.tutelary.client;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ServiceLoaderUtil;
import com.tutelary.common.config.TutelaryAgentProperties;
import com.tutelary.event.ChannelEventListener;
import com.tutelary.event.ChannelEvents;
import com.tutelary.processor.MessageProcessor;
import com.tutelary.processor.MessageProcessorManager;

import java.lang.instrument.Instrumentation;
import java.net.URISyntaxException;
import java.util.List;

public class ClientBootstrap {

    public static Instrumentation INSTRUMENTATION;

    public static TutelaryAgentProperties TUTELARY_AGENT_PROPERTIES;

    public static TutelaryClient client;

    public volatile static String instanceId;

    public static boolean registered = false;

    private static MessageProcessorManager MESSAGE_PROCESSOR_MANAGER = new MessageProcessorManager();

    public static ChannelEvents channelEvents;

    public static void start(Instrumentation instrumentation, TutelaryAgentProperties tutelaryAgentProperties) throws URISyntaxException, InterruptedException {
        Class[] initiatedClasses = instrumentation.getInitiatedClasses(ClientBootstrap.class.getClassLoader().getParent());
//        Class[] allLoadedClasses = instrumentation.getAllLoadedClasses();
        for (Class allLoadedClass : initiatedClasses) {
            System.out.println(allLoadedClass.getName());
        }
//        INSTRUMENTATION = instrumentation;
//        TUTELARY_AGENT_PROPERTIES = tutelaryAgentProperties;
//
//        instanceId = UUID.randomUUID().toString(true);
//
//        loadMessageProcessor();
//
//        loadCommandHandler();
//
//        loadChannelEventListeners();
//
//        startClient();
//
//        connect();
//
//        Runtime.getRuntime().addShutdownHook(new Thread(ClientBootstrap::destroy));
    }

    private static void loadChannelEventListeners() {
        List<ChannelEventListener> channelEventListeners = ServiceLoaderUtil.loadList(ChannelEventListener.class);
        channelEvents = new ChannelEvents(channelEventListeners);
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

    public static void connect() {
        client.connect();
    }

    public static void destroy() {
        client.destroy();
    }

}
