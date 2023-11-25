package cn.easii.tutelary.message.command.param;

import cn.easii.tutelary.annotation.Command;
import cn.easii.tutelary.constants.CommandEnum;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import cn.easii.tutelary.common.CommandRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ProtobufClass
@Command(CommandEnum.LOGGER_INFO)
@EqualsAndHashCode(callSuper = true)
public class LoggerInfoRequest extends CommandRequest {

    private String name;

    private boolean includeNoAppender;

    private String classLoaderHashCode;

}
