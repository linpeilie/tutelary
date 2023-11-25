package cn.easii.tutelary.repository.impl;

import cn.easii.tutelary.message.command.result.EnhanceAffect;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.easii.tutelary.bean.domain.CommandTask;
import cn.easii.tutelary.bean.domain.query.CommandTaskQuery;
import cn.easii.tutelary.bean.entity.CommandTaskEntity;
import cn.easii.tutelary.common.repository.AbstractRepository;
import cn.easii.tutelary.mapper.CommandTaskMapper;
import cn.easii.tutelary.repository.CommandTaskRepository;
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

    @Override
    public boolean updateEnhanceAffect(final String taskId, final EnhanceAffect enhanceAffect) {
        LambdaUpdateWrapper<CommandTaskEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(CommandTaskEntity::getTaskId, taskId);
        updateWrapper.set(CommandTaskEntity::getEnhanceAffect, JSONUtil.toJsonStr(enhanceAffect));
        return super.update(new CommandTaskEntity(), updateWrapper);
    }
}
