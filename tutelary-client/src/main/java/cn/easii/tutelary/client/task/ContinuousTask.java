package cn.easii.tutelary.client.task;

import cn.easii.tutelary.client.command.Command;
import cn.easii.tutelary.client.exception.TaskStateChangedException;
import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.easii.tutelary.constants.CommandEnum;
import cn.easii.tutelary.enums.StateEnum;
import cn.easii.tutelary.message.command.result.ContinuousCommandResponse;

/**
 * 连续且持续不断的任务
 */
public class ContinuousTask<R extends ContinuousCommandResponse> extends AbstractTask {

    private static final Log LOG = LogFactory.get();

    public ContinuousTask(final String taskId,
        final CommandEnum commandInfo,
        final Command command) {
        super(taskId, commandInfo, command);
    }

    @Override
    protected Object toExecute() {
        ContinuousCommandResponse continuousCommandResponse;
        while (true) {
            if (!TaskState.RUNNING.equals(getState())) {
                LOG.error("continuous task state changed, id : {} , current state : {}", getId(), getState());
                throw new TaskStateChangedException(getId(), getState());
            }
            continuousCommandResponse = (ContinuousCommandResponse) command.execute();
            if (continuousCommandResponse.isEnd()
                || StateEnum.FAILURE.getValue() == continuousCommandResponse.getState()) {
                break;
            } else {
                sendCommandResult(continuousCommandResponse);
            }
        }
        return continuousCommandResponse;
    }
}
