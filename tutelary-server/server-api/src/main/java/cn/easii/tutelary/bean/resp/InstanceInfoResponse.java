package cn.easii.tutelary.bean.resp;

import cn.easii.tutelary.common.bean.resp.AbstractResponse;
import cn.easii.tutelary.bean.domain.Instance;
import cn.easii.tutelary.enums.InstanceStateEnum;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Instance.class)
@Schema(name = "InstanceInfoResponse", description = "实例信息")
public class InstanceInfoResponse extends AbstractResponse {

    @Schema(name = "instanceId", description = "实例ID")
    private String instanceId;

    @Schema(name = "appName", description = "应用名称")
    private String appName;

    @Schema(name = "IP", description = "实例IP")
    private String ip;

    @Schema(name = "registerDate", description = "注册时间")
    private LocalDateTime registerDate;

    @Schema(name = "state", description = "实例状态")
    private Integer state;

    @Schema(name = "startTime", description = "启动时间")
    private LocalDateTime startTime;

}
