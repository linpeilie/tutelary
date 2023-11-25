package cn.easii.tutelary.client.processor;

import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.easii.tutelary.message.CommandCancelRequest;
import cn.easii.tutelary.processor.AbstractMessageProcessor;
import cn.easii.tutelary.processor.MessageProcessor;
import com.google.auto.service.AutoService;
import cn.easii.tutelary.client.task.TaskExecutor;
import cn.easii.tutelary.remoting.api.Channel;

@AutoService(MessageProcessor.class)
public class CommandCancelProcessor extends AbstractMessageProcessor<CommandCancelRequest> {

    private static final Log LOG = LogFactory.get(CommandCancelProcessor.class);

    @Override
    protected void process(final Channel channel, final CommandCancelRequest msg) {
        LOG.info("cancel task, request msg : {}", msg);
        TaskExecutor.cancelTask(msg.getTaskId());
    }
}
