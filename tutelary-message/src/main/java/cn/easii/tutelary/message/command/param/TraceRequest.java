package cn.easii.tutelary.message.command.param;

import cn.easii.tutelary.annotation.Command;
import cn.easii.tutelary.constants.CommandEnum;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import cn.easii.tutelary.common.CommandRequest;
import java.util.List;
import lombok.Data;

@Data
@ProtobufClass
@Command(CommandEnum.TRACE_METHOD)
public class TraceRequest extends CommandRequest {

    /**
     * 类名
     */
    private String qualifiedClassName;

    /**
     * 方法名
     */
    private List<String> methodNames;

    /**
     * 执行次数
     */
    private int times = 1;

    /**
     * 方法执行耗时
     */
    private int cost = 0;

}
