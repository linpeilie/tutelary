package com.tutelary.message.command;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.annotation.Command;
import com.tutelary.common.CommandResult;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.domain.TraceNode;
import lombok.Data;

import java.util.List;

@Data
@ProtobufClass
@Command(CommandEnum.TUTELARY_TRACE_METHOD)
public class TraceResult extends CommandResult {

    TraceNode node;

    public long totalTimeSpent() {
        if (node == null) {
            return 0;
        }
        return node.getEndTimestamp() - node.getBeginTimestamp();
    }

}
