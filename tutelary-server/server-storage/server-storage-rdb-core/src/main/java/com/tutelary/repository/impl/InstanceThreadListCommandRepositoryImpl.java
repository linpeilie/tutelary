package com.tutelary.repository.impl;

import com.tutelary.bean.domain.InstanceThreadListCommand;
import com.tutelary.bean.domain.query.CommandTaskQuery;
import com.tutelary.bean.entity.InstanceThreadListCommandEntity;
import com.tutelary.common.repository.AbstractRepository;
import com.tutelary.mapper.InstanceThreadListCommandMapper;
import com.tutelary.repository.InstanceThreadListCommandRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InstanceThreadListCommandRepositoryImpl
    extends AbstractRepository<CommandTaskQuery, InstanceThreadListCommand, InstanceThreadListCommandEntity, InstanceThreadListCommandMapper>
    implements InstanceThreadListCommandRepository {}
