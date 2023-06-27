package com.tutelary.service.impl;

import com.tutelary.bean.domain.CommandTask;
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
    public CommandTask createCommand(final Integer commandCode, final String instanceId, final CommandRequest param) {
        return extensionExecutor.execute(CommandExecute.class, commandCode,
            ext -> ext.createCommand(instanceId, param));
    }
}
