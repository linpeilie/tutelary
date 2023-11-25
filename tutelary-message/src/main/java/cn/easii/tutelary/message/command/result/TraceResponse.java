package cn.easii.tutelary.message.command.result;

import cn.easii.tutelary.annotation.Command;
import cn.easii.tutelary.constants.CommandEnum;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import cn.easii.tutelary.common.CommandResponse;
import cn.easii.tutelary.message.command.domain.TraceNode;
import lombok.Data;

@Data
@ProtobufClass
@Command(CommandEnum.TRACE_METHOD)
public class TraceResponse extends CommandResponse {

    TraceNode node;

    public long totalTimeSpent() {
        if (node == null) {
            return 0;
        }
        return node.getEndTimestamp() - node.getBeginTimestamp();
    }

}
