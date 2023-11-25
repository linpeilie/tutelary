package cn.easii.tutelary.bean.domain;

import cn.easii.tutelary.common.domain.BaseCommandDomain;
import cn.easii.tutelary.message.command.result.TraceResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TraceMethodCommand extends BaseCommandDomain<TraceResponse> {
}
