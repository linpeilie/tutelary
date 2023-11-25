package cn.easii.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import java.io.Serializable;
import lombok.Data;

@Data
@ProtobufClass
public class ThreadStatistic implements Serializable {

    private int threadCount;

    private int peakThreadCount;

    private int daemonThreadCount;

    private long totalStartedThreadCount;

}
