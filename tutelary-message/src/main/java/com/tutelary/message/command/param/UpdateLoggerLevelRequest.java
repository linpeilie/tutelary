package com.tutelary.message.command.param;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.annotation.Command;
import com.tutelary.common.CommandRequest;
import com.tutelary.constants.CommandEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ProtobufClass
@Command(CommandEnum.UPDATE_LOGGER_LEVEL)
@EqualsAndHashCode(callSuper = true)
public class UpdateLoggerLevelRequest extends CommandRequest {

    private String classLoaderHashCode;

    private String name;

    private String level;

}
