package com.tutelary.command.ext;

import com.tutelary.bean.domain.GetStaticCommand;
import com.tutelary.command.AbstractPersistenceCommandExecuteAdapter;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.dao.GetStaticCommandDAO;
import com.tutelary.message.command.param.GetStaticRequest;
import com.tutelary.message.command.result.GetStaticResponse;
import org.springframework.stereotype.Component;

@Component
@Extension(commandCode = CommandConstants.getStatic)
public class GetStaticCommandExec extends AbstractPersistenceCommandExecuteAdapter<GetStaticRequest, GetStaticResponse, GetStaticCommand> {
    public GetStaticCommandExec(GetStaticCommandDAO dao) {
        super(dao);
    }

    @Override
    public Integer commandCode() {
        return CommandConstants.getStatic;
    }
}
