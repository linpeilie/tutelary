package cn.easii.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import java.io.Serializable;
import lombok.Data;

/**
 * 主机基本信息
 */
@Data
@ProtobufClass
public class HostBaseInfo implements Serializable {

    /**
     * 操作系统名称
     */
    private String osName;

    /**
     * 操作系统版本
     */
    private String osVersion;

    /**
     * CPU核心数
     */
    private int availableProcessors;

}
