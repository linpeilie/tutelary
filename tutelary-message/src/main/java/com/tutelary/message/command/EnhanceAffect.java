package com.tutelary.message.command;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.tutelary.common.CommandResult;
import com.tutelary.message.command.domain.BaseMethod;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 增强影响范围
 *
 * 执行增强命令后，先返回该结果
 */
@Data
@ProtobufClass
public class EnhanceAffect extends CommandResult {

    private int cCnt = 0;

    private int mCnt = 0;

    private final List<BaseMethod> methods = new ArrayList<>();

    public void addMethodAndCount(ClassLoader classLoader, String clazz, String method, String methodDesc) {
        this.methods.add(BaseMethod.build(classLoader, clazz, method, methodDesc));
        this.mCnt++;
    }

    public void cCnt() {
        this.cCnt++;
    }

}
