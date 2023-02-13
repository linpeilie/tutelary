package com.tutelary.client.task.factory;

import java.lang.instrument.Instrumentation;

import com.tutelary.client.task.Task;

public interface NonParameterTaskFactory extends TaskFactory {

    Task create(String taskId, Instrumentation inst);

}
