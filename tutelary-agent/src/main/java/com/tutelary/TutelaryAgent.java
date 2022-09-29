package com.tutelary;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.tutelary.bean.AgentProperties;
import com.tutelary.common.config.TutelaryAgentProperties;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.util.Arrays;

public class TutelaryAgent {

    private static final Log LOG = LogFactory.get();

    private AgentProperties agentProperties;

    public TutelaryAgent(AgentProperties agentProperties) {
        this.agentProperties = agentProperties;
    }

    public static void agentmain(String args, Instrumentation instrumentation) {
        LOG.info("agentmain start...");
        main(args, instrumentation);
    }

    private static synchronized void main(String args, final Instrumentation instrumentation) {
        try {
            LOG.info("agent start");

            TutelaryAgentProperties tutelaryAgentProperties = JSONUtil.toBean(args, TutelaryAgentProperties.class);
            LOG.info("入参 : {}", tutelaryAgentProperties);

            String tutelaryClientJar = tutelaryAgentProperties.getTutelaryWorkspace() + File.separator + "tutelary-client.jar";
            AgentClassLoader agentClassLoader = new AgentClassLoader(tutelaryClientJar, TutelaryAgent.class.getClassLoader());

            Class<?> clientBootstrap = agentClassLoader.loadClass("com.tutelary.client.ClientBootstrap");
            ReflectUtil.invokeStatic(clientBootstrap.getMethod("start", Instrumentation.class, TutelaryAgentProperties.class),
                    instrumentation, tutelaryAgentProperties);

        } catch (Exception e) {
            LOG.error("exception occurred at tutelary client startup", e);
        }
    }
}
