package com.tutelary.dao;

import com.tutelary.bean.domain.CommandTask;
import com.tutelary.bean.domain.query.CommandTaskQuery;
import com.tutelary.dao.common.QueryDAO;

public interface CommandTaskDAO extends QueryDAO<CommandTaskQuery, CommandTask> {

    CommandTask getByTaskId(String taskId);

    boolean commandTaskComplete(String taskId);

}
