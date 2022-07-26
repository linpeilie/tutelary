package com.tutelary;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.util.*;

import com.taobao.arthas.core.command.BuiltinCommandPack;
import com.taobao.arthas.core.distribution.impl.PackingResultDistributorImpl;
import com.taobao.arthas.core.server.ArthasBootstrap;
import com.taobao.arthas.core.shell.cli.CliTokens;
import com.taobao.arthas.core.shell.command.Command;
import com.taobao.arthas.core.shell.command.CommandResolver;
import com.taobao.arthas.core.shell.session.Session;
import com.taobao.arthas.core.shell.session.impl.SessionImpl;
import com.taobao.arthas.core.shell.system.ExecStatus;
import com.taobao.arthas.core.shell.system.Job;
import com.taobao.arthas.core.shell.system.JobController;
import com.taobao.arthas.core.shell.system.impl.GlobalJobControllerImpl;
import com.taobao.arthas.core.shell.system.impl.InternalCommandManager;
import com.tutelary.arthas.ApiJobHandler;
import com.tutelary.arthas.ApiTerm;
import com.tutelary.bean.AgentProperties;
import com.tutelary.client.ClientBootstrap;
import com.tutelary.common.config.TutelaryAgentProperties;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

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

            start();

        } catch (Exception e) {
            LOG.error("exception occurred at tutelary client startup", e);
        }
    }

    public static void start() throws IOException {

        // 打印 jvm 信息
        BuiltinCommandPack builtinCommandPack = new BuiltinCommandPack(Collections.emptyList());
        List<CommandResolver> resolvers = new ArrayList<>();
        resolvers.add(builtinCommandPack);
        InternalCommandManager commandManager = new InternalCommandManager(resolvers);

        // session
        Session session = new SessionImpl();

        JobController jobController = new GlobalJobControllerImpl();
        PackingResultDistributorImpl resultDistributor = new PackingResultDistributorImpl(session);

        Job job = jobController.createJob(commandManager, CliTokens.tokenize("jvm"), session,
            new ApiJobHandler(session), new ApiTerm(session), resultDistributor);

        job.run();

        while (!(job.status() == ExecStatus.STOPPED || job.status() == ExecStatus.TERMINATED)) {
            ThreadUtil.sleep(100);
        }

        LOG.info("results : {}", JSONUtil.toJsonPrettyStr(resultDistributor.getResults()));

    }

}
