package com.tutelary.repository;

import com.tutelary.bean.domain.CommandTask;
import com.tutelary.bean.domain.query.CommandTaskQuery;
import com.tutelary.bean.entity.CommandTaskEntity;
import com.tutelary.common.repository.BaseRepository;

public interface CommandTaskRepository extends BaseRepository<CommandTaskQuery, CommandTask, CommandTaskEntity> {

    CommandTask getByTaskId(String taskId);

    boolean commandTaskComplete(String taskId);

}
