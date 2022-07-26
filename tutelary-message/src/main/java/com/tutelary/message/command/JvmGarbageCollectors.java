package com.tutelary.message.command;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serializable;

@Data
@ProtobufClass
public class JvmGarbageCollectors implements Serializable {

    private GarbageCollectorsDetail scavengeInfo;

    private GarbageCollectorsDetail markSweepInfo;

}
