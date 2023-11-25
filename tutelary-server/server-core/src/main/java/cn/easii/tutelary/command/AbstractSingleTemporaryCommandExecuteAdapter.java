package cn.easii.tutelary.command;

import cn.easii.tutelary.common.CommandRequest;
import cn.easii.tutelary.common.CommandResponse;

public abstract class AbstractSingleTemporaryCommandExecuteAdapter<PARAM extends CommandRequest, RESPONSE extends CommandResponse>
    extends AbstractTemporaryCommandExecuteAdapter<PARAM, RESPONSE> {

    @Override
    protected void callResult(final String instanceId, final String taskId, final RESPONSE response) {
        super.callResult(instanceId, taskId, response);
        commandTaskDAO.commandTaskComplete(taskId);
    }
}
