package cn.easii.tutelary.command.ext;

import cn.easii.tutelary.bean.domain.TraceMethodCommand;
import cn.easii.tutelary.command.AbstractEnhanceCommandExecuteAdapter;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.constants.CommandEnum;
import cn.easii.tutelary.dao.TraceMethodCommandDAO;
import cn.easii.tutelary.message.command.param.TraceRequest;
import cn.easii.tutelary.message.command.result.TraceResponse;
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
