package com.tutelary;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.taobao.arthas.core.server.ArthasBootstrap;
import com.tutelary.bean.AgentProperties;
import com.tutelary.client.ClientBootstrap;
import com.tutelary.common.config.TutelaryAgentProperties;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TutelaryAgent {

    private static final Log LOG = LogFactory.get();

    private AgentProperties agentProperties;

    public TutelaryAgent(AgentProperties agentProperties) {
        this.agentProperties = agentProperties;
    }

    public static void premain(String args, Instrumentation instrumentation) {
        LOG.info("premain start...");
        main(args, instrumentation);
    }

    public static void agentmain(String args, Instrumentation instrumentation) {
        LOG.info("agentmain start...");
        main(args, instrumentation);
    }

    private static synchronized void main(String args, final Instrumentation instrumentation) {
        try {
            LOG.info("agent start");
            // start ArthasBootstrap
            Method getInstance =
                ReflectUtil.getMethod(ArthasBootstrap.class, "getInstance", Instrumentation.class, Map.class);
            Object bootstrap = ReflectUtil.invokeStatic(getInstance, instrumentation, new HashMap<>());
            Object isBind = ReflectUtil.invoke(bootstrap, "isBind");
            LOG.info("is bind : {}", isBind);

            TutelaryAgentProperties tutelaryAgentProperties = JSONUtil.toBean(args, TutelaryAgentProperties.class);
            LOG.info("入参 : {}", tutelaryAgentProperties);

            ClientBootstrap.start(instrumentation, tutelaryAgentProperties);
        } catch (Exception e) {
            LOG.error("exception occurred at tutelary client startup", e);
        }
    }
}
