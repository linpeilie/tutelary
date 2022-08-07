package com.tutelary.client.arthas.distribution.factory;

import com.tutelary.client.arthas.distribution.*;
import com.tutelary.common.CommandResult;
import com.tutelary.constants.CommandEnum;
import com.tutelary.constants.CommandTypeConstants;

public class ResultDistributionFactory {

    public AbstractResultDistributor<? extends CommandResult> getResultDistributor(Integer commandCode) {
        CommandEnum commandEnum = CommandEnum.getByTypeAndCode(CommandTypeConstants.ARTHAS, commandCode);
        if (commandEnum == null) {
            return null;
        }
        switch (commandEnum) {
            case ARTHAS_DASHBOARD:
                return new DashboardCommandResultDistributor();
            case ARTHAS_THREAD_LIST:
                return new ThreadListCommandResultDistributor();
            case ARTHAS_THREAD_BLOCK:
                return new ThreadBlockCommandResultDistributor();
            case ARTHAS_THREAD_STACK_TRACE:
                return new ThreadStackTraceCommandResultDistributor();
            default:
                return null;
        }
    }

}
