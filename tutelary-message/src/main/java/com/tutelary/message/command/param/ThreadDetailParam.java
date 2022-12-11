package com.tutelary.message.command.param;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.annotation.Command;
import com.tutelary.common.BaseCommandParam;
import com.tutelary.constants.CommandEnum;
import lombok.Data;

@Data
@ProtobufClass
@Command(CommandEnum.TUTELARY_THREAD_DETAIL)
public class ThreadDetailParam extends BaseCommandParam {

    private long id;

}
