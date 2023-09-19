package com.tutelary.message.command.result;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.annotation.Command;
import com.tutelary.common.CommandResponse;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.domain.LoggerInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ProtobufClass
@Command(CommandEnum.UPDATE_LOGGER_LEVEL)
@EqualsAndHashCode(callSuper = true)
public class UpdateLoggerLevelResponse extends CommandResponse {

    private boolean success;

}
