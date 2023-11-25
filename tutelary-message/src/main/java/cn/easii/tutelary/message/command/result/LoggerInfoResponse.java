package cn.easii.tutelary.message.command.result;

import cn.easii.tutelary.annotation.Command;
import cn.easii.tutelary.constants.CommandEnum;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import cn.easii.tutelary.common.CommandResponse;
import cn.easii.tutelary.message.command.domain.LoggerInfo;
import java.util.List;
import lombok.Data;

@Data
@ProtobufClass
@Command(CommandEnum.LOGGER_INFO)
public class LoggerInfoResponse extends CommandResponse {

    private List<LoggerInfo> loggers;

}
