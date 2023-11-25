package cn.easii.tutelary.command.ext;

import cn.easii.tutelary.command.AbstractTemporaryCommandExecuteAdapter;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.constants.CommandEnum;
import cn.easii.tutelary.message.command.param.LoggerInfoRequest;
import cn.easii.tutelary.message.command.result.LoggerInfoResponse;
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
