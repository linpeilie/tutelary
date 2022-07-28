package com.tutelary.client.arthas.distribution;

import com.tutelary.common.CommandResult;
import com.tutelary.common.constants.CommandConstants;

public class ResultDistributionFactory {

    public AbstractResultDistributor<? extends CommandResult> getResultDistributor(String command) {
        switch (command) {
            case CommandConstants.JVM:
                return new JvmResultDistributor();
            default:
                return null;
        }
    }

}
