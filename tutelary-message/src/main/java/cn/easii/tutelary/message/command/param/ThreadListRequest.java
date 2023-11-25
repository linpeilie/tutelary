package cn.easii.tutelary.message.command.param;

import cn.easii.tutelary.annotation.Command;
import cn.easii.tutelary.constants.CommandEnum;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import cn.easii.tutelary.common.CommandRequest;
import lombok.Data;

@Data
@ProtobufClass
@Command(CommandEnum.THREAD_LIST)
public class ThreadListRequest extends CommandRequest {

    /**
     * 采样区间
     */
    private int samplerInterval = 200;

}
