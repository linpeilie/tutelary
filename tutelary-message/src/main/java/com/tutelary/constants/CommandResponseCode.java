package com.tutelary.constants;

import com.tutelary.common.constants.BusinessResponseCode;
import com.tutelary.common.constants.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommandResponseCode implements ResponseCode {

    UNKNOWN_COMMAND("Unknown command %s", 1);

    private final String code;
    private final String message;
    private final int args;

    CommandResponseCode(String message, int args) {
        this.code = formatCode(BusinessResponseCode.COMMAND.getCodePrefix());
        this.message = message;
        this.args = args;
    }

}
