package cn.easii.tutelary.command;

import cn.hutool.core.util.ReflectUtil;
import cn.easii.tutelary.common.CommandRequest;
import cn.easii.tutelary.common.CommandResponse;
import cn.easii.tutelary.common.domain.BaseCommandDomain;
import cn.easii.tutelary.common.utils.ClassUtil;
import cn.easii.tutelary.dao.common.Dao;
import java.time.LocalDateTime;

public abstract class AbstractPersistenceCommandExecuteAdapter<PARAM extends CommandRequest, RESPONSE extends CommandResponse, Domain extends BaseCommandDomain<RESPONSE>>
    extends AbstractCommandExecute<PARAM, RESPONSE> {

    private final Dao<Domain> dao;

    public AbstractPersistenceCommandExecuteAdapter(final Dao<Domain> dao) {
        this.dao = dao;
    }

    @Override
    protected void callResult(final String instanceId, final String taskId, final RESPONSE response) {
        Domain commandDomain = ReflectUtil.newInstance(getDomainClass());
        commandDomain.setTaskId(taskId);
        commandDomain.setInstanceId(instanceId);
        // TODO:客户端上报时间
        commandDomain.setReportTime(LocalDateTime.now());
        commandDomain.setResult(response);

        dao.add(commandDomain);
    }

    protected Class<Domain> getDomainClass() {
        return ClassUtil.getGenericsBySuperClass(getClass(), BaseCommandDomain.class);
    }

}
