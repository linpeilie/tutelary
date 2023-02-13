package com.tutelary.client.task.factory.overview;

import java.lang.instrument.Instrumentation;

import com.tutelary.client.command.overview.OverviewCommand;
import com.tutelary.client.task.ReusedTask;
import com.tutelary.client.task.Task;
import com.tutelary.client.task.factory.NonParameterTaskFactory;
import com.tutelary.constants.CommandEnum;

public class OverviewTaskFactory implements NonParameterTaskFactory {
    @Override
    public Task create(String taskId, Instrumentation inst) {
        OverviewCommand overviewCommand = new OverviewCommand();
        return new ReusedTask(taskId, commandType(), overviewCommand);
    }

    @Override
    public CommandEnum commandType() {
        return CommandEnum.TUTELARY_OVERVIEW;
    }
}
