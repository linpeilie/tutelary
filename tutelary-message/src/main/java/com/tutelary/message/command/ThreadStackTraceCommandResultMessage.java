package com.tutelary.message.command;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.annotation.Command;
import com.tutelary.common.CommandResult;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.domain.ThreadInfo;
import lombok.Data;

import java.io.Serializable;

@Data
@ProtobufClass
@Command(CommandEnum.ARTHAS_THREAD_STACK_TRACE)
public class ThreadStackTraceCommandResultMessage extends CommandResult {

    private ThreadInfo threadInfo;

}
