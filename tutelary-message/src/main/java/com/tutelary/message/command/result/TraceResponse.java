package com.tutelary.message.command.result;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.annotation.Command;
import com.tutelary.common.CommandResponse;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.domain.TraceNode;
import lombok.Data;

@Data
@ProtobufClass
@Command(CommandEnum.TUTELARY_TRACE_METHOD)
public class TraceResponse extends CommandResponse {

    TraceNode node;

    public long totalTimeSpent() {
        if (node == null) {
            return 0;
        }
        return node.getEndTimestamp() - node.getBeginTimestamp();
    }

}
