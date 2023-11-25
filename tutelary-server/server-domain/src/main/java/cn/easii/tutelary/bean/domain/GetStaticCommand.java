package cn.easii.tutelary.bean.domain;

import cn.easii.tutelary.common.domain.BaseCommandDomain;
import cn.easii.tutelary.message.command.result.GetStaticResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetStaticCommand extends BaseCommandDomain<GetStaticResponse> {
}
