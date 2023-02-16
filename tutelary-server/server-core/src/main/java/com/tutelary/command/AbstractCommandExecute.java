package com.tutelary.command;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baidu.bjf.remoting.protobuf.Any;
import com.tutelary.InstanceManager;
import com.tutelary.bean.domain.CommandTask;
import com.tutelary.bean.domain.Instance;
import com.tutelary.common.CommandRequest;
import com.tutelary.common.CommandResponse;
import com.tutelary.common.constants.Constants;
import com.tutelary.common.exception.InstanceNotExistsException;
import com.tutelary.common.utils.Asserts;
import com.tutelary.common.utils.ClassUtil;
import com.tutelary.dao.CommandTaskDAO;
import com.tutelary.message.CommandExecuteRequest;
import com.tutelary.message.CommandExecuteResponse;
import com.tutelary.remoting.api.Channel;
import java.io.IOException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class AbstractCommandExecute<PARAM extends CommandRequest, RESPONSE extends CommandResponse>
    implements CommandExecute<PARAM, RESPONSE> {

    @Autowired
    private InstanceManager instanceManager;

    @Autowired
    private CommandTaskDAO commandTaskDAO;

    @Override
    public void createCommand(String instanceId, PARAM request) {
        Optional<Instance> instanceOptional = instanceManager.getInstance(instanceId);
        if (!instanceOptional.isPresent()) {
            throw new InstanceNotExistsException(instanceId);
        }
        try {
            String taskId = generateTaskId();

            // 任务持久化
            commandTaskPersistence(instanceId, taskId, request);

            CommandExecuteRequest commandExecuteRequest = CommandExecuteRequest.builder()
                .taskId(taskId)
                .code(commandCode())
                .param(Any.pack(request))
                .build();
            instanceOptional.get().sendData(commandExecuteRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void callResult(Channel channel, CommandExecuteResponse response) {
        Object instanceId = channel.getAttribute(Constants.ChannelAttributeConstants.INSTANCE_ID);
        String taskId = response.getTaskId();
        if (StrUtil.isBlankIfStr(instanceId)) {
            log.warn("call result occur error, instance id is null, task id : {}", taskId);
            // TODO:
            return;
        }
        CommandTask commandTask = commandTaskDAO.getByTaskId(taskId);
        if (commandTask == null) {
            log.warn("call result failed, command task not exists, taskId : {}", taskId);
            // TODO:
            return;
        }
        Any data = response.getData();
        try {
            RESPONSE result = data.unpack(getResponseClass());
            callResult(Convert.toStr(instanceId), taskId, result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void callResult(String instanceId, String taskId, RESPONSE response);

    private String generateTaskId() {
        return UUID.fastUUID().toString(true);
    }

    private void commandTaskPersistence(String instanceId, String taskId, PARAM param) {
        CommandTask commandTask = new CommandTask();
        commandTask.setInstanceId(instanceId);
        commandTask.setTaskId(taskId);
        commandTask.setParam(JSONUtil.toJsonStr(param));
        commandTask.setCommandCode(commandCode());
        commandTaskDAO.add(commandTask);
    }

    protected Class<RESPONSE> getResponseClass() {
        return ClassUtil.getGenericsBySuperClass(getClass(), CommandResponse.class);
    }

    protected void taskComplete(String taskId) {
        commandTaskDAO.commandTaskComplete(taskId);
    }

}
