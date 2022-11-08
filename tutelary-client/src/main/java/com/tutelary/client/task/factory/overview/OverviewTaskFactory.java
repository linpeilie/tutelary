package com.tutelary.client.task.factory.overview;

import com.tutelary.client.command.overview.OverviewCommand;
import com.tutelary.client.task.DefaultTask;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.factory.NonParameterTaskFactory;
import com.tutelary.constants.CommandEnum;
import com.tutelary.session.Session;

import java.lang.instrument.Instrumentation;

public class OverviewTaskFactory implements NonParameterTaskFactory {
    @Override
    public Task create(Session session, Instrumentation inst) {
        OverviewCommand overviewCommand = new OverviewCommand();
        return new DefaultTask(commandType(), session, overviewCommand);
    }

    @Override
    public CommandEnum commandType() {
        return CommandEnum.TUTELARY_OVERVIEW;
    }
}
