package com.tutelary.server.processor;

import com.tutelary.common.processor.ServerMessageProcessor;
import com.tutelary.message.ClientCommandResponseMessage;
import com.tutelary.processor.AbstractMessageProcessor;
import com.tutelary.session.Session;
import com.tutelary.server.store.SessionStore;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClientCommandResponseProcessor extends AbstractMessageProcessor<ClientCommandResponseMessage>
        implements ServerMessageProcessor<ClientCommandResponseMessage> {

    @Override
    protected void process(ChannelHandlerContext ctx, ClientCommandResponseMessage message) {
        log.debug("命令执行请求返回 : {}", message);
        Session session = SessionStore.getSession(message.getSessionId());
        if (session == null) {
            log.warn("客户端 : {} 已断开连接", message.getSessionId());
            return;
        }
        session.sendData(message);
    }

    @Override
    public Class<ClientCommandResponseMessage> getCmdClass() {
        return ClientCommandResponseMessage.class;
    }
}
