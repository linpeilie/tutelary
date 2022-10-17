package com.tutelary.client.enhance;

import com.tutelary.client.command.Command;
import com.tutelary.client.command.TraceCommand;

import java.lang.instrument.Instrumentation;

public class Enhance {

    public static void enhance(Instrumentation instrumentation, String basePackage) {
        Command traceCommand = new TraceCommand(instrumentation);
        traceCommand.execute(null);
    }

}
