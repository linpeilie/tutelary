package com.tutelary.message.command.result;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.annotation.Command;
import com.tutelary.common.CommandResponse;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.domain.LockInfo;
import com.tutelary.message.command.domain.StackTraceNode;
import lombok.Data;

import java.util.List;

@Data
@ProtobufClass
@Command(CommandEnum.TUTELARY_THREAD_DETAIL)
public class ThreadDetail extends CommandResponse {

    private long id;

    private String name;

    private String threadState;

    private LockInfo lock;

    private String lockName;

    private long lockOwnerId;

    private String lockOwnerName;

    private List<StackTraceNode> stackTrace;


}
