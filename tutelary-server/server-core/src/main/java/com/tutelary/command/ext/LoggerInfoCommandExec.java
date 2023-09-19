package com.tutelary.command.ext;

import com.tutelary.command.AbstractTemporaryCommandExecuteAdapter;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.param.LoggerInfoRequest;
import com.tutelary.message.command.result.LoggerInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Extension(commandCode = CommandConstants.loggerInfo)
public class LoggerInfoCommandExec
    extends AbstractTemporaryCommandExecuteAdapter<LoggerInfoRequest, LoggerInfoResponse> {

    @Override
    public Integer commandCode() {
        return CommandEnum.LOGGER_INFO.getCommandCode();
    }
}
