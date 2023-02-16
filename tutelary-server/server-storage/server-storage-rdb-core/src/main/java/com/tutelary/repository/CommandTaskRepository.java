package com.tutelary.repository;

import com.tutelary.bean.domain.CommandTask;
import com.tutelary.bean.domain.query.CommandTaskQuery;
import com.tutelary.bean.entity.CommandTaskEntity;
import com.tutelary.common.repository.BaseRepository;
import com.tutelary.dao.CommandTaskDAO;

public interface CommandTaskRepository
    extends BaseRepository<CommandTaskQuery, CommandTask, CommandTaskEntity>, CommandTaskDAO {

    CommandTask getByTaskId(String taskId);

    boolean commandTaskComplete(String taskId);

}
