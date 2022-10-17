package com.tutelary.client;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.tutelary.Spy;
import com.tutelary.WeaveSpy;
import com.tutelary.client.enhance.Enhance;
import com.tutelary.client.enhance.spy.EnhancedSpy;
import com.tutelary.client.listener.ClientLifeCycleListener;
import com.tutelary.client.loader.ClassLoaderWrapper;
import com.tutelary.client.processor.ClientCommandProcessor;
import com.tutelary.client.processor.ClientRegisterResponseProcessor;
import com.tutelary.common.config.TutelaryAgentProperties;
import com.tutelary.event.ChannelEventListener;
import com.tutelary.event.ChannelEvents;
import com.tutelary.processor.MessageProcessor;
import com.tutelary.processor.MessageProcessorManager;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import java.util.prefs.BackingStoreException;

public class ClientBootstrap {

    private static final Log LOG = LogFactory.get();

    public static Instrumentation INSTRUMENTATION;

    public static TutelaryAgentProperties TUTELARY_AGENT_PROPERTIES;

    public static TutelaryClient client;

    public volatile static String instanceId;

    public static boolean registered = false;

    private static MessageProcessorManager MESSAGE_PROCESSOR_MANAGER = new MessageProcessorManager();

    public static ChannelEvents channelEvents;

    public static void start(Instrumentation instrumentation, TutelaryAgentProperties tutelaryAgentProperties) throws Exception {
        INSTRUMENTATION = instrumentation;
        TUTELARY_AGENT_PROPERTIES = tutelaryAgentProperties;

        instanceId = UUID.randomUUID().toString(true);

        loadMessageProcessor();

        loadCommandHandler();

        loadChannelEventListeners();

        installSyp();

        Enhance.enhance(instrumentation, tutelaryAgentProperties.getApplicationBasePackage());

        startClient();

        connect();

        Runtime.getRuntime().addShutdownHook(new Thread(ClientBootstrap::destroy));
    }

    private static void installSyp() throws Exception {
        // 将 spy 添加到 BootstrapClassLoader
        ClassLoader parent = ClassLoader.getSystemClassLoader().getParent();
        Class<?> spyClass = null;
        if (parent != null) {
            try {
                spyClass = parent.loadClass("com.tutelary.WeaveSpy");
            } catch (Throwable e) {
            }
        }
        if (spyClass == null) {
            CodeSource codeSource = ClientBootstrap.class.getProtectionDomain().getCodeSource();
            if (codeSource != null) {
                File tutelaryClientJarFile = new File(codeSource.getLocation().toURI().getSchemeSpecificPart());
                File spyJarFile = new File(tutelaryClientJarFile.getParentFile(), "tutelary-spy.jar");
                INSTRUMENTATION.appendToBootstrapClassLoaderSearch(new JarFile(spyJarFile));
            } else {
                throw new IllegalAccessException("can not find tutelary-spy.jar");
            }
        }
        WeaveSpy.installSpy(new EnhancedSpy());
    }

    private static void loadChannelEventListeners() {
        List<ChannelEventListener> channelEventListeners = new ArrayList<>();
        channelEventListeners.add(new ClientLifeCycleListener());
        channelEvents = new ChannelEvents(channelEventListeners);
    }

    private static void loadMessageProcessor() {
        List<MessageProcessor> messageProcessors = new ArrayList<>();
        messageProcessors.add(new ClientCommandProcessor());
        messageProcessors.add(new ClientRegisterResponseProcessor());
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
        LOG.info("Tutelary Agent Start Destroy");
        client.destroy();
        LOG.info("Tutelary Agent Destroyed");
    }

}
