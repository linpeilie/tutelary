package com.tutelary.client.task.factory;

import cn.hutool.core.util.TypeUtil;
import com.tutelary.client.task.Task;
import com.tutelary.common.BaseCommandParam;
import com.tutelary.session.Session;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Type;

public interface WithParameterTaskFactory<T extends BaseCommandParam> extends TaskFactory {

    Task create(Session session, Instrumentation inst, T param);

    default Class<T> parameterClass() {
        Type[] typeArguments = TypeUtil.getTypeArguments(getClass());
        for (Type typeArgument : typeArguments) {
            if (BaseCommandParam.class.isAssignableFrom(TypeUtil.getClass(typeArgument))) {
                return (Class<T>) TypeUtil.getClass(typeArgument);
            }
        }
        throw new RuntimeException("unknown param class");
    }

}
