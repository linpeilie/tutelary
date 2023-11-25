package cn.easii.tutelary.message.command.result;

import cn.easii.tutelary.annotation.Command;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import cn.easii.tutelary.common.CommandResponse;
import cn.easii.tutelary.constants.CommandEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ProtobufClass
@Command(CommandEnum.GET_STATIC)
public class GetStaticResponse extends CommandResponse {

    private String value;

}
