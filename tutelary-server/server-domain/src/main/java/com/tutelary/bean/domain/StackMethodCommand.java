package com.tutelary.bean.domain;

import com.tutelary.common.domain.BaseCommandDomain;
import com.tutelary.message.command.result.StackResponse;
import com.tutelary.message.command.result.TraceResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StackMethodCommand extends BaseCommandDomain<StackResponse> {
}
