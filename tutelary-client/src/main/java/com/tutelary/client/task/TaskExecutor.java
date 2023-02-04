package com.tutelary.client.task;

import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.message.CommandExecuteRequest;
import io.netty.channel.ChannelHandlerContext;

public class TaskExecutor {

    private static final Log LOG = LogFactory.get(TaskExecutor.class);

    private static final TaskSerializeNumberCache serialNumberCache = new TaskSerializeNumberCache();

    public void executeTask(Task task) {
        if (serialNumberCache.add(task.getId())) {
            task.execute();
        } else {
            LOG.warn("task : {} canceled, cause : repeated task", task.getId());
        }
    }

}
