package com.tutelary.bean.domain;

import com.tutelary.common.domain.BaseCommandDomain;
import com.tutelary.message.command.result.SetVmOptionResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InstanceSetVmOptionCommand extends BaseCommandDomain<SetVmOptionResponse> {
}
