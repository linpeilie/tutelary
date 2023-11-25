package cn.easii.tutelary.message.command.result;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import cn.easii.tutelary.common.CommandResponse;
import lombok.Data;

@ProtobufClass
@Data
public class EnhanceCommandComplete extends CommandResponse {

    private Integer code;

}
