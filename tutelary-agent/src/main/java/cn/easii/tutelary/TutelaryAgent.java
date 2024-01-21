package cn.easii.tutelary;

import cn.easii.tutelary.common.config.TutelaryAgentProperties;
import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.dialect.console.ConsoleLog;
import cn.hutool.json.JSONUtil;
import cn.easii.tutelary.bean.AgentProperties;
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

            Class<?> clientBootstrap = agentClassLoader.loadClass("cn.easii.tutelary.client.ClientBootstrap");

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
