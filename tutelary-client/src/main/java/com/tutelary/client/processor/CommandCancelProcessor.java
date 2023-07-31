package com.tutelary.client.processor;

import com.tutelary.client.task.TaskExecutor;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.message.CommandCancelRequest;
import com.tutelary.processor.AbstractMessageProcessor;
import com.tutelary.remoting.api.Channel;

public class CommandCancelProcessor extends AbstractMessageProcessor<CommandCancelRequest> {

    private static final Log LOG = LogFactory.get(CommandCancelProcessor.class);

    @Override
    protected void process(final Channel channel, final CommandCancelRequest msg) {
        LOG.info("cancel task, request msg : {}", msg);
        TaskExecutor.cancelTask(msg.getTaskId());
    }
}
