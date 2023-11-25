package cn.easii.tutelary.message.command.result;

import cn.easii.tutelary.annotation.Command;
import cn.easii.tutelary.constants.CommandEnum;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import cn.easii.tutelary.common.CommandResponse;
import cn.easii.tutelary.message.command.domain.StackTraceNode;
import java.util.List;
import lombok.Data;

@Data
@ProtobufClass
@Command(CommandEnum.STACK_METHOD)
public class StackResponse extends CommandResponse {

    private long startTimestamp;

    private long endTimestamp;

    private String threadName;

    private long threadId;

    private boolean daemon;

    private String classloader;

    private List<StackTraceNode> stackTraceNodeList;

}
