package com.tutelary.repository;

import com.tutelary.bean.domain.InstanceThreadStatistic;
import com.tutelary.bean.domain.query.StatisticQuery;
import com.tutelary.bean.entity.InstanceThreadStatisticEntity;
import com.tutelary.common.repository.BaseRepository;
import com.tutelary.dao.InstanceThreadStatisticDAO;

public interface InstanceThreadStatisticRepository
    extends BaseRepository<StatisticQuery, InstanceThreadStatistic, InstanceThreadStatisticEntity>,
    InstanceThreadStatisticDAO {}
