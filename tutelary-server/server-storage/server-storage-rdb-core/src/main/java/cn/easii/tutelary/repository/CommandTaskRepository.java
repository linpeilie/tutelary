package cn.easii.tutelary.repository;

import cn.easii.tutelary.bean.domain.CommandTask;
import cn.easii.tutelary.bean.domain.query.CommandTaskQuery;
import cn.easii.tutelary.bean.entity.CommandTaskEntity;
import cn.easii.tutelary.common.repository.BaseRepository;
import cn.easii.tutelary.dao.CommandTaskDAO;

public interface CommandTaskRepository
    extends BaseRepository<CommandTaskQuery, CommandTask, CommandTaskEntity>, CommandTaskDAO {

    CommandTask getByTaskId(String taskId);

    boolean commandTaskComplete(String taskId);

}
