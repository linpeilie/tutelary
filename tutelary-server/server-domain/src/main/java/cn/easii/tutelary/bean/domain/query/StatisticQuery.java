package cn.easii.tutelary.bean.domain.query;

import cn.easii.tutelary.common.annotation.Query;
import cn.easii.tutelary.common.annotation.Sort;
import cn.easii.tutelary.common.domain.BaseQueryDomain;
import cn.easii.tutelary.common.enums.QueryType;
import cn.easii.tutelary.common.enums.SortDirection;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class StatisticQuery extends BaseQueryDomain {

    @Query
    private String instanceId;

    @Query(queryType = QueryType.GE, field = "report_time")
    private LocalDateTime reportStartTime;

    @Query(queryType = QueryType.LE, field = "report_time")
    private LocalDateTime reportEndTime;

    @Sort(direction = SortDirection.ASC)
    private LocalDateTime reportTime;

}
