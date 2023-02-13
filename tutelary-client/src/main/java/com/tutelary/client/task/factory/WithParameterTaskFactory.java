package com.tutelary.client.task.factory;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Type;

import com.tutelary.client.task.Task;
import com.tutelary.common.CommandRequest;

import cn.hutool.core.util.TypeUtil;

public interface WithParameterTaskFactory<T extends CommandRequest> extends TaskFactory {

    Task create(String taskId, Instrumentation inst, T param);

    default Class<T> parameterClass() {
        Type[] typeArguments = TypeUtil.getTypeArguments(getClass());
        for (Type typeArgument : typeArguments) {
            if (CommandRequest.class.isAssignableFrom(TypeUtil.getClass(typeArgument))) {
                return (Class<T>) TypeUtil.getClass(typeArgument);
            }
        }
        throw new RuntimeException("unknown param class");
    }

}
