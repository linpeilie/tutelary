package cn.easii.tutelary.bean.domain;

import cn.easii.tutelary.common.domain.BaseDomain;
import cn.easii.tutelary.message.command.result.EnhanceAffect;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CommandTask extends BaseDomain {

    private Integer commandCode;

    private String instanceId;

    private String taskId;

    private String param;

    private EnhanceAffect enhanceAffect;

    private LocalDateTime completeTime;

}
