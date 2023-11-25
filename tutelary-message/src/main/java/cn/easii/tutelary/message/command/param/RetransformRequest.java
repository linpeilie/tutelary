package cn.easii.tutelary.message.command.param;

import cn.easii.tutelary.annotation.Command;
import cn.easii.tutelary.common.CommandRequest;
import cn.easii.tutelary.constants.CommandEnum;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

@Data
@ProtobufClass
@Command(CommandEnum.RETRANSFORM)
public class RetransformRequest extends CommandRequest {

    private String qualifiedClassName;

    private String javaSource;

}
