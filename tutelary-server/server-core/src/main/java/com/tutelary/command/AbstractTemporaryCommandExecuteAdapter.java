package com.tutelary.command;

import com.tutelary.common.CommandRequest;
import com.tutelary.common.CommandResponse;

public abstract class AbstractTemporaryCommandExecuteAdapter <PARAM extends CommandRequest, RESPONSE extends CommandResponse>
    extends AbstractCommandExecute<PARAM, RESPONSE> {

    @Override
    protected void callResult(final String instanceId, final String taskId, final RESPONSE response) {

    }

}
