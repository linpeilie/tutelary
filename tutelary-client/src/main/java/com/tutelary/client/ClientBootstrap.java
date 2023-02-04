package com.tutelary.client;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.security.CodeSource;
import java.tutelary.WeaveSpy;
import java.util.List;
import java.util.jar.JarFile;

import com.alibaba.arthas.deps.ch.qos.logback.classic.LoggerContext;
import com.tutelary.client.enhance.spy.EnhancedSpy;
import com.tutelary.client.loader.ClassLoaderWrapper;
import com.tutelary.client.util.LogUtil;
import com.tutelary.common.config.TutelaryAgentProperties;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.common.log.dialect.console.ConsoleLog;
import com.tutelary.processor.MessageProcessor;
import com.tutelary.processor.MessageProcessorManager;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ServiceLoaderUtil;
import com.tutelary.remoting.api.ChannelHandler;

public class ClientBootstrap {

    private static Log LOGGER = new ConsoleLog(ClientBootstrap.class);

    public static Instrumentation INSTRUMENTATION;

    public static TutelaryAgentProperties TUTELARY_AGENT_PROPERTIES;

    public static TutelaryClient client;

    public volatile static String instanceId;

    public static volatile boolean registered = false;

    private static LoggerContext loggerContext;

    private static List<ChannelHandler> channelHandlers;

    public static void start(Instrumentation instrumentation, TutelaryAgentProperties tutelaryAgentProperties)
        throws Exception {
        INSTRUMENTATION = instrumentation;
        TUTELARY_AGENT_PROPERTIES = tutelaryAgentProperties;

        // init logger
        initLog(tutelaryAgentProperties);

        instanceId = UUID.randomUUID().toString(true);

        loadMessageProcessor();

        loadChannelHandler();

        installSyp();

        startClient();

        Runtime.getRuntime().addShutdownHook(new Thread(ClientBootstrap::destroy));
    }

    private static void initLog(TutelaryAgentProperties properties) {
        loggerContext = LogUtil.initLogger(properties.getTutelaryWorkspace());
        if (loggerContext != null) {
            LOGGER = LogFactory.get(ClientBootstrap.class);
        }
    }

    private static void installSyp() throws Exception {
        // 将 spy 添加到 BootstrapClassLoader
        ClassLoader parent = ClassLoader.getSystemClassLoader().getParent();
        Class<?> spyClass = null;
        if (parent != null) {
            try {
                spyClass = parent.loadClass("java.tutelary.WeaveSpy");
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

    private static void loadMessageProcessor() {
        List<MessageProcessor> messageProcessors =
            ServiceLoaderUtil.loadList(MessageProcessor.class, ClassLoaderWrapper.getAgentClassLoader());
        messageProcessors.forEach(MessageProcessorManager::register);
    }

    private static void loadChannelHandler() {
        channelHandlers = ServiceLoaderUtil.loadList(ChannelHandler.class, ClassLoaderWrapper.getAgentClassLoader());
    }

    private static void startClient() {
        client = new TutelaryClient(channelHandlers);
    }

    public static void reconnect() {
        client.reconnect();
    }

    public static void destroy() {
        LOGGER.info("Tutelary Agent Start Destroy");
        client.destroy();
        LOGGER.info("Tutelary Agent Destroyed");
    }

    public static void sendData(Object message) {
        if (client != null) {
            client.sendData(message);
        }
    }

}
