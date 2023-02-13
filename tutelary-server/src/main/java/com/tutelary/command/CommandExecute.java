package com.tutelary.command;

import com.tutelary.common.CommandRequest;
import com.tutelary.common.CommandResponse;
import com.tutelary.common.extension.ExtensionPointI;
import com.tutelary.common.utils.ClassUtil;
import com.tutelary.message.CommandExecuteResponse;

public interface CommandExecute<PARAM extends CommandRequest, RESPONSE extends CommandResponse> extends ExtensionPointI {

    void createCommand(String instanceId, PARAM request);

    void callResult(CommandExecuteResponse response);

    Integer commandCode();

    default Class<PARAM> getParamClass() {
        return ClassUtil.getGenericsBySuperClass(getClass(), CommandRequest.class);
    }

}
