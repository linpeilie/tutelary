package com.tutelary.repository;

import com.tutelary.bean.domain.InstanceSimpleCommand;
import com.tutelary.bean.domain.query.CommandTaskQuery;
import com.tutelary.bean.entity.InstanceSimpleCommandEntity;
import com.tutelary.common.repository.BaseRepository;

public interface InstanceSimpleCommandRepository
    extends BaseRepository<CommandTaskQuery, InstanceSimpleCommand, InstanceSimpleCommandEntity> {
}
