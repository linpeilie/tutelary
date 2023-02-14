package com.tutelary.message.command.result;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.annotation.Command;
import com.tutelary.common.CommandResponse;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.domain.GarbageCollector;
import com.tutelary.message.command.domain.HostInfo;
import com.tutelary.message.command.domain.JvmMemory;
import com.tutelary.message.command.domain.ThreadStatistic;
import java.util.List;
import lombok.Data;

@Data
@ProtobufClass
@Command(CommandEnum.OVERVIEW)
public class Overview extends CommandResponse {

    private HostInfo hostInfo;

    private ThreadStatistic threadStatistic;

    private List<JvmMemory> heapMemory;

    private List<JvmMemory> nonHeapMemory;

    private List<GarbageCollector> garbageCollectors;

}
