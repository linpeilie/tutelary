package com.tutelary.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tutelary.bean.domain.CommandTask;
import com.tutelary.bean.domain.query.CommandTaskQuery;
import com.tutelary.bean.entity.CommandTaskEntity;
import com.tutelary.common.repository.AbstractRepository;
import com.tutelary.mapper.CommandTaskMapper;
import com.tutelary.repository.CommandTaskRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Repository;

@Repository
public class CommandTaskRepositoryImpl
    extends AbstractRepository<CommandTaskQuery, CommandTask, CommandTaskEntity, CommandTaskMapper>
    implements CommandTaskRepository {

    @Override
    public CommandTask getByTaskId(final String taskId) {
        LambdaQueryWrapper<CommandTaskEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(CommandTaskEntity::getTaskId, taskId);
        return entityToDomain(super.getOne(queryWrapper));
    }

    @Override
    public boolean commandTaskComplete(String taskId) {
        LambdaUpdateWrapper<CommandTaskEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(CommandTaskEntity::getTaskId, taskId);
        updateWrapper.set(CommandTaskEntity::getCompleteTime, LocalDateTime.now());
        return super.update(new CommandTaskEntity(), updateWrapper);
    }
}
