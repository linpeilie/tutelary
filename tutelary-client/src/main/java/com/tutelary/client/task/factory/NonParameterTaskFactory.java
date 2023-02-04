package com.tutelary.client.task.factory;

import com.tutelary.client.task.Task;
import com.tutelary.session.Session;

import java.lang.instrument.Instrumentation;

public interface NonParameterTaskFactory extends TaskFactory {

    Task create(String taskId, Instrumentation inst);

}
