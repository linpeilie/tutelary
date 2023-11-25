package cn.easii.tutelary.message.command.param;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import cn.easii.tutelary.common.CommandRequest;
import lombok.Data;

@Data
@ProtobufClass
public class SetVmOptionRequest extends CommandRequest {

    private String name;

    private String value;



}
