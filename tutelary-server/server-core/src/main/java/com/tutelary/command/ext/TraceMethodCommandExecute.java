package com.tutelary.command.ext;

import com.tutelary.bean.domain.TraceMethodCommand;
import com.tutelary.command.AbstractEnhanceCommandExecuteAdapter;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.constants.CommandEnum;
import com.tutelary.dao.TraceMethodCommandDAO;
import com.tutelary.message.command.param.TraceRequest;
import com.tutelary.message.command.result.TraceResponse;
import org.springframework.stereotype.Component;

@Component
@Extension(commandCode = CommandConstants.traceMethod)
public class TraceMethodCommandExecute
    extends AbstractEnhanceCommandExecuteAdapter<TraceRequest, TraceResponse, TraceMethodCommand> {

    public TraceMethodCommandExecute(final TraceMethodCommandDAO dao) {
        super(dao);
    }

    @Override
    public Integer commandCode() {
        return CommandEnum.TRACE_METHOD.getCommandCode();
    }
}
