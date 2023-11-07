package com.tutelary.constants;

import static com.tutelary.constants.CommandConstants.decompile;
import static com.tutelary.constants.CommandConstants.fileDownload;
import static com.tutelary.constants.CommandConstants.fileList;
import static com.tutelary.constants.CommandConstants.getStatic;
import static com.tutelary.constants.CommandConstants.getVmOption;
import static com.tutelary.constants.CommandConstants.heapDump;
import static com.tutelary.constants.CommandConstants.loggerInfo;
import static com.tutelary.constants.CommandConstants.overview;
import static com.tutelary.constants.CommandConstants.stackMethod;
import static com.tutelary.constants.CommandConstants.threadDetail;
import static com.tutelary.constants.CommandConstants.threadList;
import static com.tutelary.constants.CommandConstants.traceMethod;
import static com.tutelary.constants.CommandConstants.updateLoggerLevel;

public enum CommandEnum {

    /****************************** 普通任务，不需要增强 ****************************/
    OVERVIEW(overview, "overview"),

    THREAD_LIST(threadList, "thread base info list"),

    THREAD_DETAIL(threadDetail, "thread detail"),

    HEAP_DUMP(heapDump, "heap dump"),

    FILE_LIST(fileList, "file list"),

    GET_STATIC(getStatic, "get static field"),

    FILE_DOWNLOAD(fileDownload, "file download"),

    DECOMPILE(decompile, "decompile"),

    LOGGER_INFO(loggerInfo, "logger info"),

    UPDATE_LOGGER_LEVEL(updateLoggerLevel, "update logger level"),

    GET_VM_OPTION(getVmOption, "get VM Option"),

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
