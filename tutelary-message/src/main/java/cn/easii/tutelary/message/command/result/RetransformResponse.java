package cn.easii.tutelary.message.command.result;

import cn.easii.tutelary.annotation.Command;
import cn.easii.tutelary.common.CommandResponse;
import cn.easii.tutelary.constants.CommandEnum;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ProtobufClass
@EqualsAndHashCode(callSuper = true)
@Command(CommandEnum.RETRANSFORM)
public class RetransformResponse extends CommandResponse {



}
