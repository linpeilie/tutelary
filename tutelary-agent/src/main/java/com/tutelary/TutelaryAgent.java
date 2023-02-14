package com.tutelary;

import cn.hutool.json.JSONUtil;
import com.tutelary.bean.AgentProperties;
import com.tutelary.common.config.TutelaryAgentProperties;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.dialect.console.ConsoleLog;
import java.io.File;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;

public class TutelaryAgent {

    private static final Log LOG = new ConsoleLog(TutelaryAgent.class);

    private AgentProperties agentProperties;

    public TutelaryAgent(AgentProperties agentProperties) {
        this.agentProperties = agentProperties;
    }

    public static void agentmain(String args, Instrumentation instrumentation) {
        main(args, instrumentation);
    }

    private static synchronized void main(String args, final Instrumentation instrumentation) {
        try {
            TutelaryAgentProperties tutelaryAgentProperties = JSONUtil.toBean(args, TutelaryAgentProperties.class);

            String tutelaryClientJar =
                tutelaryAgentProperties.getTutelaryWorkspace() + File.separator + "tutelary-client.jar";
            AgentClassLoader agentClassLoader = new AgentClassLoader(
                tutelaryClientJar, Thread.currentThread().getContextClassLoader());

            Class<?> clientBootstrap = agentClassLoader.loadClass("com.tutelary.client.ClientBootstrap");

            Method start = clientBootstrap.getMethod("start", Instrumentation.class,
                agentClassLoader.loadClass(TutelaryAgentProperties.class.getName())
            );
            LOG.info(
                "tutelaryAgentProperties : {}, classloader : {}", tutelaryAgentProperties,
                tutelaryAgentProperties.getClass().getClassLoader()
            );
            Thread.currentThread().setContextClassLoader(agentClassLoader);
            start.invoke(
                null, instrumentation,
                JSONUtil.toBean(args, agentClassLoader.loadClass(TutelaryAgentProperties.class.getName()))
            );
        } catch (Exception e) {
            LOG.error("start tutelary agent exception", e);
        }
    }
}
