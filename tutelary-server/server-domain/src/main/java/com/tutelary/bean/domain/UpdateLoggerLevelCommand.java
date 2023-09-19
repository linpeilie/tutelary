package com.tutelary.bean.domain;

import com.tutelary.common.domain.BaseCommandDomain;
import com.tutelary.message.command.result.UpdateLoggerLevelResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateLoggerLevelCommand extends BaseCommandDomain<UpdateLoggerLevelResponse> {
}
