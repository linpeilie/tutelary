package com.tutelary.message.command.result;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.annotation.Command;
import com.tutelary.common.CommandResponse;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.domain.*;
import lombok.Data;

import java.util.List;

@Data
@ProtobufClass
@Command(CommandEnum.TUTELARY_OVERVIEW)
public class Overview extends CommandResponse {

    private HostInfo hostInfo;

    private ThreadStatistic threadStatistic;

    private List<JvmMemory> heapMemory;

    private List<JvmMemory> nonHeapMemory;

    private List<GarbageCollector> garbageCollectors;

}
