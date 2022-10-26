package com.tutelary.client.processor;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.tutelary.message.ClientCommandRequestMessage;
import com.tutelary.message.ClientCommandResponseMessage;
import com.tutelary.processor.AbstractMessageProcessor;
import io.netty.channel.ChannelHandlerContext;

public class ClientCommandProcessor extends AbstractMessageProcessor<ClientCommandRequestMessage> {

    private static final Log LOG = LogFactory.get();

    private static final int COMMAND_TIMEOUT = 1000;

    public ClientCommandProcessor() {
    }

    @Override
    protected void process(ChannelHandlerContext ctx, ClientCommandRequestMessage message) {
        ClientCommandResponseMessage clientCommandResponseMessage = new ClientCommandResponseMessage();
        clientCommandResponseMessage.setCommand(message.getCommand());
        clientCommandResponseMessage.setCommandType(message.getCommandType());
        clientCommandResponseMessage.setCommandCode(message.getCommandCode());
        clientCommandResponseMessage.setSessionId(message.getSessionId());

//        if (CommandTypeConstants.ARTHAS.equals(message.getCommandType())) {
//            try {
//                clientCommandResponseMessage.setData(Any.pack(commandResult));
//                clientCommandResponseMessage.setStatus(Boolean.TRUE);
//            } catch (IOException e) {
//                LOG.error("ClientCommandProcessor Any.pack error", e);
//                clientCommandResponseMessage.setStatus(Boolean.FALSE);
//                clientCommandResponseMessage.setMessage("系统异常[" + e.getMessage() + "]");
//            }
//        }
        ctx.writeAndFlush(clientCommandResponseMessage);
    }

    @Override
    public Class<ClientCommandRequestMessage> getCmdClass() {
        return ClientCommandRequestMessage.class;
    }
}
