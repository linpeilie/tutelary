package com.tutelary.client.processor;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.taobao.arthas.core.distribution.ResultDistributor;
import com.taobao.arthas.core.shell.cli.CliTokens;
import com.taobao.arthas.core.shell.session.Session;
import com.taobao.arthas.core.shell.session.impl.SessionImpl;
import com.taobao.arthas.core.shell.system.ExecStatus;
import com.taobao.arthas.core.shell.system.Job;
import com.taobao.arthas.core.shell.system.JobController;
import com.taobao.arthas.core.shell.system.impl.GlobalJobControllerImpl;
import com.taobao.arthas.core.shell.system.impl.InternalCommandManager;
import com.tutelary.client.arthas.distribution.AbstractResultDistributor;
import com.tutelary.client.arthas.distribution.JvmResultDistributor;
import com.tutelary.client.arthas.distribution.ResultDistributionFactory;
import com.tutelary.client.arthas.listener.ArthasJobListener;
import com.tutelary.client.arthas.term.TutelaryApiTerm;
import com.tutelary.client.handler.command.ArthasCommandPack;
import com.tutelary.common.CommandResult;
import com.tutelary.message.ClientCommandRequestMessage;
import com.tutelary.message.ClientCommandResponseMessage;
import com.tutelary.processor.AbstractMessageProcessor;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientCommandProcessor extends AbstractMessageProcessor<ClientCommandRequestMessage> {

    private final InternalCommandManager commandManager;

    private final ResultDistributionFactory resultDistributionFactory;

    public ClientCommandProcessor() {
        resultDistributionFactory = new ResultDistributionFactory();
        ArthasCommandPack commandPack = new ArthasCommandPack();
        commandManager = new InternalCommandManager(ListUtil.toList(commandPack));
    }

    @Override
    protected void process(ChannelHandlerContext ctx, ClientCommandRequestMessage message) {
        ClientCommandResponseMessage clientCommandResponseMessage = new ClientCommandResponseMessage();
        clientCommandResponseMessage.setCommand(message.getCommand());
        clientCommandResponseMessage.setCommandType(message.getCommandType());
        clientCommandResponseMessage.setSessionId(message.getSessionId());
        clientCommandResponseMessage.setStatus(Boolean.TRUE);
        if (message.getCommandType().equals("arths")) {
            CommandResult commandResult = execArthasCommand(message.getSessionId(), message.getCommand());
            clientCommandResponseMessage.setData(commandResult);
        }
        ctx.writeAndFlush(clientCommandResponseMessage);
    }

    private CommandResult execArthasCommand(String sessionId, String command) {
        // session
        SessionImpl session = new SessionImpl();
        session.put(Session.ID, sessionId);

        // job handler
        JobController jobController = new GlobalJobControllerImpl();

        // result distributor
        AbstractResultDistributor resultDistributor = resultDistributionFactory.getResultDistributor(command);

        Job job = jobController.createJob(commandManager,
                CliTokens.tokenize(command),
                session,
                new ArthasJobListener(session),
                new TutelaryApiTerm(session),
                resultDistributor);

        job.run();

        while (!(job.status() == ExecStatus.STOPPED || job.status() == ExecStatus.TERMINATED)) {
            ThreadUtil.sleep(100);
        }

        return resultDistributor.getResult();
    }

    @Override
    public Class<ClientCommandRequestMessage> getCmdClass() {
        return ClientCommandRequestMessage.class;
    }
}
