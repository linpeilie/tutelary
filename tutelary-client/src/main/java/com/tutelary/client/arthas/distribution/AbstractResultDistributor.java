package com.tutelary.client.arthas.distribution;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.taobao.arthas.core.command.model.ResultModel;
import com.taobao.arthas.core.command.model.StatusModel;
import com.taobao.arthas.core.distribution.ResultDistributor;
import com.tutelary.common.CommandResult;

public abstract class AbstractResultDistributor<T extends CommandResult> implements ResultDistributor {

    protected static final int SUCCESS = 0;
    protected static final int FAILURE = 1;

    private static final Log LOG = LogFactory.get();

    protected final T resultMessage;

    public AbstractResultDistributor(T resultMessage) {
        this.resultMessage = resultMessage;
    }

    public T getResult() {
        return resultMessage;
    }

    protected void appendFailureResult(String message) {
        resultMessage.setStatus(SUCCESS);
        resultMessage.setMessage(message);
    }

    protected abstract void appendCommandResult(ResultModel resultModel);

    @Override
    public void appendResult(ResultModel result) {
        LOG.debug("[ PackageResultDistributor ] append result : {}", result);
        if (result instanceof StatusModel) {
            StatusModel statusModel = (StatusModel)result;
            if (SUCCESS != statusModel.getStatusCode()) {
                appendFailureResult(statusModel.getMessage());
            }
        } else {
            resultMessage.setStatus(SUCCESS);
            appendCommandResult(result);
        }
    }

    @Override
    public void close() {
    }

}
