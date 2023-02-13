package com.tutelary.constants;

import static com.tutelary.constants.CommandConstants.*;

public enum CommandEnum {

    /****************************** 普通任务，不需要增强 ****************************/
    OVERVIEW(overview, "overview"),

    THREAD_LIST(threadList, "thread base info list"),

    THREAD_DETAIL(threadDetail, "thread detail"),

    /****************************** 增强任务 ****************************/
    /**
     * tutelary - trace
     */
    TRACE_METHOD(traceMethod, "trace"),

    /**
     * tutelary - stack
     */
    STACK_METHOD(stackMethod, "stack");

    private final Integer commandCode;
    private final String command;

    CommandEnum(Integer commandCode, String command) {
        this.commandCode = commandCode;
        this.command = command;
    }

    public Integer getCommandCode() {
        return commandCode;
    }

    public String getCommand() {
        return command;
    }

    public static CommandEnum getByTypeAndCode(Integer code) {
        for (CommandEnum commandEnum : CommandEnum.values()) {
            if (commandEnum.getCommandCode().equals(code)) {
                return commandEnum;
            }
        }
        return null;
    }

}
