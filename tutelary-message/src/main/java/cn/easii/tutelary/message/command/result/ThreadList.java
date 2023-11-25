package cn.easii.tutelary.message.command.result;

import cn.easii.tutelary.annotation.Command;
import cn.easii.tutelary.constants.CommandEnum;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import cn.easii.tutelary.common.CommandResponse;
import cn.easii.tutelary.message.command.domain.BaseThreadInfo;
import cn.easii.tutelary.message.command.domain.ThreadStatistic;
import java.util.List;
import lombok.Data;

@Data
@ProtobufClass
@Command(CommandEnum.THREAD_LIST)
public class ThreadList extends CommandResponse {

    private ThreadStatistic threadStatistic;

    private List<BaseThreadInfo> threads;

}
