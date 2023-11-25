package cn.easii.tutelary.command;

import cn.easii.tutelary.common.CommandRequest;
import cn.easii.tutelary.common.CommandResponse;
import cn.easii.tutelary.common.domain.BaseCommandDomain;
import cn.easii.tutelary.dao.common.Dao;

public abstract class AbstractSingleCommandExecuteAdapter<PARAM extends CommandRequest, RESPONSE extends CommandResponse, Domain extends BaseCommandDomain<RESPONSE>>
    extends AbstractPersistenceCommandExecuteAdapter<PARAM, RESPONSE, Domain>{
    public AbstractSingleCommandExecuteAdapter(final Dao<Domain> dao) {
        super(dao);
    }

    @Override
    protected void callResult(final String instanceId, final String taskId, final RESPONSE response) {
        super.callResult(instanceId, taskId, response);
        commandTaskDAO.commandTaskComplete(taskId);
    }
}
