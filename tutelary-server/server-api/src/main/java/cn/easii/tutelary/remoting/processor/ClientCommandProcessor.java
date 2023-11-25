package cn.easii.tutelary.remoting.processor;

import cn.easii.tutelary.command.CommandExecute;
import cn.easii.tutelary.message.CommandExecuteResponse;
import cn.easii.tutelary.processor.AbstractMessageProcessor;
import cn.easii.tutelary.common.extension.ExtensionExecutor;
import cn.easii.tutelary.remoting.api.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClientCommandProcessor extends AbstractMessageProcessor<CommandExecuteResponse> {

    @Autowired
    private ExtensionExecutor extensionExecutor;

    @Override
    protected void process(Channel channel, CommandExecuteResponse message) {

        log.info("command execute response : {}", message);
        extensionExecutor.executeVoid(CommandExecute.class, message.getCode(), ext -> {
            ext.callResult(channel, message);
        });
    }

}
