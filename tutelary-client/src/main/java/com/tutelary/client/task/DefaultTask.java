package com.tutelary.client.task;

import com.tutelary.client.command.Command;
import com.tutelary.constants.CommandEnum;
import com.tutelary.session.Session;

public class DefaultTask extends AbstractTask {

    public DefaultTask(CommandEnum commandInfo, Session session, Command command) {
        super(commandInfo, session, command);
    }
}
