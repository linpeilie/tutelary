package com.tutelary.repository.impl;

import com.tutelary.bean.converter.SimpleCommandConverter;
import com.tutelary.bean.domain.InstanceSimpleCommand;
import com.tutelary.bean.domain.query.CommandTaskQuery;
import com.tutelary.bean.entity.InstanceSimpleCommandEntity;
import com.tutelary.common.repository.AbstractRepository;
import com.tutelary.mapper.InstanceSimpleCommandMapper;
import com.tutelary.repository.InstanceSimpleCommandRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InstanceSimpleCommandRepositoryImpl
    extends AbstractRepository<CommandTaskQuery, InstanceSimpleCommand, InstanceSimpleCommandEntity, InstanceSimpleCommandMapper>
    implements InstanceSimpleCommandRepository {

    public InstanceSimpleCommandRepositoryImpl(final SimpleCommandConverter converter) {
        super(converter);
    }
}
