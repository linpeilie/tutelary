package com.tutelary.command;

import cn.hutool.core.util.ReflectUtil;
import com.tutelary.common.CommandRequest;
import com.tutelary.common.CommandResponse;
import com.tutelary.common.domain.BaseCommandDomain;
import com.tutelary.common.utils.ClassUtil;
import com.tutelary.dao.common.Dao;

public abstract class AbstractSimpleCommandExecuteAdapter<PARAM extends CommandRequest, RESPONSE extends CommandResponse, Domain extends BaseCommandDomain<RESPONSE>>
    extends AbstractCommandExecute<PARAM, RESPONSE> {

    private final Dao<Domain> dao;

    public AbstractSimpleCommandExecuteAdapter(final Dao<Domain> dao) {
        this.dao = dao;
    }

    @Override
    protected void callResult(final String instanceId, final String taskId, final RESPONSE response) {
        Domain commandDomain = ReflectUtil.newInstance(getDomainClass());
        commandDomain.setTaskId(taskId);
        commandDomain.setInstanceId(instanceId);
        commandDomain.setResult(response);

        dao.add(commandDomain);
    }

    protected Class<Domain> getDomainClass() {
        return ClassUtil.getGenericsBySuperClass(getClass(), BaseCommandDomain.class);
    }

}
