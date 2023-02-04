package com.tutelary.server.processor;

import org.springframework.stereotype.Component;

import com.tutelary.message.CommandExecuteResponse;
import com.tutelary.processor.AbstractMessageProcessor;
import com.tutelary.remoting.api.Channel;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ClientCommandResponseProcessor extends AbstractMessageProcessor<CommandExecuteResponse>  {

    @Override
    protected void process(Channel channel, CommandExecuteResponse message) {
        log.info("command execute response : {}", message);
    }
}
