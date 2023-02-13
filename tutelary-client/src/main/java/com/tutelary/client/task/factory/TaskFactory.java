package com.tutelary.client.task.factory;

import java.lang.instrument.Instrumentation;

import com.tutelary.client.task.Task;
import com.tutelary.common.CommandRequest;
import com.tutelary.common.extension.ExtensionPointI;
import com.tutelary.common.utils.ClassUtil;
import com.tutelary.constants.CommandEnum;

public interface TaskFactory<T extends CommandRequest> extends ExtensionPointI {

    Task create(String taskId, Instrumentation inst, T param);

    CommandEnum commandType();

    default Class<T> parameterClass() {
        return ClassUtil.getGenericsBySuperClass(getClass(), CommandRequest.class);
    }

}
