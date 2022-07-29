package com.tutelary.bean.vo;

import com.tutelary.common.bean.vo.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InstanceQueryVO extends BaseRequest {

    private String appName;

}
