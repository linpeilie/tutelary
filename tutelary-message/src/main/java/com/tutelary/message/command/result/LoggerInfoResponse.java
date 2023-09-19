package com.tutelary.message.command.result;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.annotation.Command;
import com.tutelary.common.CommandResponse;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.domain.LoggerInfo;
import java.util.List;
import lombok.Data;

@Data
@ProtobufClass
@Command(CommandEnum.LOGGER_INFO)
public class LoggerInfoResponse extends CommandResponse {

    private List<LoggerInfo> loggers;

}
