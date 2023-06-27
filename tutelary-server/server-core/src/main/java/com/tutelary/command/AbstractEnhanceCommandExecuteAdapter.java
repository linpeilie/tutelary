package com.tutelary.command;

import com.tutelary.common.CommandRequest;
import com.tutelary.common.CommandResponse;
import com.tutelary.common.domain.BaseCommandDomain;
import com.tutelary.dao.common.Dao;
import com.tutelary.message.command.result.EnhanceAffect;
import com.tutelary.message.command.result.EnhanceCommandComplete;

public abstract class AbstractEnhanceCommandExecuteAdapter<PARAM extends CommandRequest, RESPONSE extends CommandResponse, Domain extends BaseCommandDomain<RESPONSE>>
    extends AbstractPersistenceCommandExecuteAdapter<PARAM, RESPONSE, Domain> {
    public AbstractEnhanceCommandExecuteAdapter(final Dao<Domain> dao) {
        super(dao);
    }

}
