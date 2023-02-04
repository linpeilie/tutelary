package com.tutelary.server.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutelary.InstanceManager;
import com.tutelary.message.CommandExecuteRequest;
import com.tutelary.processor.AbstractMessageProcessor;
import com.tutelary.remoting.api.Channel;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ClientCommandRequestProcessor extends AbstractMessageProcessor<CommandExecuteRequest> {

    @Autowired
    private InstanceManager instanceManager;

    @Override
    protected void process(Channel channel, CommandExecuteRequest message) {
        log.debug("接收执行命令请求 : {}", message);
        // TODO
//        Optional<Instance> instanceOptional = instanceManager.getInstance(message.getInstanceId());
//        if (!instanceOptional.isPresent()) {
//            ErrorMessage errorMessage = new ErrorMessage();
//            errorMessage.setLastCmd(String.valueOf(message.getCmd()));
//            errorMessage.setMessage(message.getInstanceId() + "实例不存在或已下线");
//            ctx.channel().writeAndFlush(errorMessage);
//            return;
//        }

//        instanceOptional.get().writeAndFlush(message);
    }
}
