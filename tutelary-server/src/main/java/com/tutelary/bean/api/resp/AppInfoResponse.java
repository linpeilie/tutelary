package com.tutelary.bean.api.resp;

import com.tutelary.common.bean.api.resp.AbstractResponse;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppInfoResponse extends AbstractResponse {

    private String appName;

    private LocalDateTime registerDate;

    private Integer instanceNum;

}
