package com.tutelary.service.impl;

import cn.hutool.json.JSONUtil;
import com.tutelary.bean.domain.CommandTaskCreate;
import com.tutelary.command.CommandExecute;
import com.tutelary.common.CommandRequest;
import com.tutelary.common.extension.ExtensionExecutor;
import com.tutelary.service.CommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommandServiceImpl implements CommandService {

    @Autowired
    private ExtensionExecutor extensionExecutor;

    @Override
    public void createCommand(CommandTaskCreate commandTaskCreate) {
        extensionExecutor.executeVoid(CommandExecute.class, commandTaskCreate.getCommandCode(), ext -> {
            CommandRequest param = (CommandRequest) JSONUtil.toBean(commandTaskCreate.getParam(), ext.getParamClass());
            ext.createCommand(commandTaskCreate.getInstanceId(), param);
        });
    }
}
