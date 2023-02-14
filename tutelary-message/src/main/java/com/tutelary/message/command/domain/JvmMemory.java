package com.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import java.io.Serializable;
import lombok.Data;

@Data
@ProtobufClass
public class JvmMemory implements Serializable {

    /**
     * 内存区域名称
     */
    private String name;

    /**
     * 已使用内存，单位：kb
     */
    private long used;

    /**
     * 已提交内存，单位：kb
     */
    private long committed;

    /**
     * 最大内存，单位：kb
     */
    private long max;

}
