package cn.easii.tutelary.message.command.result;

import cn.easii.tutelary.annotation.Command;
import cn.easii.tutelary.constants.CommandEnum;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import cn.easii.tutelary.common.CommandResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ProtobufClass
@Command(CommandEnum.UPDATE_LOGGER_LEVEL)
@EqualsAndHashCode(callSuper = true)
public class UpdateLoggerLevelResponse extends CommandResponse {

    private boolean success;

}
