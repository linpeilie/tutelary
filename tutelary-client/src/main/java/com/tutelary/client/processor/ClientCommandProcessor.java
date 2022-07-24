package com.tutelary.client.processor;

import com.tutelary.message.ClientCommandRequestMessage;
import com.tutelary.message.ClientCommandResponseMessage;
import com.tutelary.processor.AbstractMessageProcessor;
import io.netty.channel.ChannelHandlerContext;

public class ClientCommandProcessor extends AbstractMessageProcessor<ClientCommandRequestMessage> {
    @Override
    protected void process(ChannelHandlerContext ctx, ClientCommandRequestMessage message) {
        ClientCommandResponseMessage clientCommandResponseMessage = new ClientCommandResponseMessage();
        clientCommandResponseMessage.setCommand(message.getCommand());
        clientCommandResponseMessage.setCommandType(message.getCommandType());
        clientCommandResponseMessage.setSessionId(message.getSessionId());
        clientCommandResponseMessage.setStatus(Boolean.FALSE);
        clientCommandResponseMessage.setMessage("Unknown command " + message.getCommandType() + " " + message.getCommand());
        ctx.writeAndFlush(clientCommandResponseMessage);
    }

    @Override
    public Class<ClientCommandRequestMessage> getCmdClass() {
        return ClientCommandRequestMessage.class;
    }
}
