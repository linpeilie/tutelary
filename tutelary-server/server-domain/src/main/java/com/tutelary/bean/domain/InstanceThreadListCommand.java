package com.tutelary.bean.domain;

import com.tutelary.common.domain.BaseCommandDomain;
import com.tutelary.common.domain.BaseDomain;
import com.tutelary.message.command.result.ThreadList;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InstanceThreadListCommand extends BaseCommandDomain<ThreadList> {

}
