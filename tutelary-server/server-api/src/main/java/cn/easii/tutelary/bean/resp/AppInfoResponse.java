package cn.easii.tutelary.bean.resp;

import cn.easii.tutelary.common.bean.resp.AbstractResponse;
import cn.easii.tutelary.bean.domain.App;
import io.github.linpeilie.annotations.AutoMapper;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@AutoMapper(target = App.class)
public class AppInfoResponse extends AbstractResponse {

    private String appName;

    private LocalDateTime registerDate;

    private Integer instanceNum;

}
