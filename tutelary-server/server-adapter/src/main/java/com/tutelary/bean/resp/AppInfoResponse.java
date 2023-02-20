package com.tutelary.bean.resp;

import com.tutelary.bean.domain.App;
import com.tutelary.common.bean.resp.AbstractResponse;
import io.github.zhaord.mapstruct.plus.annotations.AutoMap;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@AutoMap(targetType = App.class)
public class AppInfoResponse extends AbstractResponse {

    private String appName;

    private LocalDateTime registerDate;

    private Integer instanceNum;

}
