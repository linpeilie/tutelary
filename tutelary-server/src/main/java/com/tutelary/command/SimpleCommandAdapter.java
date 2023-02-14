package com.tutelary.command;

import cn.hutool.json.JSONUtil;
import com.tutelary.bean.domain.InstanceSimpleCommand;
import com.tutelary.common.CommandRequest;
import com.tutelary.common.CommandResponse;
import com.tutelary.repository.InstanceSimpleCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class SimpleCommandAdapter<PARAM extends CommandRequest, RESPONSE extends CommandResponse>
    extends AbstractCommandExecute<PARAM, RESPONSE> {

    @Autowired
    private InstanceSimpleCommandRepository instanceSimpleCommandRepository;

    @Override
    protected void callResult(String instanceId, String taskId, RESPONSE response) {
        String result = JSONUtil.toJsonStr(response);

        InstanceSimpleCommand instanceSimpleCommand = new InstanceSimpleCommand();
        instanceSimpleCommand.setInstanceId(instanceId);
        instanceSimpleCommand.setCommandCode(commandCode());
        instanceSimpleCommand.setTaskId(taskId);
        instanceSimpleCommand.setResult(result);

        instanceSimpleCommandRepository.add(instanceSimpleCommand);

        taskComplete(taskId);
    }

}
