package com.tutelary.command;

import cn.hutool.core.lang.UUID;
import com.baidu.bjf.remoting.protobuf.Any;
import com.tutelary.InstanceManager;
import com.tutelary.bean.domain.Instance;
import com.tutelary.common.CommandRequest;
import com.tutelary.common.CommandResponse;
import com.tutelary.common.exception.InstanceNotExistsException;
import com.tutelary.common.utils.ClassUtil;
import com.tutelary.message.CommandExecuteRequest;
import com.tutelary.message.CommandExecuteResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class AbstractCommandExecute<PARAM extends CommandRequest, RESPONSE extends CommandResponse>
    implements CommandExecute<PARAM, RESPONSE> {

    @Autowired
    private InstanceManager instanceManager;

    @Override
    public void createCommand(String instanceId, PARAM request) {
        Optional<Instance> instanceOptional = instanceManager.getInstance(instanceId);
        if (!instanceOptional.isPresent()) {
            throw new InstanceNotExistsException(instanceId);
        }
        // 序列化参数
        try {
            CommandExecuteRequest commandExecuteRequest = CommandExecuteRequest.builder()
                .taskId(UUID.fastUUID().toString(true))
                .code(commandCode())
                .param(Any.pack(request))
                .build();
            instanceOptional.get().sendData(commandExecuteRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void callResult(CommandExecuteResponse response) {
        Any data = response.getData();
        try {
            RESPONSE result = data.unpack(getResponseClass());
            callResult(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void callResult(RESPONSE response);

    protected Class<RESPONSE> getResponseClass() {
        return ClassUtil.getGenericsBySuperClass(getClass(), CommandResponse.class);
    }

}
