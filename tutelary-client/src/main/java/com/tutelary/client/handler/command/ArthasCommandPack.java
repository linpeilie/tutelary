package com.tutelary.client.handler.command;

import com.taobao.arthas.core.command.klass100.*;
import com.taobao.arthas.core.command.monitor200.*;
import com.taobao.arthas.core.shell.command.Command;
import com.taobao.arthas.core.shell.command.CommandResolver;

import java.util.ArrayList;
import java.util.List;

public class ArthasCommandPack implements CommandResolver {

    @Override
    public List<Command> commands() {
        List<Command> commands = new ArrayList<>();
        return commands;
    }

}
