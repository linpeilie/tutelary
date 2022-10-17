package com.tutelary.client.processor;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.baidu.bjf.remoting.protobuf.Any;
import com.taobao.arthas.core.command.BuiltinCommandPack;
import com.taobao.arthas.core.shell.cli.CliTokens;
import com.taobao.arthas.core.shell.command.CommandResolver;
import com.taobao.arthas.core.shell.session.Session;
import com.taobao.arthas.core.shell.session.impl.SessionImpl;
import com.taobao.arthas.core.shell.system.Job;
import com.taobao.arthas.core.shell.system.JobController;
import com.taobao.arthas.core.shell.system.impl.GlobalJobControllerImpl;
import com.taobao.arthas.core.shell.system.impl.InternalCommandManager;
import com.tutelary.client.arthas.distribution.AbstractResultDistributor;
import com.tutelary.client.arthas.distribution.factory.ResultDistributionFactory;
import com.tutelary.client.arthas.listener.ArthasJobListener;
import com.tutelary.client.arthas.term.TutelaryApiTerm;
import com.tutelary.common.CommandResult;
import com.tutelary.constants.CommandTypeConstants;
import com.tutelary.message.ClientCommandRequestMessage;
import com.tutelary.message.ClientCommandResponseMessage;
import com.tutelary.processor.AbstractMessageProcessor;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.util.Collections;

public class ClientCommandProcessor extends AbstractMessageProcessor<ClientCommandRequestMessage> {

    private static final Log LOG = LogFactory.get();

    private static final int COMMAND_TIMEOUT = 1000;

    private final InternalCommandManager commandManager;

    private final ResultDistributionFactory resultDistributionFactory;

    public ClientCommandProcessor() {
        resultDistributionFactory = new ResultDistributionFactory();
        CommandResolver commandPack = new BuiltinCommandPack(Collections.emptyList());
        commandManager = new InternalCommandManager(ListUtil.toList(commandPack));
    }

    @Override
    protected void process(ChannelHandlerContext ctx, ClientCommandRequestMessage message) {
        ClientCommandResponseMessage clientCommandResponseMessage = new ClientCommandResponseMessage();
        clientCommandResponseMessage.setCommand(message.getCommand());
        clientCommandResponseMessage.setCommandType(message.getCommandType());
        clientCommandResponseMessage.setCommandCode(message.getCommandCode());
        clientCommandResponseMessage.setSessionId(message.getSessionId());

        if (CommandTypeConstants.ARTHAS.equals(message.getCommandType())) {
            CommandResult commandResult = execArthasCommand(message.getSessionId(), message.getCommandCode(), message.getCommand());
            try {
                clientCommandResponseMessage.setData(Any.pack(commandResult));
                clientCommandResponseMessage.setStatus(Boolean.TRUE);
            } catch (IOException e) {
                LOG.error("ClientCommandProcessor Any.pack error", e);
                clientCommandResponseMessage.setStatus(Boolean.FALSE);
                clientCommandResponseMessage.setMessage("系统异常[" + e.getMessage() + "]");
            }
        }
        ctx.writeAndFlush(clientCommandResponseMessage);
    }

    private CommandResult execArthasCommand(String sessionId, Integer commandCode, String command) {
        // session
        SessionImpl session = new SessionImpl();
        session.put(Session.ID, sessionId);

        // job handler
        JobController jobController = new GlobalJobControllerImpl();

        // result distributor
        AbstractResultDistributor resultDistributor = resultDistributionFactory.getResultDistributor(commandCode);

        Job job = jobController.createJob(commandManager, CliTokens.tokenize(command), session,
                                          new ArthasJobListener(session), new TutelaryApiTerm(session),
                                          resultDistributor);

        job.run();

        boolean timeExpired = !waitForJob(job, COMMAND_TIMEOUT);
        if (timeExpired) {
            LOG.warn("Job is exceeded time limit, force interrupt it, command : {}", command);
            job.interrupt();
        }

        return resultDistributor.getResult();
    }

    private boolean waitForJob(Job job, int timeout) {
        long startTime = System.currentTimeMillis();
        while (true) {
            switch (job.status()) {
                case STOPPED:
                case TERMINATED:
                    return true;
            }
            if (System.currentTimeMillis() - startTime > timeout) {
                return false;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public Class<ClientCommandRequestMessage> getCmdClass() {
        return ClientCommandRequestMessage.class;
    }
}
