package com.tutelary.client.task;

import com.tutelary.client.command.Command;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.result.ContinuousCommandResponse;

/**
 * 连续且持续不断的任务
 */
public class ContinuousTask<R extends ContinuousCommandResponse> extends AbstractTask {

    public ContinuousTask(final String taskId,
        final CommandEnum commandInfo,
        final Command command) {
        super(taskId, commandInfo, command);
    }

    @Override
    protected Object toExecute() {
        ContinuousCommandResponse continuousCommandResponse;
        while (true) {
            continuousCommandResponse = (ContinuousCommandResponse) command.execute();
            if (continuousCommandResponse.isEnd()) {
                break;
            } else {
                sendCommandResult(continuousCommandResponse);
            }
        }
        return continuousCommandResponse;
    }
}
