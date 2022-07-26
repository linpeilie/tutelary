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
