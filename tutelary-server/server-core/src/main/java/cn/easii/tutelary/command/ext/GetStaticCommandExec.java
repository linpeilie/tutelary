package cn.easii.tutelary.command.ext;

import cn.easii.tutelary.bean.domain.GetStaticCommand;
import cn.easii.tutelary.command.AbstractPersistenceCommandExecuteAdapter;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.dao.GetStaticCommandDAO;
import cn.easii.tutelary.message.command.param.GetStaticRequest;
import cn.easii.tutelary.message.command.result.GetStaticResponse;
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
