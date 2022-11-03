package com.tutelary.client.task.factory.host;

import com.tutelary.client.command.host.HostCommand;
import com.tutelary.client.task.DefaultTask;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.factory.NonParameterTaskFactory;
import com.tutelary.constants.CommandEnum;
import com.tutelary.session.Session;

import java.lang.instrument.Instrumentation;

public class HostTaskFactory implements NonParameterTaskFactory {
    @Override
    public Task create(Session session, Instrumentation inst) {
        HostCommand hostCommand = new HostCommand();
        return new DefaultTask(commandType(), session, hostCommand);
    }

    @Override
    public CommandEnum commandType() {
        return CommandEnum.TUTELARY_HOST_INFO;
    }
}
