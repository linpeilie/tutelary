package com.tutelary.message.command.param;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.annotation.Command;
import com.tutelary.common.CommandRequest;
import com.tutelary.constants.CommandEnum;
import lombok.Data;

@Data
@ProtobufClass
@Command(CommandEnum.TUTELARY_THREAD_LIST)
public class ThreadListRequest extends CommandRequest {

    /**
     * 采样区间
     */
    private int samplerInterval = 200;

}
