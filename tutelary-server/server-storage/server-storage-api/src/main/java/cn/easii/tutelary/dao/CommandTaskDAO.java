package cn.easii.tutelary.dao;

import cn.easii.tutelary.dao.common.QueryDAO;
import cn.easii.tutelary.message.command.result.EnhanceAffect;
import cn.easii.tutelary.bean.domain.CommandTask;
import cn.easii.tutelary.bean.domain.query.CommandTaskQuery;

public interface CommandTaskDAO extends QueryDAO<CommandTaskQuery, CommandTask> {

    CommandTask getByTaskId(String taskId);

    boolean commandTaskComplete(String taskId);

    boolean updateEnhanceAffect(String taskId, EnhanceAffect enhanceAffect);

}
