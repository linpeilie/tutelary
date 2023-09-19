package com.tutelary.message.command.param;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.annotation.Command;
import com.tutelary.common.CommandRequest;
import com.tutelary.constants.CommandEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ProtobufClass
@Command(CommandEnum.LOGGER_INFO)
@EqualsAndHashCode(callSuper = true)
public class LoggerInfoRequest extends CommandRequest {

    private String name;

    private boolean includeNoAppender;

    private String classLoaderHashCode;

}
