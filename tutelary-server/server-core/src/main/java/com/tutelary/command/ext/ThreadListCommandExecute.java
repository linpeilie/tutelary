package com.tutelary.command.ext;

import com.tutelary.bean.domain.InstanceThreadListCommand;
import com.tutelary.command.AbstractSingleCommandExecuteAdapter;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.constants.CommandEnum;
import com.tutelary.dao.InstanceThreadListCommandDAO;
import com.tutelary.message.command.param.ThreadListRequest;
import com.tutelary.message.command.result.ThreadList;
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
