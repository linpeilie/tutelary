package cn.easii.tutelary.command.ext;

import cn.easii.tutelary.bean.domain.HeapDumpCommand;
import cn.easii.tutelary.command.AbstractSingleCommandExecuteAdapter;
import cn.easii.tutelary.common.extension.Extension;
import cn.easii.tutelary.constants.CommandConstants;
import cn.easii.tutelary.dao.HeapDumpCommandDAO;
import cn.easii.tutelary.message.command.param.HeapDumpRequest;
import cn.easii.tutelary.message.command.result.HeapDumpResponse;
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
