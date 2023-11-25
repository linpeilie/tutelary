package cn.easii.tutelary.message.command.result;

import cn.easii.tutelary.annotation.Command;
import cn.easii.tutelary.constants.CommandEnum;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import cn.easii.tutelary.common.CommandResponse;
import cn.easii.tutelary.message.command.domain.GarbageCollector;
import cn.easii.tutelary.message.command.domain.HostInfo;
import cn.easii.tutelary.message.command.domain.JvmMemory;
import cn.easii.tutelary.message.command.domain.ThreadStatistic;
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
