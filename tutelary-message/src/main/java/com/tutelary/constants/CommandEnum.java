package com.tutelary.constants;

public enum CommandEnum {

    /**
     * dashboard
     */
    ARTHAS_DASHBOARD(CommandTypeConstants.ARTHAS, 10010, "dashboard"),
    /**
     * thread
     */
    ARTHAS_THREAD_LIST(CommandTypeConstants.ARTHAS, 10020, "thread"),
    /**
     * thread [id]
     */
    ARTHAS_THREAD_STACK_TRACE(CommandTypeConstants.ARTHAS, 10021, "thread"),
    /**
     * thread b
     */
    ARTHAS_THREAD_BLOCK(CommandTypeConstants.ARTHAS, 10022, "thread"),

    /****************************** 普通任务，不需要增强 ****************************/
    TUTELARY_OVERVIEW(CommandTypeConstants.TUTELARY, 20010, "overview"),

    TUTELARY_THREAD_LIST(CommandTypeConstants.TUTELARY, 20011, "thread base info list"),

    /****************************** 增强任务 ****************************/
    TUTELARY_ENHANCE(CommandTypeConstants.TUTELARY, 21010, "enhance"),

    /**
     * tutelary - trace
     */
    TUTELARY_TRACE_METHOD(CommandTypeConstants.TUTELARY, 21011, "trace"),

    /**
     * tutelary - stack
     */
    TUTELARY_STACK_METHOD(CommandTypeConstants.TUTELARY, 21012, "stack"),

    TUTELARY_ENHANCE_TASK_COMPLETE(CommandTypeConstants.TUTELARY, 21099, "complete")
    ;

    private final Integer type;
    private final Integer commandCode;
    private final String command;

    CommandEnum(Integer type, Integer commandCode, String command) {
        this.type = type;
        this.commandCode = commandCode;
        this.command = command;
    }

    public Integer getType() {
        return type;
    }

    public Integer getCommandCode() {
        return commandCode;
    }

    public String getCommand() {
        return command;
    }

    public static CommandEnum getByTypeAndCode(Integer type, Integer code) {
        for (CommandEnum commandEnum : CommandEnum.values()) {
            if (commandEnum.getType().equals(type) && commandEnum.getCommandCode().equals(code)) {
                return commandEnum;
            }
        }
        return null;
    }

}
