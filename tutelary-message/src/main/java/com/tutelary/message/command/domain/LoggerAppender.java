package com.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
@ProtobufClass
public class LoggerAppender implements Serializable {

    private String name;
    private String file;
    private String blocking;
    private List<String> appenderRef;
    private String target;

}
