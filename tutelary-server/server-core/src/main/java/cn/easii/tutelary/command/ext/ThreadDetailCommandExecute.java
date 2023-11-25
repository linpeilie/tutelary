package cn.easii.tutelary.command.ext;

import cn.easii.tutelary.bean.domain.InstanceThreadDetailCommand;
import cn.easii.tutelary.command.AbstractSingleCommandExecuteAdapter;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.dao.InstanceThreadDetailCommandDAO;
import cn.easii.tutelary.message.command.param.ThreadDetailRequest;
import cn.easii.tutelary.message.command.result.ThreadDetail;
import org.springframework.stereotype.Component;

@Component
@Extension(commandCode = CommandConstants.threadDetail)
public class ThreadDetailCommandExecute
    extends AbstractSingleCommandExecuteAdapter<ThreadDetailRequest, ThreadDetail, InstanceThreadDetailCommand> {

    public ThreadDetailCommandExecute(final InstanceThreadDetailCommandDAO dao) {
        super(dao);
    }

    @Override
    public Integer commandCode() {
        return CommandConstants.threadDetail;
    }
}
