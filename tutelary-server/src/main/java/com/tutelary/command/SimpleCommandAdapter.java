package com.tutelary.command;

import com.tutelary.common.CommandRequest;
import com.tutelary.common.CommandResponse;

public abstract class SimpleCommandAdapter<PARAM extends CommandRequest, RESPONSE extends CommandResponse>
    extends AbstractCommandExecute<PARAM, RESPONSE> {
    @Override
    protected void callResult(RESPONSE response) {

    }

}
