package com.tutelary.message.command;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;

@Data
@ProtobufClass
public class JvmClassLoading implements Serializable {
    private Integer loadedClassCount;
    private Integer totalLoadedClassCount;
    private Integer unloadedClassCount;
    private Boolean isVerbose;
}