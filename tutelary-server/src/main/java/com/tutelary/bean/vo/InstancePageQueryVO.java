package com.tutelary.bean.vo;

import com.tutelary.common.bean.vo.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InstancePageQueryVO extends PageRequest {

    private String appName;

}
