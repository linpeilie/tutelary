package cn.easii.tutelary.constants;

public enum CommandEnum {

    /****************************** 普通任务，不需要增强 ****************************/
    OVERVIEW(CommandConstants.overview, "overview"),

    THREAD_LIST(CommandConstants.threadList, "thread base info list"),

    THREAD_DETAIL(CommandConstants.threadDetail, "thread detail"),

    HEAP_DUMP(CommandConstants.heapDump, "heap dump"),

    FILE_LIST(CommandConstants.fileList, "file list"),

    FILE_DOWNLOAD(CommandConstants.fileDownload, "file download"),

    DECOMPILE(CommandConstants.decompile, "decompile"),

    LOGGER_INFO(CommandConstants.loggerInfo, "logger info"),

    UPDATE_LOGGER_LEVEL(CommandConstants.updateLoggerLevel, "update logger level"),

    GET_STATIC(CommandConstants.getStatic, "get static field"),

    GET_VM_OPTION(CommandConstants.getVmOption, "get VM Option"),

    SET_VM_OPTION(CommandConstants.setVmOption, "set VM Option"),

    /****************************** 增强任务 ****************************/
    TRACE_METHOD(CommandConstants.traceMethod, "trace"),

    STACK_METHOD(CommandConstants.stackMethod, "stack"),

    RETRANSFORM(CommandConstants.retransform, "retransform");

    private final Integer commandCode;
    private final String command;

    CommandEnum(Integer commandCode, String command) {
        this.commandCode = commandCode;
        this.command = command;
    }

    public static CommandEnum getByTypeAndCode(Integer code) {
        for (CommandEnum commandEnum : CommandEnum.values()) {
            if (commandEnum.getCommandCode().equals(code)) {
                return commandEnum;
            }
        }
        return null;
    }

    public Integer getCommandCode() {
        return commandCode;
    }

    public String getCommand() {
        return command;
    }

}
