package com.tutelary.repository;

import com.tutelary.bean.domain.InstanceThreadListCommand;
import com.tutelary.bean.domain.query.CommandTaskQuery;
import com.tutelary.bean.entity.InstanceThreadListCommandEntity;
import com.tutelary.common.repository.BaseRepository;
import com.tutelary.dao.InstanceThreadListCommandDAO;

public interface InstanceThreadListCommandRepository extends
    BaseRepository<CommandTaskQuery, InstanceThreadListCommand, InstanceThreadListCommandEntity>,
    InstanceThreadListCommandDAO {
}
