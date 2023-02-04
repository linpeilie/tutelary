package com.tutelary.command;

import cn.hutool.core.lang.UUID;
import com.baidu.bjf.remoting.protobuf.Any;
import com.tutelary.InstanceManager;
import com.tutelary.bean.domain.Instance;
import com.tutelary.common.CommandRequest;
import com.tutelary.common.CommandResponse;
import com.tutelary.common.exception.InstanceNotExistsException;
import com.tutelary.message.CommandExecuteRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public abstract class AbstractCommandExecute<PARAM extends CommandRequest, RESPONSE extends CommandResponse>
    implements CommandExecute {

    @Autowired
    private InstanceManager instanceManager;

    @Override
    public void createCommand(String instanceId, Object request) {
        Optional<Instance> instanceOptional = instanceManager.getInstance(instanceId);
        if (!instanceOptional.isPresent()) {
            throw new InstanceNotExistsException(instanceId);
        }
        if (!(request instanceof CommandRequest)) {
            log.warn(
                "create command failed, param : {}, cause : the parameter types for command execution must inherit from CommandRequest",
                request);
            return;
        }
        // 序列化参数
        try {
            CommandExecuteRequest commandExecuteRequest = CommandExecuteRequest.builder()
                .taskId(UUID.fastUUID().toString(true)).code(commandCode()).param(Any.pack(request)).build();
            instanceOptional.get().sendData(commandExecuteRequest);
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    @Override
    public void callResult(Object response) {

    }

}
