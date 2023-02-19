package com.tutelary.command.ext;

import com.tutelary.bean.domain.InstanceThreadListCommand;
import com.tutelary.command.AbstractCommandExecute;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.constants.CommandEnum;
import com.tutelary.dao.InstanceThreadListCommandDAO;
import com.tutelary.message.command.param.ThreadListRequest;
import com.tutelary.message.command.result.ThreadList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Extension(commandCode = CommandConstants.threadList)
public class ThreadListCommandExecute extends AbstractCommandExecute<ThreadListRequest, ThreadList> {

    private InstanceThreadListCommandDAO instanceThreadListCommandDAO;

    @Override
    public Integer commandCode() {
        return CommandEnum.THREAD_LIST.getCommandCode();
    }

    @Override
    protected void callResult(final String instanceId, final String taskId, final ThreadList response) {
        InstanceThreadListCommand instanceThreadListCommand = new InstanceThreadListCommand();
        instanceThreadListCommand.setInstanceId(instanceId);
        instanceThreadListCommand.setTaskId(taskId);
        instanceThreadListCommand.setResult(response);
        instanceThreadListCommandDAO.add(instanceThreadListCommand);
    }

    @Autowired
    public void setInstanceThreadListCommandDAO(final InstanceThreadListCommandDAO instanceThreadListCommandDAO) {
        this.instanceThreadListCommandDAO = instanceThreadListCommandDAO;
    }
}
