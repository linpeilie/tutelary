package com.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
@ProtobufClass
public class LoggerInfo implements Serializable {

    private String name;

    private String clazz;

    private String classLoader;

    private String classLoaderHash;

    private String level;

    private String effectiveLevel;

    private Boolean additivity;

    private String codeSource;

    private String config;

    private List<LoggerAppender> appenders;

}
