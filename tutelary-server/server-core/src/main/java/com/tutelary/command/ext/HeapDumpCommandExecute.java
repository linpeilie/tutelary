package com.tutelary.command.ext;

import com.tutelary.bean.domain.HeapDumpCommand;
import com.tutelary.command.AbstractSingleCommandExecuteAdapter;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.dao.HeapDumpCommandDAO;
import com.tutelary.message.command.param.HeapDumpRequest;
import com.tutelary.message.command.result.HeapDumpResponse;
import org.springframework.stereotype.Component;

@Component
@Extension(commandCode = CommandConstants.heapDump)
public class HeapDumpCommandExecute
    extends AbstractSingleCommandExecuteAdapter<HeapDumpRequest, HeapDumpResponse, HeapDumpCommand> {

    public HeapDumpCommandExecute(final HeapDumpCommandDAO dao) {
        super(dao);
    }

    @Override
    public Integer commandCode() {
        return CommandConstants.heapDump;
    }
}
