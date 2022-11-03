package com.tutelary.client.task.factory;

import com.tutelary.client.task.Task;
import com.tutelary.session.Session;

import java.lang.instrument.Instrumentation;

public interface WithParameterTaskFactory<T> extends TaskFactory {

    Task create(Session session, Instrumentation inst, T param);

    Class<T> parameterClass();

}
