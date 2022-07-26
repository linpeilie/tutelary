package com.tutelary.client.arthas.distribution;

import com.taobao.arthas.core.distribution.ResultDistributor;
import com.tutelary.common.CommandResult;

public abstract class AbstractResultDistributor<T extends CommandResult> implements ResultDistributor {

    protected static final int SUCCESS = 0;
    protected static final int FAILURE = -1;

    public abstract T getResult();

    @Override
    public void close() {
    }

}
