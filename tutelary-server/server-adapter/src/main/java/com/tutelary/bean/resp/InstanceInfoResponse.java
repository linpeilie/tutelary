package com.tutelary.bean.resp;

import com.tutelary.bean.domain.Instance;
import com.tutelary.common.bean.resp.AbstractResponse;
import com.tutelary.common.enums.InstanceStateEnum;
import io.github.zhaord.mapstruct.plus.annotations.AutoMap;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMap(targetType = Instance.class)
public class InstanceInfoResponse extends AbstractResponse {

    private String instanceId;

    private String appName;

    private String ip;

    private LocalDateTime registerDate;

    private InstanceStateEnum state;

    private LocalDateTime startTime;

}
