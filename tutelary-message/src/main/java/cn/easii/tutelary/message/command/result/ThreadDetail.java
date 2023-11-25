package cn.easii.tutelary.message.command.result;

import cn.easii.tutelary.annotation.Command;
import cn.easii.tutelary.constants.CommandEnum;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import cn.easii.tutelary.common.CommandResponse;
import cn.easii.tutelary.message.command.domain.LockInfo;
import cn.easii.tutelary.message.command.domain.StackTraceNode;
import java.util.List;
import lombok.Data;

@Data
@ProtobufClass
@Command(CommandEnum.THREAD_DETAIL)
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
