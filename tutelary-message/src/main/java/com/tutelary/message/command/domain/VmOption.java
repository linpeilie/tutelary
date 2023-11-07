package com.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Data
@ProtobufClass
public class VmOption implements Serializable {

    private String name;
    private String value;
    private boolean writeable;
    /**
     * Origin of the value of a Vm option.
     */
    private String origin;

}
