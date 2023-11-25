package cn.easii.tutelary.message.command.param;

import cn.easii.tutelary.annotation.Command;
import cn.easii.tutelary.constants.CommandEnum;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import cn.easii.tutelary.common.CommandRequest;
import lombok.Data;

@Data
@ProtobufClass
@Command(CommandEnum.THREAD_DETAIL)
public class ThreadDetailRequest extends CommandRequest {

    private long id;

}
