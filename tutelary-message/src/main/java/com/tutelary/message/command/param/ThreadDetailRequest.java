package com.tutelary.message.command.param;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.annotation.Command;
import com.tutelary.common.CommandRequest;
import com.tutelary.constants.CommandEnum;
import lombok.Data;

@Data
@ProtobufClass
@Command(CommandEnum.THREAD_DETAIL)
public class ThreadDetailRequest extends CommandRequest {

    private long id;

}
