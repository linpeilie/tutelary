package com.tutelary.command;

import com.tutelary.common.CommandRequest;
import com.tutelary.common.CommandResponse;
import com.tutelary.common.extension.ExtensionPointI;

public interface CommandExecute extends ExtensionPointI {

    void createCommand(String instanceId, Object request);

    void callResult(Object response);

    Integer commandCode();

}
