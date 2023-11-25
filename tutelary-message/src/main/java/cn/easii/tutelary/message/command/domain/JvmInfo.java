package cn.easii.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
@ProtobufClass
public class JvmInfo implements Serializable {

    private List<String> inputArguments;

    private Map<String, String> systemProperties;

    private String classPath;

    private String libraryPath;

    /**
     * Java 虚拟机实现供应商
     */
    private String vmVendor;

    /**
     * Java 虚拟机实现名称
     */
    private String vmName;

    /**
     * Java 虚拟机实现版本
     */
    private String vmVersion;

    /**
     * JDK 版本
     */
    private String jdkVersion;

    /**
     * JVM 启动时间戳
     */
    private long startTime;

}
