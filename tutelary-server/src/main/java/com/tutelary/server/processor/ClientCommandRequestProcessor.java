package com.tutelary.server.processor;

import com.tutelary.InstanceManager;
import com.tutelary.bean.domain.Instance;
import com.tutelary.message.ClientCommandRequestMessage;
import com.tutelary.message.ErrorMessage;
import com.tutelary.processor.AbstractMessageProcessor;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClientCommandRequestProcessor extends AbstractMessageProcessor<ClientCommandRequestMessage> {

    @Autowired
    private InstanceManager instanceManager;

    @Override
    protected void process(ChannelHandlerContext ctx, ClientCommandRequestMessage message) {
        message.setSessionId(ctx.channel().id().asShortText());
        log.debug("接收执行命令请求 : {}", message);
        Instance instance = instanceManager.getInstance(message.getInstanceId());
        if (instance == null) {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setLastCmd(String.valueOf(message.getCmd()));
            errorMessage.setMessage(message.getInstanceId() + "实例不存在");
            ctx.channel().writeAndFlush(errorMessage);
            return;
        }

        instance.writeAndFlush(message);
    }

    @Override
    public Class<ClientCommandRequestMessage> getCmdClass() {
        return ClientCommandRequestMessage.class;
    }
}
