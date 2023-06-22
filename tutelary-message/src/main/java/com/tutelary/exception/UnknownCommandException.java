package com.tutelary.exception;

import com.tutelary.common.exception.BaseException;
import com.tutelary.constants.CommandResponseCode;

public class UnknownCommandException extends BaseException {

    private final byte cmd;

    public UnknownCommandException(byte cmd) {
        super(CommandResponseCode.UNKNOWN_COMMAND, cmd);
        this.cmd = cmd;
    }

    public byte getCmd() {
        return cmd;
    }
}
