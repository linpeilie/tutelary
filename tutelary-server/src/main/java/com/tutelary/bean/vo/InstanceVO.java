package com.tutelary.bean.vo;

import com.tutelary.common.bean.vo.BaseResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class InstanceVO extends BaseResult {

    private String instanceId;

    private String appName;

    private String ip;

    private LocalDateTime registerDate;

}
