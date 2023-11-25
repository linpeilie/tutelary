package cn.easii.tutelary.command.ext;

import cn.easii.tutelary.bean.domain.InstanceThreadListCommand;
import cn.easii.tutelary.command.AbstractSingleCommandExecuteAdapter;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.constants.CommandEnum;
import cn.easii.tutelary.dao.InstanceThreadListCommandDAO;
import cn.easii.tutelary.message.command.param.ThreadListRequest;
import cn.easii.tutelary.message.command.result.ThreadList;
import org.springframework.stereotype.Component;

@Component
@Extension(commandCode = CommandConstants.threadList)
public class ThreadListCommandExecute extends AbstractSingleCommandExecuteAdapter<ThreadListRequest, ThreadList, InstanceThreadListCommand> {

    public ThreadListCommandExecute(final InstanceThreadListCommandDAO dao) {
        super(dao);
    }

    @Override
    public Integer commandCode() {
        return CommandEnum.THREAD_LIST.getCommandCode();
    }

}
