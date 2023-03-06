package com.tutelary.common;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import java.io.Serializable;
import lombok.Data;

@Data
@ProtobufClass
public class Metadata implements Serializable {

    private String token;

}
