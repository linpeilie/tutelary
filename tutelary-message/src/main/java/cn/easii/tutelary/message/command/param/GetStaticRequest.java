package cn.easii.tutelary.message.command.param;

import cn.easii.tutelary.annotation.Command;
import cn.easii.tutelary.constants.CommandEnum;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import cn.easii.tutelary.common.CommandRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ProtobufClass
@Command(CommandEnum.GET_STATIC)
public class GetStaticRequest extends CommandRequest {

    private String qualifiedClassName;

    private String field;

    private String classLoader;

}
