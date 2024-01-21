package cn.easii.tutelary.bean.resp;

import cn.easii.tutelary.common.bean.resp.AbstractResponse;
import cn.easii.tutelary.bean.domain.App;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@AutoMapper(target = App.class)
@Schema(name = "AppInfoResponse", description = "应用信息")
public class AppInfoResponse extends AbstractResponse {

    @Schema(name = "appName", description = "应用名称")
    private String appName;

    @Schema(name = "registerDate", description = "注册时间")
    private LocalDateTime registerDate;

    @Schema(name = "instanceNum", description = "应用实例数量")
    private Integer instanceNum;

}
