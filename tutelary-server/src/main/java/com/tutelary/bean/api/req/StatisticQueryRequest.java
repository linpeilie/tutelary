package com.tutelary.bean.api.req;

import com.tutelary.common.bean.api.req.QueryRequest;
import com.tutelary.common.utils.ParameterUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
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
