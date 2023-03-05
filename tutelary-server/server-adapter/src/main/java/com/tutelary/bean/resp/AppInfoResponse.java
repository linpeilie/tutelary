package com.tutelary.bean.resp;

import com.tutelary.bean.domain.App;
import com.tutelary.common.bean.resp.AbstractResponse;
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
