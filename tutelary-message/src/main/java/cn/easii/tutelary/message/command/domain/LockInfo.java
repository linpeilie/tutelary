package cn.easii.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import java.io.Serializable;
import lombok.Data;

@Data
@ProtobufClass
public class LockInfo implements Serializable {

    private String className;

    private int identityHashCode;

}
