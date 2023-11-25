package cn.easii.tutelary.client.task;

import cn.easii.tutelary.client.command.Command;
import cn.easii.tutelary.constants.CommandEnum;

public class DefaultTask extends AbstractTask {

    public DefaultTask(String taskId, CommandEnum commandInfo, Command command) {
        super(taskId, commandInfo, command);
    }
}
