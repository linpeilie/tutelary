package cn.easii.tutelary.service.impl;

import cn.easii.tutelary.bean.domain.CommandTask;
import cn.easii.tutelary.command.CommandExecute;
import cn.easii.tutelary.common.CommandRequest;
import cn.easii.tutelary.common.extension.ExtensionExecutor;
import cn.easii.tutelary.service.CommandService;
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
