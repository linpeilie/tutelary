package cn.easii.tutelary.bean.resp;

import cn.easii.tutelary.common.bean.resp.AbstractResponse;
import cn.easii.tutelary.bean.domain.Instance;
import cn.easii.tutelary.enums.InstanceStateEnum;
import io.github.linpeilie.annotations.AutoMapper;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Instance.class)
public class InstanceInfoResponse extends AbstractResponse {

    private String instanceId;

    private String appName;

    private String ip;

    private LocalDateTime registerDate;

    private InstanceStateEnum state;

    private LocalDateTime startTime;

}
