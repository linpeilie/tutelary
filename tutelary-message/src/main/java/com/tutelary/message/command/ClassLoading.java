package com.tutelary.message.command;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;

@Data
@ProtobufClass
public class ClassLoading implements Serializable {
    private int loadedClassCount;
    private long totalLoadedClassCount;
    private long unloadedClassCount;
    private boolean isVerbose;
}