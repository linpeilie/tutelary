package com.tutelary.message.command.result;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.annotation.Command;
import com.tutelary.common.CommandResponse;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.domain.StackTraceNode;
import lombok.Data;

import java.util.List;

@Data
@ProtobufClass
@Command(CommandEnum.TUTELARY_STACK_METHOD)
public class StackResponse extends CommandResponse {

    private long startTimestamp;

    private long endTimestamp;

    private String threadName;

    private long threadId;

    private boolean daemon;

    private String classloader;

    private List<StackTraceNode> stackTraceNodeList;

}
