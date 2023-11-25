package cn.easii.tutelary.bean.domain;

import cn.easii.tutelary.common.domain.BaseCommandDomain;
import cn.easii.tutelary.message.command.result.ThreadDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author linpl
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InstanceThreadDetailCommand extends BaseCommandDomain<ThreadDetail> {

}
