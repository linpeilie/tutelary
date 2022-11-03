package com.tutelary.client.task.factory.baseinfo;

import com.tutelary.client.command.BaseInfoCommand;
import com.tutelary.client.task.DefaultTask;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.factory.NonParameterTaskFactory;
import com.tutelary.client.task.factory.TaskFactory;
import com.tutelary.constants.CommandEnum;
import com.tutelary.session.Session;

import java.lang.instrument.Instrumentation;

public class BaseInfoTaskFactory implements NonParameterTaskFactory {

    @Override
    public Task create(Session session, Instrumentation inst) {
        BaseInfoCommand baseInfoCommand = new BaseInfoCommand();
        return new DefaultTask(commandType(), session, baseInfoCommand);
    }

    @Override
    public CommandEnum commandType() {
        return CommandEnum.TUTELARY_INSTANCE_INFO;
    }
}
