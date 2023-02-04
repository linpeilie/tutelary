package com.tutelary.service.impl;

import com.tutelary.bean.api.req.CommandApiRequest;
import com.tutelary.command.CommandExecute;
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
    public void createCommand(CommandApiRequest commandApiRequest) {
        extensionExecutor.executeVoid(CommandExecute.class, commandApiRequest.getCommandCode(),
            ext -> ext.createCommand(commandApiRequest.getInstanceId(), commandApiRequest.getParam()));
    }
}
