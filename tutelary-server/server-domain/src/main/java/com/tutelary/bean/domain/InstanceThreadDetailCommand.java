package com.tutelary.bean.domain;

import com.tutelary.common.domain.BaseCommandDomain;
import com.tutelary.message.command.result.ThreadDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author linpl
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InstanceThreadDetailCommand extends BaseCommandDomain<ThreadDetail> {

}
