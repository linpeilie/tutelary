package com.tutelary.constants;

import static com.tutelary.constants.CommandConstants.*;

public enum CommandEnum {

    /**
     * dashboard
     */
    ARTHAS_DASHBOARD(10010, "dashboard"),
    /**
     * thread
     */
    ARTHAS_THREAD_LIST(10020, "thread"),
    /**
     * thread [id]
     */
    ARTHAS_THREAD_STACK_TRACE(10021, "thread"),
    /**
     * thread b
     */
    ARTHAS_THREAD_BLOCK(10022, "thread"),

    /****************************** 普通任务，不需要增强 ****************************/
    TUTELARY_OVERVIEW(20010, "overview"),

    TUTELARY_THREAD_LIST(threadList, "thread base info list"),

    TUTELARY_THREAD_DETAIL(20012, "thread detail"),

    /****************************** 增强任务 ****************************/
    TUTELARY_ENHANCE(21010, "enhance"),

    /**
     * tutelary - trace
     */
    TUTELARY_TRACE_METHOD(21011, "trace"),

    /**
     * tutelary - stack
     */
    TUTELARY_STACK_METHOD(21012, "stack"),

    TUTELARY_ENHANCE_TASK_COMPLETE(21099, "complete");

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
