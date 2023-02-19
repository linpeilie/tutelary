package com.tutelary.command.ext;

import com.tutelary.bean.domain.InstanceThreadDetailCommand;
import com.tutelary.command.AbstractSimpleCommandExecuteAdapter;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.dao.InstanceThreadDetailCommandDAO;
import com.tutelary.message.command.param.ThreadDetailRequest;
import com.tutelary.message.command.result.ThreadDetail;
import org.springframework.stereotype.Component;

@Component
@Extension(commandCode = CommandConstants.threadDetail)
public class ThreadDetailCommandExecute
    extends AbstractSimpleCommandExecuteAdapter<ThreadDetailRequest, ThreadDetail, InstanceThreadDetailCommand> {

    public ThreadDetailCommandExecute(final InstanceThreadDetailCommandDAO dao) {
        super(dao);
    }

    @Override
    public Integer commandCode() {
        return CommandConstants.threadDetail;
    }
}
