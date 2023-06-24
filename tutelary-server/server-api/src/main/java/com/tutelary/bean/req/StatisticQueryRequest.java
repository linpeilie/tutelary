package com.tutelary.bean.req;

import com.tutelary.bean.domain.query.StatisticQuery;
import com.tutelary.common.bean.req.QueryRequest;
import com.tutelary.common.utils.ParameterUtils;
import io.github.linpeilie.annotations.AutoMapper;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = StatisticQuery.class)
public class StatisticQueryRequest extends QueryRequest {

    private String instanceId;

    private LocalDateTime reportStartTime;

    private LocalDateTime reportEndTime;

    @Override
    public void checkInput() {
        ParameterUtils.notBlank(instanceId, "instance id cannot be blank");
        ParameterUtils.nonNull(reportStartTime, "report start time cannot be null");
        ParameterUtils.nonNull(reportEndTime, "report end time cannot be null");
    }
}
