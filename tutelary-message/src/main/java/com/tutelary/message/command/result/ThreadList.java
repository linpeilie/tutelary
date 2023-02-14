package com.tutelary.message.command.result;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.annotation.Command;
import com.tutelary.common.CommandResponse;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.domain.BaseThreadInfo;
import com.tutelary.message.command.domain.ThreadStatistic;
import java.util.List;
import lombok.Data;

@Data
@ProtobufClass
@Command(CommandEnum.THREAD_LIST)
public class ThreadList extends CommandResponse {

    private ThreadStatistic threadStatistic;

    private List<BaseThreadInfo> threads;

}
