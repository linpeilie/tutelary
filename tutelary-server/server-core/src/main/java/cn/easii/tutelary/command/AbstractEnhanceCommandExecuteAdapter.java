package cn.easii.tutelary.command;

import cn.easii.tutelary.common.CommandRequest;
import cn.easii.tutelary.common.CommandResponse;
import cn.easii.tutelary.common.domain.BaseCommandDomain;
import cn.easii.tutelary.dao.common.Dao;

public abstract class AbstractEnhanceCommandExecuteAdapter<PARAM extends CommandRequest, RESPONSE extends CommandResponse, Domain extends BaseCommandDomain<RESPONSE>>
    extends AbstractPersistenceCommandExecuteAdapter<PARAM, RESPONSE, Domain> {
    public AbstractEnhanceCommandExecuteAdapter(final Dao<Domain> dao) {
        super(dao);
    }

}
