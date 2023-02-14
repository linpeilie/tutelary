package com.tutelary.bean.api.resp;

import com.tutelary.common.bean.api.resp.AbstractResponse;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AppInfoResponse extends AbstractResponse {

    private String appName;

    private LocalDateTime registerDate;

    private Integer instanceNum;

}
