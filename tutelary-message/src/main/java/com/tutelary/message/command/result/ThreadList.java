package com.tutelary.message.command.result;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.annotation.Command;
import com.tutelary.common.CommandResult;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.domain.BaseThreadInfo;
import com.tutelary.message.command.domain.ThreadOverview;
import lombok.Data;

import java.util.List;

@Data
@ProtobufClass
@Command(CommandEnum.TUTELARY_THREAD_LIST)
public class ThreadList extends CommandResult {

    private ThreadOverview threadOverview;

    private List<BaseThreadInfo> threads;

}
