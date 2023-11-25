package cn.easii.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import java.io.Serializable;
import lombok.Data;

@Data
@ProtobufClass
public class StackTraceNode implements Serializable {

    private String declaringClass;

    private String methodName;

    private int lineNumber;

    private boolean isNative;

}
