package cn.easii.tutelary.client.task.factory;

import cn.easii.tutelary.common.CommandRequest;
import cn.easii.tutelary.common.extension.ExtensionPointI;
import cn.easii.tutelary.common.utils.ClassUtil;
import cn.easii.tutelary.constants.CommandEnum;
import cn.easii.tutelary.client.task.Task;
import java.lang.instrument.Instrumentation;

public interface TaskFactory<T extends CommandRequest> extends ExtensionPointI {

    Task create(String taskId, Instrumentation inst, T param);

    CommandEnum commandType();

    default Class<T> parameterClass() {
        return ClassUtil.getGenericsBySuperClass(getClass(), CommandRequest.class);
    }

}
