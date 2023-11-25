package cn.easii.tutelary.bean.domain;

import cn.easii.tutelary.common.domain.BaseCommandDomain;
import cn.easii.tutelary.message.command.result.HeapDumpResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class HeapDumpCommand extends BaseCommandDomain<HeapDumpResponse> {

}
