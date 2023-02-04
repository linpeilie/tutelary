package com.tutelary.client.task;

import com.tutelary.client.command.Command;
import com.tutelary.constants.CommandEnum;

public class DefaultTask extends AbstractTask {

    public DefaultTask(String taskId, CommandEnum commandInfo, Command command) {
        super(taskId, commandInfo, command);
    }
}
